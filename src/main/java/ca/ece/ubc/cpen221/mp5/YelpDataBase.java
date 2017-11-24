package ca.ece.ubc.cpen221.mp5;

import java.util.Map;
import java.util.Set;

public class YelpDataBase implements MP5Db {
    Map<String, Review> allreviews;
    Set<RestaurantUser> allusers;
    Set<Restaurant> allrestaurants;
}
