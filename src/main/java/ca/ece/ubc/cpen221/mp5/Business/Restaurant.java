package ca.ece.ubc.cpen221.mp5.Business;

import ca.ece.ubc.cpen221.mp5.Business.Business;

import javax.json.*;
import java.util.*;

public class Restaurant extends Business {
    private static int gen_id = 0;

    public final String url;
    private int price;
    private String photo_url;
    private Set<String> categories;
    private Set<String> schools;

    //if loading one from the database
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

    //creating a new restaurant
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

    public void addSchool(String school){
        schools.add(school);
    }

    public void addCategory(String category){
        categories.add(category);
    }

    public Set<String> getCategory(){
        return new HashSet<>(categories);
    }

    public Set<String> getSchool(){
        return new HashSet<>(schools);
    }

    public int getPrice(){
        return this.price;
    }

    public String getPhotoUrl(){
        return photo_url;
    }

    @Override
    public String toString(){
        JsonBuilderFactory f = Json.createBuilderFactory(null);
        JsonArrayBuilder neighbourhoodarray = f.createArrayBuilder();
        for(String s : neighbourhood) {
            neighbourhoodarray.add(s);
        }
        JsonArray nArray = neighbourhoodarray.build();
        JsonArrayBuilder categoryarray = f.createArrayBuilder();
        for(String s : categories) {
            categoryarray.add(s);
        }
        JsonArray cArray = categoryarray.build();
        JsonArrayBuilder schoolarray = f.createArrayBuilder();
        for(String s : schools) {
            schoolarray.add(s);
        }
        JsonArray sArray = schoolarray.build();
        JsonObject restaurant = f.createObjectBuilder().add("open", "true").add("url", url)
                .add("longitude", super.getLocation()[1]).add("neighborhoods", nArray)
                .add("business_id", id).add("name", name).add("categories", cArray)
                .add("state", state).add("type", "business").add("stars", super.getRating())
                .add("city", city).add("full_address", full_address).add("review_count", super.getReviewCount())
                .add("photo_url", photo_url).add("schools", sArray).add("latitude", super.getLocation()[0])
                .add("price", price).build();

        return restaurant.toString();
    }

}
