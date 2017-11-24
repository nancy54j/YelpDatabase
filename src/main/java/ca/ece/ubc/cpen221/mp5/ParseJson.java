package ca.ece.ubc.cpen221.mp5;
import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParseJson {
    Set<Restaurant> restaurants;
    Set<Review> reviews;
    Set<User> users;
    Map<String, Restaurant> restMap;
    Map<String, User> userMap;


    //https://stackoverflow.com/questions/16265693/how-to-use-buffered-reader-in-java
    public ParseJson(String users, String businesses, String reviews){
        try {
            //parse restaurants
            BufferedReader restaurantReader = new BufferedReader(new FileReader(businesses));
            String line;
            while((line = restaurantReader.readLine()) != null){
                JsonParser parseRestaurant = Json.createParser(new StringReader(line));
                JsonObject restaurant = parseRestaurant.getObject();

                if(restaurant.getBoolean("open")){
                    String url = restaurant.getString("url");
                    double longitude = restaurant.getJsonNumber("longitude").doubleValue();
                    double latitude = restaurant.getJsonNumber("latitude").doubleValue();
                    //TODO: OVER HERE 
                    JsonArray a = restaurant.getJsonArray("neighborhoods")
                }
            }

            //parse reviews
            BufferedReader reviewReader = new BufferedReader(new FileReader(reviews));
            while((line = reviewReader.readLine()) != null){
                JsonParser parseReview = Json.createParser(new StringReader(line));
                JsonObject review = parseReview.getObject();

                //check if it is a date
                if(review.getString("type").equals("review")){
                    //convert date to unix time
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    int unixtime = (int) (df.parse(review.getString("date")).getTime() / 1000);

                    JsonObject jo = review.getJsonObject("votes");
                    int[] upvotes = new int[] {jo.getInt("cool"), jo.getInt("useful"), jo.getInt("funny")};

                    Review r = new Review(review.getJsonNumber("stars").doubleValue(),review.getString("text"),
                            review.getString("review_id"), review.getString("user_id"),
                            review.getString("business_id"), unixtime, upvotes);

                    this.reviews.add(r);

                }
            }
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    //returns a map of unique ids to its respective restaurant
    public Map<String, Restaurant> getRestaraunts(){
    }

    //returns a map of unique ids to its respective user
    public Map<String, User> getUsers(){
        
    }

    public Map<String,Set<Review>> restarauntReviews(){

    }

    public Map<String, Set<Review>> userReviews(){

    }

    public Set<Review> getReviews
}
