package ca.ece.ubc.cpen221.mp5;
import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParseJson {
    Set<Restaurant> restaurants;
    Set<Review> reviews;
    Set<RestaurantUser> users;
    Map<String, Restaurant> restMap;
    Map<String, RestaurantUser> userMap;


    //https://stackoverflow.com/questions/16265693/how-to-use-buffered-reader-in-java
    public ParseJson(String users, String businesses, String reviews){
        this.restaurants = new HashSet<>();
        this.reviews = new HashSet<>();
        this.users = new HashSet<>();
        this.restMap = new HashMap<>();
        this.userMap = new HashMap<>();

        try {
            //parse restaurants
            BufferedReader restaurantReader = new BufferedReader(new FileReader(businesses));
            String line;
            while((line = restaurantReader.readLine()) != null){
                StringReader sr = new StringReader(line);
                JsonReader parseRestaurant = Json.createReader(sr);
                JsonObject restaurant = parseRestaurant.readObject();

                if(restaurant.getBoolean("open") && restaurant.getString("type").equals("business")){
                    String url = restaurant.getString("url");
                    double longitude = restaurant.getJsonNumber("longitude").doubleValue();
                    double latitude = restaurant.getJsonNumber("latitude").doubleValue();
                    JsonArray a = restaurant.getJsonArray("neighborhoods");
                    //make a neighborhood string[]
                    String[] neighborhood = new String[a.size()];
                    int i = 0;
                    for(JsonValue s : a){
                        neighborhood[i] = s.toString();
                        i++;
                    }
                    String business_id = restaurant.getString("business_id");
                    String name = restaurant.getString("name");
                    //parse categories
                    JsonArray cat = restaurant.getJsonArray("categories");
                    Set<String> categories = new HashSet<>();
                    for(JsonValue val : cat) {
                        categories.add(val.toString());
                    }
                    String state = restaurant.getString("state");
                    double stars = restaurant.getJsonNumber("stars").doubleValue();
                    String city = restaurant.getString("city");
                    String full_address = restaurant.getString("full_address");
                    int reviewCount = restaurant.getInt("review_count");
                    //parse schools
                    JsonArray school = restaurant.getJsonArray("schools");
                    Set<String> schools = new HashSet<>();
                    for(JsonValue val : school){
                        schools.add(val.toString());
                    }
                    int price = restaurant.getInt("price");

                    Restaurant r = new Restaurant(business_id, name, url, full_address, city, neighborhood, state,
                            latitude, longitude, stars, reviewCount, price, categories, schools);

                    restMap.put(business_id, r);
                    restaurants.add(r);
                }
            }

            //parse users
            BufferedReader userReader = new BufferedReader(new FileReader(users));
            while((line = userReader.readLine()) != null){
                JsonReader parseUser = Json.createReader(new StringReader(line));
                JsonObject user = parseUser.readObject();

                if(user.getString("type").equals("user")) {
                    String url = user.getString("url");
                    JsonObject vote = user.getJsonObject("votes");
                    int[] votes = new int[]{vote.getInt("funny"), vote.getInt("useful"), vote.getInt("cool")};
                    int reviewCount = user.getInt("review_count");
                    String user_id = user.getString("user_id");
                    String name = user.getString("name");
                    double aveStar = user.getJsonNumber("average_stars").doubleValue();

                    RestaurantUser ru = new RestaurantUser(name, user_id, url, aveStar, votes, reviewCount);
                    userMap.put(user_id, ru);
                    this.users.add(ru);
                }

            }

            //parse reviews
            BufferedReader reviewReader = new BufferedReader(new FileReader(reviews));
            while((line = reviewReader.readLine()) != null){
                JsonReader parseReview = Json.createReader(new StringReader(line));
                JsonObject review = parseReview.readObject();

                //check if it is a date
                if(review.getString("type").equals("review")){
                    //convert date to unix time
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    int unixtime = (int) (df.parse(review.getString("date")).getTime() / 1000);
                    //combine cool useful and funny together
                    JsonObject jo = review.getJsonObject("votes");
                    int[] upvotes = new int[] {jo.getInt("cool"), jo.getInt("useful"), jo.getInt("funny")};

                    Review r = new Review(review.getJsonNumber("stars").doubleValue(),review.getString("text"),
                            review.getString("review_id"), review.getString("user_id"),
                            review.getString("business_id"), unixtime, upvotes);

                    this.reviews.add(r);

                    //add the id of the review to the user it belongs to
                    if(userMap.keySet().contains(r.user)){
                        userMap.get(r.user).addReview(r);
                    }

                    if(restMap.keySet().contains(r.business)){
                        restMap.get(r.business).addReview(r);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("SOMETHING HAPPENED");
        }
    }

    //returns a map of unique ids to its respective restaurant
    public Map<String, Restaurant> getrestMap(){
        return restMap;
    }

    //returns a map of unique ids to its respective user
    public Map<String, RestaurantUser> getUserMap(){
        return userMap;
    }

    public Set<Restaurant> getRestaurants(){
        return restaurants;
    }

    public Set<RestaurantUser> getUsers(){
        return users;
    }

    public Set<Review> getReviews(){
        return reviews;
    }
}
