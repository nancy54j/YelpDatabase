package ca.ece.ubc.cpen221.mp5.Database;

import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.User.RestaurantUser;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

public class YelpDataBase implements MP5Db {
    Set<Restaurant> restaurants;
    Set<Review> reviews;
    Set<RestaurantUser> users;
    Map<String, Restaurant> restMap;
    Map<String, RestaurantUser> userMap;
    Map<String, Review> revMap;
    private Object modifydatabase = new Object();


    //initalize yelpDataBase
    public YelpDataBase(){
        ParseJson pj = new ParseJson("./data/users.json", "./data/restaurants.json",
                "./data/reviews.json");

        restaurants = pj.getRestaurants();
        reviews = pj.getReviews();
        users = pj.getUsers();
        restMap = pj.getrestMap();
        userMap = pj.getUserMap();
    }

    //i'm not editing anything, so I don't violate concurrency/rep invariants
    public String getRestaurant(String restID) throws IllegalArgumentException{
        if(restMap.keySet().contains(restID)){
            throw new IllegalArgumentException();
        }

        return restMap.get(restID).toString();
    }

    public boolean addUser(String restUser)throws JsonParsingException {

    }
    //double latitude, double longitude, String name, String[] neighbourhood, String full_address,
    //String city, String state
    //me
    //{{\"latitide\": <double>, \"longitude\": <double>, \"name\": \"<name>\", " +
    //                "\"neighborhood\": [\"<neighborhoods>\", \"<neighborhood>\"], \"full_address\": \"<full_address\"" +
    //                       ", \"city\": \"<city>\", \"state\": \"<state>\"}
    public boolean addRestaurant(String rest)throws JsonParsingException, IllegalArgumentException{
        if(restMap.keySet().contains(rest)){
            return false;
        }
        synchronized(modifydatabase){
            JsonObject jsonrestaurant = Json.createReader(new StringReader(rest)).readObject();

            //make a neighborhood string[]
            JsonArray a = jsonrestaurant.getJsonArray("neighborhoods");
            String[] neighborhood = new String[a.size()];
            int i = 0;
            for(JsonValue s : a){
                neighborhood[i] = s.toString();
                i++;
            }
            double latitude = jsonrestaurant.getJsonNumber("latitude").doubleValue();

            Restaurant r = Restaurant()
        }
    }

    public boolean addReview(String rev)throws JsonParsingException{

    }

    /**
     * adds a new user to the database. This user must not have been added before
     *
     * @param ru
     * @return whether or not the user was successfully added
     */
    private boolean addnewUser(RestaurantUser ru){
        if(!userMap.keySet().contains(ru.UserID)){
            userMap.put(ru.UserID, ru);
            users.add(ru);
            return true;
        }
        return false;
    }

    /**
     * adds a new restaurant to the databse. This restaurant must not have existed in this database before
     * @param r
     * @return whether the add was successful
     */
    private boolean addnewRestaurant(Restaurant r){
        if(!restMap.keySet().contains(r.id)){
            restMap.put(r.id, r);
            restaurants.add(r);
            return true;
        }
        return false;
    }

    /**
     * adds a new review to the database. It will check if the business and restaurant corresponding to the
     * review are within this database, and then it will check if the user has previously reviewed this restaurant
     * before.
     *
     * If the parameters pass, then it will be added to the database
     *
     * @param r
     * @return
     */
    private boolean addReview(Review r){
        //if the user and business are in this database, and the review has not been recorded yet
        if(userMap.keySet().contains(r.user) && restMap.keySet().contains(r.business) && !reviews.contains(r.id)){
            //see if the restaurant has been reviewed by this specific user already
            if(!userMap.get(r.user).getReviews().contains(r.business)){
                userMap.get(r.user).addReview(r);
                restMap.get(r.business).addReview(r);
                reviews.add(r);
            }
            return false;
        }
        return false;
    }

    /**
     * removes a given review from the database. It takes in the restaurant and the user, and checks if there is a
     * review that links the two. If so, it will remove all instances of that review
     * @param ru
     * @param r
     * @return whether the removal was successful or not
     */
    public boolean deleteReview(RestaurantUser ru, Restaurant r){
        //finding the review of a particular user to the particular restaurant
        synchronized(userMap){
            for (Review rev : reviews) {
                if (rev.business.equals(r) && rev.user.equals(ru)) {
                    userMap.get(rev.user).deleteReview(rev);
                    restMap.get(rev.business).deleteReview(rev);
                    this.reviews.remove(rev);
                }
            }
            return false;
        }
    }

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

        for(Restaurant r : restaurants){
            //do some light manipulation to the string for better matches

            String[] query = queryString.toLowerCase().split("//s+");
            String tosearch = ".*";
            for(String s : query){
                tosearch += s + ".*";
            }

            //added is to slightly improve performance
            boolean added = false;
            if(r.city.toLowerCase().matches(tosearch) || r.full_address.toLowerCase().matches(tosearch) ||
                    r.state.toLowerCase().matches(tosearch) || r.name.toLowerCase().matches(tosearch)){
               retSet.add(r.name);
            }
            else {
                for (String s : r.neighbourhood) {
                    if (s.toLowerCase().matches(tosearch)) {
                        added = true;
                        retSet.add(r.name);
                        break;
                    }
                }
                if(added) continue;
                for (String s : r.getCategory()) {
                    if (s.toLowerCase().matches(tosearch)) {
                        added = true;
                        retSet.add(r.name);
                        break;
                    }
                }
                if(added) continue;
                for (String s : r.getSchool()) {
                    if (s.toLowerCase().matches(tosearch)) {
                        retSet.add(r.name);
                        break;
                    }
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
        if(restaurants.size() < k){
            throw new IllegalArgumentException("k is too big");
        }

        List<Node> nodes = new ArrayList<>(k);
        Map<String, double[]> memberlocation = new HashMap<>();

        //Split the restaurant set into k equal sets
        Iterator it = restaurants.iterator();
        int amount = restaurants.size() / k + 1;
        for(int i = 0; i < k; i++){
            nodes.add(new Node(i));
            for(int ii = 0; ii < amount && it.hasNext(); ii++){
                Restaurant r = (Restaurant) it.next();
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

        if (business.contains(businessID)) {
            try {
                throw new Exception("business already reviewed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } if (!restMap.containsKey(businessID)) {
            try {
                throw new Exception("business not found!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  if (!userMap.containsKey(userID)) {
            try {
                throw new Exception("user not found!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SimpleRegression regression = new SimpleRegression();

        for (int i = 0; i < price.size(); i++) {
            regression.addData(price.get(i), rating.get(i));
        }

        Restaurant restaurant = restMap.get(businessID);

        return regression.predict(restaurant.getPrice());

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


    public static void main(String[] args){
        YelpDataBase ydb = new YelpDataBase();
        System.out.println(ydb.getMatches("coffee"));
        System.out.println(ydb.kMeansClusters_json(6));
        System.out.println(ydb.getMatches("chinese"));

        System.out.println(ydb.users);
        System.out.println(ydb.userMap.get("_NH7Cpq3qZkByP5xR4gXog").getReviewCount());

        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("VfqkoiMTtw3_BVk9wAB_YA");
        System.out.println(func.applyAsDouble(ydb, "    _NH7Cpq3qZkByP5xR4gXog"));


    }




}