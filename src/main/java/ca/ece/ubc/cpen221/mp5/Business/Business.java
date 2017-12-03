package ca.ece.ubc.cpen221.mp5.Business;

import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.Review.Reviewable;

import java.util.HashSet;
import java.util.Set;

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

        this.id = "+NEW+" + gen_id++;
    }

    //gets the longtitude and latitude of a location
    public double[] getLocation(){
        return new double[] {latitude, longitude};
    }

    public void addReview(Review r){
        if(!r.business.equals(id)){
            throw new IllegalArgumentException("Review must pertain to this restaurant");
        }
        stars = (stars*reviewCount + r.rating) / ++reviewCount;
        this.reviews.add(r.id);
    }

    //return the rating in increments of .5's
    public double getRating(){
        return Math.round(stars * 2) / 2f;
    }

    public Set<String> getReviews(){
        return reviews;
    }

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

    public int getReviewCount(){
        return reviewCount;
    }

    //need to add review ids to the set without changing the number of reviews/ratings, etc
    public void addReviewinitialize(String s){
        this.reviews.add(s);
    }


}
