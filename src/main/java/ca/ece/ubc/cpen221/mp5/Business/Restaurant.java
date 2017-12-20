package ca.ece.ubc.cpen221.mp5.Business;

import ca.ece.ubc.cpen221.mp5.Database.ParseJson;

import java.util.*;

/**
 * this is the restaurant implementation of the business. Some extra fields that it has includes an
 * online url and a photo, as well as other additional information.
 *
 * This class extends business, as you can tell below
 */
public class Restaurant extends Business {

    public final String url;
    private int price;
    private String photo_url;
    private Set<String> categories;
    private Set<String> schools;


    /**
     * Restaurant constructor used when initializing the database with pre-existing restaurants. All of the
     * fields within business are taken as parameters and set accordingly.
     *
     * This also calls the business constructor.
     *
     * @param business_id
     * @param name
     * @param url
     * @param full_address
     * @param city
     * @param neighbourhood
     * @param state
     * @param latitude
     * @param longitude
     * @param stars
     * @param reviewCount
     * @param price
     * @param categories
     * @param schools
     * @param photo_url
     */
    public Restaurant(String business_id, String name, String url, String full_address, String city,
                      String[] neighbourhood, String state, double latitude, double longitude, double stars,
                      int reviewCount, int price, Collection<? extends String> categories,
                      Collection<? extends String> schools, String photo_url){

        super(business_id, name, full_address, city, latitude, longitude, neighbourhood, state, stars, reviewCount);
        this.url = url;
        this.price = price;
        this.categories = new HashSet<>(categories);
        this.schools = new HashSet<>(schools);
        this.photo_url = photo_url;
    }

    /**
     * Restaurant constructor when creating a new restaurant. The caller of this method will have to specify
     * some internal fields, but others, like the business_id, is generated for the restaurant instead in
     * the super class.
     *
     * Business uses an internal static variable to keep track and assure that each created restaurant is
     * unique.
     */
    public Restaurant(double latitude, double longitude, String name, String[] neighbourhood, String full_address,
                      String city, String state){
        super(latitude, longitude, name, neighbourhood, full_address, city, state);

        //generating url
        String nameurl = name.replace(' ', '-');
        this.url = "http://www.yelp.com/biz/" + nameurl;
        this.price = 0;
        this.categories = new HashSet<>();
        this.schools = new HashSet<>();
        this.photo_url = "";
    }

    /**
     * to manually set or change the price rating of the restaurant
     * @param p
     */
    public void setPrice(int p){
        if(p < 0 || p > 5){
            throw new IllegalArgumentException();
        }
        else{
            this.price = p;
        }
    }

    /**
     * add a new school to the list of schools in which someone can find this restaurant by.
     * @param school
     */
    public void addSchool(String school){
        schools.add(school);
    }

    /**
     * add a new category to the list of categories that pertain to this restaurant
     * @param category
     */
    public void addCategory(String category){
        categories.add(category);
    }

    /**
     * returns the set of categories pertaining to this restaurant
     * @return
     */
    public Set<String> getCategory(){
        return new HashSet<>(categories);
    }

    /**
     * returns the set of schools that pertains to this restaurant
     * @return
     */
    public Set<String> getSchool(){
        return new HashSet<>(schools);
    }

    /**
     * returns the current price rating of the restaurant
     * @return
     */
    public int getPrice(){
        return this.price;
    }

    /**
     * returns the photourl of the restaurant if there is one. Returns null otherwise
     * @return
     */
    public String getPhotoUrl(){
        return photo_url;
    }

    /**
     * returns a string representation of this restaurant in JsonObject form
     * @return
     */
    @Override
    public String toString(){
        return ParseJson.restToJson(this);
    }

}
