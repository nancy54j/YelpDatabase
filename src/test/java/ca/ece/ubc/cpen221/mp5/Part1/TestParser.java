package ca.ece.ubc.cpen221.mp5.Part1;

import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Database.ParseJson;
import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.User.RestaurantUser;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestParser {
    ParseJson pj;
    Restaurant r;
    Review rev;
    RestaurantUser ru;

    @Before
    public void setUp(){
        pj = new ParseJson("./data/users.json", "./data/restaurants.json",
                "./data/reviews.json");
        //these parameters are going to be manually inputed:
        r = new Restaurant("gclB3ED6uk6viWlolSb_uA", "Cafe 3", "http://www.yelp.com/biz/cafe-3-berkeley",
                "2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701", "Berkeley",
                new String[] {"Telegraph Ave", "UC Campus Area"}, "CA", 37.867417,
                -122.260408, 2.0, 9, 1, Arrays.asList(new String[] {"Cafes", "Restaurants"}),
                Arrays.asList(new String[] {"University of California at Berkeley"}),
                "http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg");
        rev = new Review(2.0, "The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\n\nOh, and the pasta is even worse than the pizza.",
                "0a-pCW4guXIlWNpVeBHChg", "90wm_01FAIqhcgV_mPON9Q", "1CBs84C-a-cuA3vncXVSAw", 23213,
                new int[]{0, 0, 0});
        ru = new RestaurantUser("Chris M.", "_NH7Cpq3qZkByP5xR4gXog",
                "http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog", 3.89655172413793,
                new int[] {35, 21, 14}, 29);
    }

    //testing restaurant json parser
    @Test
    public void testrestaurant(){
        Restaurant r1 = ParseJson.parseJsonRestaurant(Json.createReader(new StringReader("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}")).readObject());
        assertTrue(r1.equals(r));
        assertEquals(r1.url, r.url);
        assertEquals(r1.getPrice(), r.getPrice());
    }

    @Test
    public void testuser(){
        RestaurantUser ru1 = ParseJson.parseJsonRestUser(Json.createReader(new StringReader("{\"url\": \"http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog\", \"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14}, \"review_count\": 29, \"type\": \"user\", \"user_id\": \"_NH7Cpq3qZkByP5xR4gXog\", \"name\": \"Chris M.\", \"average_stars\": 3.89655172413793}")).readObject());
        assertTrue(ru1.equals(ru));
        assertEquals(ru1.getName(), ru.getName());
        assertEquals(ru1.hashCode(), ru.hashCode());
        assertEquals(ru1.url, ru.url);
    }

    @Test
    public void testreview() throws ParseException {
        Review rev1 = ParseJson.parseJsonReview(Json.createReader(new StringReader("{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}")).readObject());
        assertTrue(rev1.equals(rev));
        assertEquals(rev1.business, rev.business);
        assertEquals(rev1.text, rev.text);
    }

    //test the entire parser
    @Test
    public void testentireparser() {
        assertTrue(pj.getRestaurants().contains(r));
        assertTrue(pj.getUsers().contains(ru));
        assertTrue(pj.getReviews().contains(rev));
    }

    @Test
    public void testentireparser1(){
        //see if the size is the same as how many lines there are in the 3 json files
        assertEquals(pj.getrestMap().size(), 135);
        assertEquals(pj.getUserMap().size(), 8556);
        assertEquals(pj.getReviews().size(), 17396);
    }
}

