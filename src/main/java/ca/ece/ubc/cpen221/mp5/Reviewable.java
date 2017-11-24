package ca.ece.ubc.cpen221.mp5;

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
}
