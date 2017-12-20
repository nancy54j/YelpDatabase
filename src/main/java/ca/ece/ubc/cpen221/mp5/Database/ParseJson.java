package ca.ece.ubc.cpen221.mp5.Database;

import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.User.RestaurantUser;

import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is a utility class just used to parse given the json files that were given to us, and contains other useful
 * methods when performing operations related to json format things, like converting a restaurant object
 * into a json string or converting a generics array into a jsonarray.
 */
public class ParseJson {
    Set<Restaurant> restaurants;
    Set<Review> reviews;
    Set<RestaurantUser> users;
    Map<String, Restaurant> restMap;
    Map<String, RestaurantUser> userMap;
    Map<String, Review> reviewMap;


    //https://stackoverflow.com/questions/16265693/how-to-use-buffered-reader-in-java

    /**
     * the main constructor of the object, which takes in the three file locations of the datasets in
     * json form, and does all of the parsing within the constructor. Exceptions related to files and
     * parsers are caught and printed on the screen, allowing the parser to skip some sections.
     *
     * @param users
     * @param businesses
     * @param reviews
     */
    public ParseJson(String users, String businesses, String reviews){
        this.restaurants = new HashSet<>();
        this.reviews = new HashSet<>();
        this.users = new HashSet<>();
        this.restMap = new HashMap<>();
        this.userMap = new HashMap<>();
        this.reviewMap = new HashMap<>();

        try {
            //parse restaurants
            BufferedReader restaurantReader = new BufferedReader(new FileReader(businesses));
            String line;
            while((line = restaurantReader.readLine()) != null){
                JsonObject restaurant = Json.createReader(new StringReader(line)).readObject();

                if(restaurant.getBoolean("open") && restaurant.getString("type").equals("business")){
                    Restaurant r = parseJsonRestaurant(restaurant);
                    restMap.put(r.id, r);
                    restaurants.add(r);
                }
            }

            //parse users
            BufferedReader userReader = new BufferedReader(new FileReader(users));
            while((line = userReader.readLine()) != null){
                JsonObject user = Json.createReader(new StringReader(line)).readObject();

                if(user.getString("type").equals("user")) {
                    RestaurantUser ru = parseJsonRestUser(user);
                    userMap.put(ru.UserID, ru);
                    this.users.add(ru);
                }
            }

            //parse reviews
            BufferedReader reviewReader = new BufferedReader(new FileReader(reviews));
            while((line = reviewReader.readLine()) != null){
                JsonObject review = Json.createReader(new StringReader(line)).readObject();

                //check if it is a date
                if(review.getString("type").equals("review")){

                    Review r = parseJsonReview(review);

                    //add the id of the review to the user it belongs to
                    if(userMap.keySet().contains(r.user)){
                        userMap.get(r.user).addReviewinitialize(r.id);
                        this.reviews.add(r);
                        this.reviewMap.put(r.id, r);

                    }
                    if(restMap.keySet().contains(r.business)){
                        restMap.get(r.business).addReviewinitialize(r.id);
                        this.reviews.add(r);
                        this.reviewMap.put(r.id, r);
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println("file problem");
        }
        catch(ParseException e){
            System.out.println("parse problem");
        }
    }

    /**
     * creates a Review object given the json string representation of the review
     * It will throw a nullpointerexception of it tries to find json categories that are not present
     * in the string.
     * @param review
     * @return
     * @throws ParseException if the date cannot be converted into unix time
     */
    public static Review parseJsonReview (JsonObject review) throws ParseException{
        //convert date to unix time
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int unixtime = (int) (df.parse(review.getString("date")).getTime() / 1000);
        //combine cool useful and funny together
        JsonObject jo = review.getJsonObject("votes");
        int[] upvotes = new int[] {jo.getInt("cool"), jo.getInt("useful"), jo.getInt("funny")};

        return new Review(review.getJsonNumber("stars").doubleValue(),review.getString("text"),
                review.getString("review_id"), review.getString("user_id"),
                review.getString("business_id"), unixtime, upvotes);
    }

    /**
     * creates a restaurantuser object given the json string representation of the restaurantuser.
     * It will throw a nullpointerexception if it tries to find a json category that isn't present
     * in the string.
     * @param user
     * @return
     */
    public static RestaurantUser parseJsonRestUser(JsonObject user){
        String url = user.getString("url");
        JsonObject vote = user.getJsonObject("votes");
        int[] votes = new int[]{vote.getInt("funny"), vote.getInt("useful"), vote.getInt("cool")};
        int reviewCount = user.getInt("review_count");
        String user_id = user.getString("user_id");
        String name = user.getString("name");
        double aveStar = user.getJsonNumber("average_stars").doubleValue();

        return new RestaurantUser(name, user_id, url, aveStar, votes, reviewCount);
    }

    /**
     * creates a restaurant object given the json string representation of the restaurant.
     * It will throw a nullpointerexception if it tries to find a json category that isn't present
     * in the string.
     * @param restaurant
     * @return
     */
    public static Restaurant parseJsonRestaurant(JsonObject restaurant){
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
        String photo_url = restaurant.getString("photo_url");

        return new Restaurant(business_id, name, url, full_address, city, neighborhood, state,
                latitude, longitude, stars, reviewCount, price, categories, schools, photo_url);
    }

    /**
     * this does the opposite of parseJsonRestaurant, in that it changes a restaurant object back into
     * a Json string. Can be considered the inverse function of ParseJsonRestaurant
     * @param r
     * @return
     */
    public static String restToJson(Restaurant r){
        JsonBuilderFactory f = Json.createBuilderFactory(null);
        JsonArrayBuilder neighbourhoodarray = f.createArrayBuilder();
        for(String s : r.neighbourhood) {
            neighbourhoodarray.add(s);
        }
        JsonArray nArray = neighbourhoodarray.build();
        JsonArrayBuilder categoryarray = f.createArrayBuilder();
        for(String s : r.getCategory()) {
            categoryarray.add(s);
        }
        JsonArray cArray = categoryarray.build();
        JsonArrayBuilder schoolarray = f.createArrayBuilder();
        for(String s : r.getSchool()) {
            schoolarray.add(s);
        }
        JsonArray sArray = schoolarray.build();
        JsonObject restaurant = f.createObjectBuilder().add("open", "true").add("url", r.url)
                .add("longitude", r.getLocation()[1]).add("neighborhoods", nArray)
                .add("business_id", r.id).add("name", r.name).add("categories", cArray)
                .add("state", r.state).add("type", "business").add("stars", r.getRating())
                .add("city", r.city).add("full_address", r.full_address).add("review_count", r.getReviewCount())
                .add("photo_url", r.getPhotoUrl()).add("schools", sArray).add("latitude", r.getLocation()[0])
                .add("price", r.getPrice()).build();

        return restaurant.toString();
    }

    /**
     * converts any sort of collection object into a Json string representation using the
     * toString() method of the object.
     * @param c
     * @param <T>
     * @return
     */
    public static <T> String arrayToJson(Collection<T> c){
        JsonBuilderFactory f = Json.createBuilderFactory(null);
        JsonArrayBuilder retArray = f.createArrayBuilder();
        for(T s : c){
            retArray.add(s.toString());
        }

        return retArray.toString();
    }

    /**
     * this returns the map of restaurant id -> restaurant object after the Json constructor has finished
     * parsing the dataset
     * @return
     */
    public Map<String, Restaurant> getrestMap(){
        return restMap;
    }

    /**
     * returns the map of restaurant user id -> restaurant user object after the Json constructor has
     * finished parsing the user dataset
     * @return
     */
    public Map<String, RestaurantUser> getUserMap(){
        return userMap;
    }

    /**
     * returns the map of review id -> review object after the Json constructor has finished
     * parsing the review dataset
     * @return
     */
    public Map<String, Review> getReviewMap(){
        return reviewMap;
    }

    /**
     * returns a set of restaurant objects from the dataset
     * @return
     */
    public Set<Restaurant> getRestaurants(){
        return restaurants;
    }

    /**
     * returns a set of user objects from the dataset
     * @return
     */
    public Set<RestaurantUser> getUsers(){
        return users;
    }

    /**
     * returns a set of review objects from the dataset
     * @return
     */
    public Set<Review> getReviews(){
        return reviews;
    }
}
