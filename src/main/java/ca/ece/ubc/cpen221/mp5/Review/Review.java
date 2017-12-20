package ca.ece.ubc.cpen221.mp5.Review;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Arrays;
import java.util.Date;

/**
 * This class implements that abstract datatype of a review, which is mostly just a bunch of primitives
 * and different fields clumped together.
 *
 * Reviews are for the most part immutable, but all of its representation is exposed for easy access for
 * other methods to use.
 */
public class Review {
    //TODO: add parameters
    public final String id;
    public final String user;
    public final String business;
    public final String text;
    public final int date; //date is represented in unix time
    public final double rating;
    private static int gen_id = 0;
    private int[] upvotes = new int[] {0, 0, 0};

    /**
     * for constructing a review from a given dataset: all of the fields are set through parameters
     * @param starRating
     * @param text
     * @param id
     * @param user
     * @param business
     * @param date
     * @param upvotes
     */
    public Review(double starRating, String text, String id, String user, String business, int date, int[] upvotes){
        this.id = id;
        this.user = user;
        this.business = business;
        this.text = text;
        this.date = date;
        this.rating = starRating;
        this.upvotes = Arrays.copyOf(upvotes, 3); //ensures not copy by reference and saves size
    }

    /**
     * Creating a new review, and a unique id is generated for the given review. Can accept null strings
     * as text. Callers of this method should check that their string is not null before passing it in
     * here to avoid errors.
     * @param starRating
     * @param text
     * @param user_id
     * @param business_id
     */
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

    /**
     * Returns the given review in a json string format that matches the datasets.
     * @return
     */
    @Override
    public String toString(){
        JsonBuilderFactory f = Json.createBuilderFactory(null);
        JsonObject review = f.createObjectBuilder().add("type", "review")
                .add("votes", f.createObjectBuilder().add("cool", upvotes[0])
                        .add("useful", upvotes[1]).add("funny", upvotes[2]).build())
                .add("business_id", business).add("review_id", this.id).add("text",
                        this.text).add("stars", this.rating).add("user_id", this.user)
                .add("date", (new Date(this.date*1000)).toString()).build();

        return review.toString();
    }
}
