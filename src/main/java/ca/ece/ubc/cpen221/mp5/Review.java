package ca.ece.ubc.cpen221.mp5;

import java.util.Arrays;

public class Review {
    //TODO: add parameters
    public final String id;
    public final String user;
    public final String business;
    public final String text;
    public final int date;
    public final double rating;
    private static int gen_id = 0;
    private int[] upvotes = new int[] {0, 0, 0};

    //we do not need methods to return things because all of these variables are public
    //use unix encoding for date
    public Review(double starRating, String text, String id, String user, String business, int date, int[] upvotes){
        this.id = id;
        this.user = user;
        this.business = business;
        this.text = text;
        this.date = date;
        this.rating = starRating;
        this.upvotes = Arrays.copyOf(upvotes, 3); //ensures not copy by reference and saves size
    }

    //create new review
    public Review(double starRating, String user_id, String business_id){
        gen_id++;
        this.id = "+NEW+" + gen_id;
        this.user = user_id;
        this.business = business_id;
        this.rating = starRating;
        this.date = (int) (System.currentTimeMillis() / 1000);
        this.text = "";
    }

    String getID(){
        return id;
    }

    //create a new review with text
    public Review(double starRating, String text, String user_id, String business_id){
        gen_id++;
        this.id = "+NEW+" + gen_id;
        this.user = user_id;
        this.business = business_id;
        this.rating = starRating;
        this.date = (int) (System.currentTimeMillis() / 1000);
        this.text = text;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Review){
            Review r = (Review) o;
            return r.id.equals(this.id);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return user.hashCode() + business.hashCode();
    }
}
