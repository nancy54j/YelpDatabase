package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
        ParseJson pj = new ParseJson("./data/dataset/users.json", "./data/dataset/restaurants.json",
                "./data/dataset/reviews.json");

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
    public Set<String> getMatches(String queryString){

    }

    /**
     * returns a cluster of restaurants in json format based on the lat/long (rough location) of them,
     * such that no restaurant is closer to another node than the one it is assigned to
     * @param k
     *            number of clusters to create (0 < k <= number of objects)
     * @return
     */
    public String kMeansClusters_json(int k){
        return "lol";
    }

    public ToDoubleBiFunction<MP5Db, String> getPredictorFunction(String user){
        return null;
    }

    public static void main(String[] args){
        YelpDataBase ydb = new YelpDataBase();

    }




}
