package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RestaurantUser implements User{
    private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()";
    private String id;
    private String name;
    private int reviewCount;
    private int creationDate; //use unix time for this
    private String[] friends; //ID of all friends
    private int useful;
    private int funny;
    private int cool;
    private int fans;
    private int[] elite;
    private int avgRating;
    private int totalCompliments; //all compliments combined together

    public RestaurantUser(String id, String name, int reviewCount, int creationDate, String[] friends, int useful,
                          int funny, int cool, int fans, int[] elite, int avgRating, int totalCompliments){
        this.id = id;
        this.name = name;
        this.reviewCount = reviewCount;
        this.creationDate = creationDate;
        this.friends = friends;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.fans = fans;
        this.elite = elite;
        this.avgRating = avgRating;
        this.totalCompliments = totalCompliments;
    }
    //create a new restaurant user for the database
    public RestaurantUser(MP5Db<Restaurant> users, String name){
        Random random = new Random();
        String id = "";
        for(int i = 0; i < 22; i++){
            id += chars.charAt(random.nextInt() % 72);
        }
        this.id = id;
        this.name = name;
        this.creationDate = (int) System.currentTimeMillis() / 1000;
        this.useful = 0;
        this.funny = 0;
        this.cool = 0;
        this.fans = 0;
        this.avgRating = 0;
        this.totalCompliments = 0;
    }

    public Set<Review> getReviews(ReviewDataBase data){
        Set<Review> allReviews = data.returnData;
        Set<Review> retSet = new HashSet<>();
        for(Review r : allReviews){
            if(r.getID().equals(this.id)){
                retSet.add(r);
            }
        }
        return retSet;

    }


    public String getID(){
        return id;
    }
}
