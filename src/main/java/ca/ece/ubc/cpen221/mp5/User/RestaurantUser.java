package ca.ece.ubc.cpen221.mp5.User;

import ca.ece.ubc.cpen221.mp5.Review.Review;

import javax.json.*;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Implementation of the RestaurantUser database, this class implements what a User should do,
 * and is capable of creating new users, etc and interfaces with restaurants and reviews.
 * Like restaurant and review, restaurantuser has some of its representation exposed so code is more
 * readable to others.
 */
public class RestaurantUser implements User {
    private static int gen_id;
    public final String UserID;
    public final String url;
    private String name;
    private double aveStar;
    private int reviewCount;
    private Set<String> reviewID;
    private int[] votes;//funny useful cool

    /**
     * creates an existing user from a given RestaurantUser json string. All of the fields of this class
     * are taken in as parameters and set accordingly.
     * @param name
     * @param userID
     * @param url
     * @param aveStar
     * @param votes
     * @param reviewCount
     */
    public RestaurantUser(String name, String userID, String url, double aveStar, int[] votes, int reviewCount){
        this.name = name;
        this.aveStar = aveStar;
        this.url = url;
        this.UserID = userID;
        this.reviewID = new HashSet<>();
        this.votes = votes;
        this.reviewCount = reviewCount;
    }

    /**
     * creates a new user. The name is the only information that is needed, and the rest, like id and votes,
     * etc are all either initialized or generated appropriately
     * @param name
     */
    public RestaurantUser(String name){
        this.name = name;
        this.UserID = "+NEWUSER+" + gen_id++;
        this.url = "http://www.yelp.com/user_details?userid=" + UserID;
        this.aveStar = 0;
        reviewID = new HashSet<>();
        votes = new int[] {0, 0, 0};
        this.reviewCount = 0;
    }

    /**
     * self-explanatory
     * @return
     */
    public int getReviewCount(){
        return reviewCount;
    }

    /**
     * returns the set of review ids that pertains to this user
     * @return
     */
    public Set<String> getReviews(){
        return new HashSet<>(this.reviewID);
    }

    /**
     * returns the average rating that this user gives restaurants
     * @return
     */
    @Override
    public double getAverageStar() {
        return aveStar;
    }

    /**
     * all this method does is verify that a certain review id corresponds to this user
     * (pretty useless function, I don't know why we need it)
     * @param id
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public String getReviewByID(String id) throws NoSuchElementException{
        for(String id_internal: reviewID){
            // add getID method to review
            if(id_internal == id){
                return id_internal;
            }
        }
        throw new NoSuchElementException("Review id does not match any in database");
    }

    /**
     * For changing the name of the user: checks if the name is valid, and then changes the name
     *
     * @param name
     * @throws IllegalArgumentException
     */
    @Override
    public void editName(String name) throws IllegalArgumentException{
        if(name != "") {
            this.name = name;
        }
        else{
            throw new IllegalArgumentException("Name must be a valid string");
        }
    }

    /**
     * Checks to see if a review pertains to this user, and if it does, adjusts the user's rating
     * accordingly and adds it to this set.
     * @param r
     * @return
     */
    @Override
    public boolean addReview(Review r) {
        if(r.user.equals(this.UserID)){
            aveStar = (aveStar*reviewCount + r.rating) / ++reviewCount;
            this.reviewID.add(r.id);
            return true;
        }
        return false;
    }

    /**
     * checks to see if the review relates to this user, and if it does, remove the relation and adjust the
     * rating of the user accordingly.
     * @param r
     * @return
     */
    @Override
    public boolean deleteReview(Review r) {
        if(this.reviewID.contains(r.id)){
            this.reviewID.remove(r.id);
            aveStar = (aveStar*reviewCount - r.rating) / --reviewCount;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof RestaurantUser){
            RestaurantUser ru = (RestaurantUser) o;
            return ru.UserID.equals(this.UserID);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return votes[0] + reviewCount;
    }

    /**
     * returns the name of the user. Multiple users can have the same name
     * @return
     */
    public String getName(){
        return new String(this.name);
    }

    /**
     * returns the set of ratings that correspond to this user's reviews, in the order of:
     * funny useful cool
     *
     * @return
     */
    public int[] getVotes(){
        return votes;
    }

    /**
     * this method provides us a way to add reviews corresponding to this user without actually altering
     * the user's rating: used for the initialization of the dataset
     * @param s
     */
    public void addReviewinitialize(String s){
        this.reviewID.add(s);
    }

    /**
     * returns the json string representation of the review, identical to that of the datasets.
     * @return
     */
    @Override
    public String toString(){
        JsonBuilderFactory f = Json.createBuilderFactory(null);
        JsonObject restUser = f.createObjectBuilder().add("url", this.url).add("votes",
                f.createObjectBuilder().add("funny", votes[0]).add("useful", votes[1])
                .add("cool", votes[2]).build()).add("review_count", reviewCount)
                .add("type", "user").add("user_id", this.UserID).add("name",
                this.name).add("average_stars", this.aveStar).build();

        return restUser.toString();
    }

}