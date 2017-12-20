package ca.ece.ubc.cpen221.mp5.Business;

import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.Review.Reviewable;

import java.util.HashSet;
import java.util.Set;

/**
 * Generic Business class. This represents the abstract datatype of a business, of which can be reviewed,
 * and must have a physical location.
 *
 * A design choice was taken in making a bunch of the variables final and public so we can easily access
 * parameters of business without compromising mutability and introducing more wrapper functions
 */
public class Business implements Reviewable {
    private static int gen_id = 0;

    public final String id;
    public final String name;
    public final String full_address;
    public final String city;
    public final String[] neighbourhood;
    public final String state;
    private double latitude;
    private double longitude;
    private double stars; //between 0 and 5;
    private int reviewCount;
    private Set<String> reviews;

    /**
     * Business constructor used when initializing the database with pre-existing businesses. All of the
     * fields within business are taken as parameters and set accordingly.
     * @param business_id
     * @param name
     * @param full_address
     * @param city
     * @param latitude
     * @param longitude
     * @param neighbourhood
     * @param state
     * @param stars
     * @param reviewCount
     */
    public Business(String business_id, String name, String full_address, String city, double latitude,
                    double longitude, String[] neighbourhood, String state, double stars, int reviewCount){
        this.id = business_id;
        this.name = name;
        this.full_address = full_address;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.state = state;
        this.stars = stars;
        this.reviewCount = reviewCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = new HashSet<>();
    }

    /**
     * Business constructor when creating a new business. The caller of this method will have to specify
     * some internal fields, but others, like the business_id, is generated for the business instead.
     *
     * This class uses an internal static variable to keep track and assure that each created restaurant is
     * unique.
     *
     * @param latitude
     * @param longitude
     * @param name
     * @param neighbourhood
     * @param full_address
     * @param city
     * @param state
     */
    public Business(double latitude, double longitude, String name, String[] neighbourhood, String full_address,
                    String city, String state){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.full_address = full_address;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.state = state;
        this.stars = 0;
        this.reviewCount = 0;
        this.reviews = new HashSet<>();

        this.id = "+NEWREST+" + gen_id++;
    }

    /** Gets the longtitude and latitude of a location in the form of a double array
     *
     * The first index is the latitude and the second the longitude.
     *
     * @return
     */
    public double[] getLocation(){
        return new double[] {latitude, longitude};
    }

    /**
     * Takes in a review object and checks to see if that review is in fact directed at this restaurant
     * If it is, then a new average rating is calculated and the review id is recorded
     *
     * @param r
     */
    public void addReview(Review r){
        if(!r.business.equals(id)){
            throw new IllegalArgumentException("Review must pertain to this restaurant");
        }
        stars = (stars*reviewCount + r.rating) / ++reviewCount;
        this.reviews.add(r.id);
    }

    /**
     * Returns the rating of the restaurant in incremends of .5 stars
     * @return
     */
    public double getRating(){
        return Math.round(stars * 2) / 2f;
    }

    /**
     * returns a new set of all the reviews that pertain to this restaurant.
     * @return
     */
    public Set<String> getReviews(){
        return new HashSet<>(reviews);
    }

    /**
     * used for clearing a review directed at this restaurant. This is mostly called from the database when
     * a user attempts to create a second review of the same restaurant. It also recalculates the average
     * star rating of the restaurant.
     * @param r
     * @return
     */
    public boolean deleteReview(Review r){
        if(this.reviews.contains(r.id)){
            this.reviews.remove(r.id);
            stars = (stars*reviewCount - r.rating)/ --reviewCount;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Restaurant){
            Restaurant r = (Restaurant) o;
            return r.id.equals(this.id);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    /**
     * self explanatory
     * @return
     */
    public int getReviewCount(){
        return reviewCount;
    }

    /**
     * this is for the intialization of the database, when adding review information to this restaurant
     * shouldn't change the internal rating of the restaurant.
     * @param s
     */
    public void addReviewinitialize(String s){
        this.reviews.add(s);
    }


}
