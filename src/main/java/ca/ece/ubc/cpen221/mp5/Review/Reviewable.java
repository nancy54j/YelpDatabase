package ca.ece.ubc.cpen221.mp5.Review;

import java.util.Set;

public interface Reviewable {

    /** get the rating /5 for a certain object
     * @return
     */
    double getRating();

    /**
     * add a review to the object
     * @param r
     */
    void addReview(Review r);

    /**
     * return the set of review id's that pertain to the object that calls this method
     * @return
     */
    Set<String> getReviews();

    /**
     * deletes a given review
     * @param r
     * @return
     */
    boolean deleteReview(Review r);
}
