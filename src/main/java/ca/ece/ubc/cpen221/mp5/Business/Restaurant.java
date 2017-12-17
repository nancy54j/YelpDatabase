package ca.ece.ubc.cpen221.mp5.Business;

import ca.ece.ubc.cpen221.mp5.Business.Business;
import ca.ece.ubc.cpen221.mp5.Database.ParseJson;

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

    public void setPrice(int p){
        if(p < 0 || p > 5){
            throw new IllegalArgumentException();
        }
        else{
            this.price = p;
        }
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
        return ParseJson.restToJson(this);
    }

}
