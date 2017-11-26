package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class RestaurantUser implements User {
    private String UserID;
    private String name;
    private int aveStar;
    private Set<String> reviewID;

    public RestaurantUser(String name, String userID){
        this.name = name;
        this.aveStar = 0;
        this.UserID = userID;
        this.reviewID = new HashSet<>();
    }


    @Override
    public int GetAverageStar() {
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
    public void addReview(String id) {
        this.reviewID.add(id);
    }

    @Override
    public void deleteReview(String reviewID) {
        
    }
}