package ca.ece.ubc.cpen221.mp5.Database;

import ca.ece.ubc.cpen221.mp5.Antlr.RequestLexer;
import ca.ece.ubc.cpen221.mp5.Antlr.RequestParser;
import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.User.RestaurantUser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.function.ToDoubleBiFunction;



public class YelpDataBase implements MP5Db {
    Map<String, Restaurant> restMap;
    Map<String, RestaurantUser> userMap;
    Map<String, Review> revMap;
    private Object modifydatabase;


    //initalize yelpDataBase
    public YelpDataBase(){
        ParseJson pj = new ParseJson("./data/users.json", "./data/restaurants.json",
                "./data/reviews.json");

        restMap = pj.getrestMap();
        userMap = pj.getUserMap();
        revMap = pj.getReviewMap();
        modifydatabase = new Object();
    }

    //this section is for queries of the server
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getRestaurant(String restID) throws IllegalArgumentException{
        if(!restMap.keySet().contains(restID)){
            throw new IllegalArgumentException();
        }

        return restMap.get(restID).toString();
    }

    public boolean addUser(String restUser)throws JsonParsingException {
        return false;
    }

    public String addRestaurant(String rest)throws JsonParsingException{
        JsonObject jsonrestaurant = Json.createReader(new StringReader(rest)).readObject();

        //make a neighborhood string[]
        JsonArray a = jsonrestaurant.getJsonArray("neighborhoods");
        String[] neighborhood = new String[a.size()];
        int i = 0;
        for(JsonValue s : a){
            neighborhood[i] = s.toString();
            i++;
        }
        Restaurant r = new Restaurant(jsonrestaurant.getJsonNumber("latitude").doubleValue(),
                jsonrestaurant.getJsonNumber("longitude").doubleValue(),
                jsonrestaurant.getString("name"), neighborhood,
                jsonrestaurant.getString("full_address"), jsonrestaurant.getString("city"),
                jsonrestaurant.getString("state"));

        if(addnewRestaurant(r)){
            return "Add success:" + r.toString();
        }
        else{
            return "Something went wrong internally. Add unsuccessful";
        }
    }

    public boolean addReview(String rev)throws JsonParsingException{
        JsonObject review = Json.createReader(new StringReader(rev)).readObject();
        double starRating = review.getJsonNumber("stars").doubleValue();
        if (starRating > 5 || starRating < 1) {
            throw new IllegalArgumentException("Star rating can not be greater than 5!");
        }

        String user_id = review.getString("user_id");
        String business_id = review.getString("business_id");
        String text = review.getString("text");

        Review r = new Review(starRating, text, user_id, business_id);
        if(revMap.containsKey(r.id)){
            revMap.remove(r.id);
        }
        this.revMap.put(r.id,r);
        return true;
    }

    /**
     * adds a new user to the database. This user must not have been added before
     *
     * This method is used by addUser, which is used by the server
     *
     * @param ru
     * @return whether or not the user was successfully added
     */
    private boolean addnewUser(RestaurantUser ru){
        if(!userMap.keySet().contains(ru.UserID)){
            userMap.put(ru.UserID, ru);
            return true;
        }
        return false;
    }

    /**
     * adds a new restaurant to the database. This restaurant must not have existed in this database before
     *
     * This method is used by addRestaurant, which is called by the server that processes requests
     * @param r
     * @return whether the add was successful
     */
    private boolean addnewRestaurant(Restaurant r){
        synchronized(modifydatabase) {
            if (!restMap.keySet().contains(r.id)) {
                restMap.put(r.id, r);
                return true;
            }
            return false;
        }
    }

    /**
     * adds a new review to the database. It will check if the business and restaurant corresponding to the
     * review are within this database, and then will check if there already exists a review between
     * the user and a restaurant. If there is, it will delete that review and add the new review
     *
     * This method is used by addReview, which is then called from the server that processes requests
     *
     * @param r review
     * @return
     */
    private boolean addnewReview(Review r){
        Restaurant rrev = restMap.get(r.business);
        RestaurantUser urev = userMap.get(r.business);
        //if user/business are not in this database, or if this review has been recorded already
        if(rrev == null || urev == null || revMap.keySet().contains(r.id)){
            return false;
        }

        synchronized(modifydatabase){
            Set<String> intersect = rrev.getReviews();
            intersect.retainAll(urev.getReviews());
            if(!intersect.isEmpty()){
                deleteReview(urev, rrev);
            }
            rrev.addReview(r);
            urev.addReview(r);
            revMap.put(r.id, r);
        }
        return true;
    }

    /**
     * removes a given review from the database. It takes in the restaurant and the user
     * and checks if there is a review that links the two. If so, it will remove all instances of
     * that review
     *
     * This method is only used by addNewReview, and therefore is private and therefore addNewReview will
     * take care of threading issues
     *
     * @param ru
     * @param r
     * @return whether the removal was successful or not
     */
    private boolean deleteReview(RestaurantUser ru, Restaurant r){
        //finding the review of a particular user to the particular restaurant
        for (String srev : revMap.keySet()) {
            Review rev = revMap.get(srev);
            if (rev.business.equals(r) && rev.user.equals(ru)) {
                userMap.get(rev.user).deleteReview(rev);
                restMap.get(rev.business).deleteReview(rev);
                revMap.remove(rev.id);
            }
        }
        return false;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * returns a set of ids that represents the ID of a restaurant, such that some field within the restaurant
     * matches the query string
     * (personally, a list would be a better implementation, because then there is ordinality, and different
     * weights and relevancy can be assigned to different parameters
     *
     * @param queryString
     * @return
     */
    public Set<String> getMatches(String queryString){
        Set<String> retSet = new HashSet<>();
        String[] query = queryString.toLowerCase().split("\\s+");

        for(String rid : restMap.keySet()){
            Restaurant r = restMap.get(rid);
            //do some light manipulation to the string for better matches

            for(String tosearch : query) {
                tosearch = ".*" + tosearch + ".*";

                //boolean is to slightly improve performance
                boolean added = false;

                if (r.city.toLowerCase().matches(tosearch) || r.full_address.toLowerCase().matches(tosearch) ||
                        r.state.toLowerCase().matches(tosearch) || r.name.toLowerCase().matches(tosearch)) {
                    retSet.add(r.id);
                    added = true;
                } else {
                    for (String s : r.neighbourhood) {
                        if (s.toLowerCase().matches(tosearch)) {
                            added = true;
                            retSet.add(r.id);
                            break;
                        }
                    }
                    if (added) break;
                    for (String s : r.getCategory()) {
                        if (s.toLowerCase().matches(tosearch)) {
                            added = true;
                            retSet.add(r.id);
                            break;
                        }
                    }
                    if (added) break;
                    for (String s : r.getSchool()) {
                        if (s.toLowerCase().matches(tosearch)) {
                            retSet.add(r.id);
                            break;
                        }
                    }
                }
                if(added){
                    break;
                }
            }
        }

        return retSet;
    }

    /**
     * returns a cluster of restaurants in json format based on the lat/long (rough location) of them,
     * such that no restaurant is closer to another node than the one it is assigned to
     * @param k
     *            number of clusters to create (0 < k <= number of objects)
     * @return
     */
    public String kMeansClusters_json(int k){
        if(restMap.size() < k || k < 1){
            throw new IllegalArgumentException("k is too big");
        }

        List<Node> nodes = new ArrayList<>(k);
        Map<String, double[]> memberlocation = new HashMap<>();

        //Split the restaurant set into k equal sets
        Iterator it = restMap.keySet().iterator();
        int amount = restMap.size() / k + 1;
        for(int i = 0; i < k; i++){
            nodes.add(new Node(i));
            for(int ii = 0; ii < amount && it.hasNext(); ii++){
                Restaurant r = restMap.get(it.next());
                memberlocation.put(r.id, r.getLocation());
                nodes.get(i).addmember(r.id, r.getLocation());
            }
            nodes.get(i).calcNewLocation();
        }

        //actual computing: moved is used to terminate the loop
        boolean moved = true;
        while(moved){
            moved = false;

            //check each restaurant
            for(String s : memberlocation.keySet()){
                //good enough for lat/long...
                double shortestdistance = 1000000000;
                int assign = 0;
                //figure out which node is the closest
                for(int i = 0; i < nodes.size(); i++) {
                    double distance = nodes.get(i).calcDistance(memberlocation.get(s));
                    if(distance < shortestdistance){
                        assign = i;
                        shortestdistance = distance;
                    }
                }
                //if it wasn't the previously assigned node, change it
                if(!nodes.get(assign).getMembers().contains(s)){
                    moved = true;
                    for(Node n : nodes){
                        n.removemember(s);
                    }
                    nodes.get(assign).addmember(s, memberlocation.get(s));
                }
            }

            //calculate new locations
            for(Node n : nodes){
                n.calcNewLocation();
            }
        }

        if(nodes.isEmpty()){
            return "[]";
        }
        String retjson = "[";
        for(Node n : nodes){
            retjson += n.toJsonString() + ", ";
        }
        retjson = retjson.substring(0, retjson.length() -2) + "]";

        return retjson;
    }

    //used by k-means
    private class Node{
        Map<String, double[]> memberlocations;
        double[] location;
        int number; //identifies this node from another node

        Node(int number){
            memberlocations = new HashMap<>();
            location = new double[]{0, 0};
            this.number = number;
        }

        void addmember(String s, double[] location){
            memberlocations.put(s, location);
        }

        void removemember(String s){
            memberlocations.remove(s);
        }

        Set<String> getMembers(){
            return memberlocations.keySet();
        }

        double[] calcNewLocation(){
            double[] newlocation = new double[] {0,0};
            for(double[] d : memberlocations.values()){
                newlocation[0] += d[0];
                newlocation[1] += d[1];
            }
            newlocation[0] /= memberlocations.size();
            newlocation[1] /= memberlocations.size();

            location = newlocation;
            return newlocation;
        }

        private double calcDistance(double[] a){
            double dx = a[0] - location[0];
            double dy = a[1] - location[1];
            return Math.sqrt(dx*dx + dy*dy);
        }

        //{"x": number, "y": number, "name": "string", "cluser": int, "weight": int}
        String toJsonString(){
            String json = "";
            if(memberlocations.isEmpty()){
                return "{}";
            }
            for(String s : memberlocations.keySet()){
                json += "{\"x\": " + memberlocations.get(s)[0] + ", \"y\": " + memberlocations.get(s)[1] +
                        ", \"name\": \"" + s + "\", \"cluster\": " + number + ", \"weight\": " + "1.0" + "}, ";
            }
            json = json.substring(0, json.length() - 2);

            return json;
        }
    }

    public double linearRegression(YelpDataBase database,String businessID, String userID) {

        RestaurantUser user = (RestaurantUser) database.userMap.get(userID);
        Set<String> reviewID = user.getReviews();
        Set<Review> reviews = new HashSet<>();
        Set<String> business = new HashSet<>();

        //map all review id written by users to their ratings
        for (String id : reviewID) {
            reviews.add(database.revMap.get(id));
        }

        //map all review id to their restaurants
        //map all reviews written by users to price
        LinkedList<Double> price = new LinkedList<>();
        LinkedList<Double> rating = new LinkedList<>();

        for (Review review : reviews) {
            String bID = review.business;
            Restaurant restaurant = (Restaurant) database.restMap.get(bID);

            //map price vs rating
            business.add(review.business);
            price.add((double) restaurant.getPrice());
            rating.add(review.rating);
        }

        if(price.size() < 2){
            throw new IllegalArgumentException("not enough data points");
        }

        if (business.contains(businessID)) {
             System.out.println("Business already reviewed by user!");
             return restMap.get(businessID).getRating();
        }

        if (!restMap.containsKey(businessID)) {
            throw new IllegalArgumentException("business not found!");
        }
        if (!userMap.containsKey(userID)) {
            throw new IllegalArgumentException("user not found!");
        }

        SimpleRegression regression = new SimpleRegression();


        for (int i = 0; i < price.size(); i++) {
            regression.addData(price.get(i), rating.get(i));
        }


        Restaurant restaurant = restMap.get(businessID);

        double predict = regression.predict(restaurant.getPrice());
        if(predict < 1 ){
            predict = 1;
        }
        if (predict > 5){
            predict = 5;
        }

        return predict;
    }

    /**
     *
     * @param user
     *            represents a user_id in the database
     * @return a function that predicts the user's ratings for objects (of type
     *         T) in the database of type MP5Db<T>. The function that is
     *         returned takes two arguments: one is the database and other other
     *         is a String that represents the id of an object of type T.
     */
    public ToDoubleBiFunction<YelpDataBase, String> getPredictorFunction(String user){

        ToDoubleBiFunction<YelpDataBase, String> predictRating = (x,y)->linearRegression(x,y,user);

        return predictRating;
    }


    public static void main(String[] args) throws IOException {
        YelpDataBase ydb = new YelpDataBase();
        System.out.println(ydb.getMatches("coffee"));
        System.out.println(ydb.kMeansClusters_json(6));

        CharStream cs = CharStreams.fromReader(new StringReader(
                "in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2"));
        RequestLexer lexer = new RequestLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RequestParser parser = new RequestParser(tokens, ydb);
        System.out.println(parser.req().restaurants);


        /*
        System.out.println(ydb.getMatches("chinese"));

        System.out.println(ydb.users);
        System.out.println(ydb.userMap.get("_NH7Cpq3qZkByP5xR4gXog").getReviewCount());
        */

        /*
        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("VfqkoiMTtw3_BVk9wAB_YA");
        System.out.println(func.applyAsDouble(ydb, "_NH7Cpq3qZkByP5xR4gXog"));
        */
    }

    //String s is regex
    //there's a lot of useless code at the start, but I don't know what to do with it
    public Set<String> categoryAtom(String regex){
        Set<String> retSet = new HashSet<>();
        for(String srest : restMap.keySet()){
            Restaurant r = restMap.get(srest);
            for(String category : r.getCategory()){
                if(category.matches(regex)){
                    retSet.add(r.id);
                    break;
                }
            }
        }
        return retSet;
    }

    public Set<String> inAtom(String regex){
        Set<String> retSet = new HashSet<>();
        for(String srest : restMap.keySet()){
            Restaurant r = restMap.get(srest);
            //full_address should take into account state and city
            if(r.full_address.matches(regex)){
                retSet.add(r.id);
                continue;
            }
            for(String location : r.neighbourhood){
                if(location.matches(regex)){
                    retSet.add(r.id);
                    break;
                }
            }
        }
        return retSet;
    }

    public Set<String> nameAtom(String regex){
        Set<String> retSet = new HashSet<>();
        String tofind = ".*" + regex + ".*";
        for(String srest : restMap.keySet()){
            Restaurant r = restMap.get(srest);
            if(r.name.matches(tofind)){
                retSet.add(r.id);
            }
        }
        return retSet;
    }

    public Set<String> priceAtom(String s, int num) throws IllegalArgumentException{
        Set<String> retSet = new HashSet<>();
        if(num > 5 || num < 0){
            throw new IllegalArgumentException();
        }
        switch(s){
            case ">":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getPrice() > num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "<":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getPrice() < num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "<=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getPrice() <= num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case ">=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getPrice() >= num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getPrice() == num) {
                        retSet.add(r.id);
                    }
                }
                break;
            default: throw new IllegalArgumentException();
        }

        return retSet;
    }

    public Set<String> ratingAtom(String s, int num) throws IllegalArgumentException{
        Set<String> retSet = new HashSet<>();
        if(num > 5 || num < 0){
            throw new IllegalArgumentException();
        }
        switch(s){
            case ">":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getRating() > num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "<":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getRating() < num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "<=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getRating() <= num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case ">=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getRating() >= num) {
                        retSet.add(r.id);
                    }
                }
                break;
            case "=":
                for(String srest : restMap.keySet()) {
                    Restaurant r = restMap.get(srest);
                    if (r.getRating() == num) {
                        retSet.add(r.id);
                    }
                }
                break;
            default: throw new IllegalArgumentException();
        }

        return retSet;
    }


}
