package ca.ece.ubc.cpen221.mp5;

public interface User {

    //return average star rating of the user
    public int GetAverageStar();

    //get review by id
    public String getReviewByID(String id);

    //edit name
    public void editName(String name);

    //add new review
    public void addReview(String id);

    //delete review
    public void deleteReview(String reviewID);

}