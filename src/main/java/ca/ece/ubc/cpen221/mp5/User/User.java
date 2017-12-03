package ca.ece.ubc.cpen221.mp5.User;

import ca.ece.ubc.cpen221.mp5.Review.Review;

public interface User {

    //return average star rating of the user
    public double getAverageStar();

    //get review by id
    public String getReviewByID(String id);

    //edit name
    public void editName(String name);

    //add new review
    public boolean addReview(Review r);

    //delete review
    public boolean deleteReview(Review r);

}