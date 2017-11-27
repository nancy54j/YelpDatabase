package ca.ece.ubc.cpen221.mp5;

import java.util.*;
import java.util.function.ToDoubleBiFunction;

public class YelpDataBase implements MP5Db{
    Set<Restaurant> restaurants;
    Set<Review> reviews;
    Set<RestaurantUser> users;
    Map<String, Restaurant> restMap;
    Map<String, RestaurantUser> userMap;
    Map<String, Review> revMap;

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

    /**
     * adds a new user to the database. This user must not have been added before
     *
     * @param ru
     * @return whether or not the user was successfully added
     */
    public synchronized boolean addnewUser(RestaurantUser ru){
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
    public synchronized boolean addnewRestaurant(Restaurant r){
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
    public synchronized boolean addReview(Review r){
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
    public synchronized boolean deleteReview(RestaurantUser ru, Restaurant r){
        //finding the review of a particular user to the particular restaurant
        for(Review rev : reviews){
            if(rev.business.equals(r) && rev.user.equals(ru)){
                userMap.get(rev.user).deleteReview(rev);
                restMap.get(rev.business).deleteReview(rev);
                this.reviews.remove(rev);
            }
        }
        return false;
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
    public Set<Restaurant> getMatches(String queryString){
        Set<Restaurant> retSet = new HashSet<>();

        for(Restaurant r : restaurants){
            //do some light manipulation to the string for better matches

            String[] query = queryString.toLowerCase().split("//s+");
            String tosearch = ".+";
            for(String s : query){
                tosearch += s + ".+";
            }

            //added is to slightly improve performance
            boolean added = false;
            if(r.city.toLowerCase().matches(tosearch) || r.full_address.toLowerCase().matches(tosearch) ||
                    r.state.toLowerCase().matches(tosearch) || r.name.toLowerCase().matches(tosearch)){
               retSet.add(r);
            }
            else {
                for (String s : r.neighbourhood) {
                    s.toLowerCase();
                    if (s.matches(tosearch)) {
                        added = true;
                        retSet.add(r);
                        break;
                    }
                }
                if(added) continue;
                for (String s : r.getCategory()) {
                    s.toLowerCase();
                    if (s.matches(tosearch)) {
                        added = true;
                        retSet.add(r);
                        break;
                    }
                }
                if(added) continue;
                for (String s : r.getSchool()) {
                    s.toLowerCase();
                    if (s.matches(tosearch)) {
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
            nodes.add(new Node());
            for(int ii = 0; i < amount && it.hasNext(); ii++){
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
                    double distance = nodes.get(i).calcdistance(memberlocation.get(s));
                    if(distance < shortestdistance){
                        assign = i;
                        shortestdistance = distance;
                    }
                }
                //if it wasn't the previously assigned node, change it
                if(!nodes.get(assign).getMembers().contains(s)){
                    moved = true;
                    nodes.get(assign).addmember(s, memberlocation.get(s));
                    for(Node n : nodes){
                        n.removemember(s);
                    }
                }
            }

            //calculate new locations
            for(Node n : nodes){
                n.calcNewLocation();
            }
        }

        //{"x": number, "y": number, "name": "string", "cluser": int, "weight": int}
        String retJson = "{";
        for(Node n : nodes){

        }


    }

    //used by k-means
    private class Node{
        Map<String, double[]> memberlocations;
        double[] location;

        Node(){
            memberlocations = new HashMap<>();
            location = new double[]{0, 0};
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

        private double calcdistance(double[] a){
            double dx = a[0] - location[0];
            double dy = a[1] - location[1];
            return Math.sqrt(dx*dx + dy*dy);
        }

        String toJsonString(){
            String json = "[";
            for(String s : memberlocations.keySet()){
                json += " " + s + ",";
            }
            if(json.length() > 1){
                json = json.substring(0, json.length() - 2) + "]";
            }
            else{
                json += "]";
            }

            return json;
        }
    }

    public ToDoubleBiFunction<MP5Db, String> getPredictorFunction(String user){
        return null;
    }

    public static void main(String[] args){
        YelpDataBase ydb = new YelpDataBase();
        System.out.println(ydb.getMatches("coffee"));

    }




}
