package ca.ece.ubc.cpen221.mp5.User;

import ca.ece.ubc.cpen221.mp5.Review.Review;

import javax.json.*;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class RestaurantUser implements User {
    private static int gen_id;
    public final String UserID;
    public final String url;
    private String name;
    private double aveStar;
    private int reviewCount;
    private Set<String> reviewID;
    private int[] votes;//funny useful cool

    public RestaurantUser(String name, String userID, String url, double aveStar, int[] votes, int reviewCount){
        this.name = name;
        this.aveStar = aveStar;
        this.url = url;
        this.UserID = userID;
        this.reviewID = new HashSet<>();
        this.votes = votes;
        this.reviewCount = reviewCount;
    }

    //create new user
    public RestaurantUser(String name){
        this.name = name;
        this.UserID = "+NEW+" + gen_id++;
        this.url = "http://www.yelp.com/user_details?userid=" + gen_id;
        this.aveStar = 0;
        reviewID = new HashSet<>();
        votes = new int[] {0, 0, 0};
        this.reviewCount = 0;
    }

    public int getReviewCount(){
        return reviewCount;
    }

    public Set<String> getReviews(){
        return new HashSet<>(this.reviewID);
    }


    @Override
    public double getAverageStar() {
        return aveStar;
    }

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

    @Override
    public void editName(String name) throws IllegalArgumentException{
        if(name != "") {
            this.name = name;
        }
        else{
            throw new IllegalArgumentException("Name must be a valid string");
        }
    }

    @Override
    public boolean addReview(Review r) {
        if(r.user.equals(this.UserID)){
            aveStar = (aveStar*reviewCount + r.rating) / ++reviewCount;
            this.reviewID.add(r.id);
            return true;
        }
        return false;
    }

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

    public String getName(){
        return new String(this.name);
    }

    public int[] getVotes(){
        return votes;
    }

    //need to add review ids to the set without changing the number of reviews/ratings, etc
    public void addReviewinitialize(String s){
        this.reviewID.add(s);
    }

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