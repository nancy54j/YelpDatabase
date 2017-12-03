package ca.ece.ubc.cpen221.mp5.Part1;

import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Review.Review;
import ca.ece.ubc.cpen221.mp5.User.RestaurantUser;
import ca.ece.ubc.cpen221.mp5.User.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TestImplementation {
    Restaurant r1;
    Restaurant r2;
    RestaurantUser ru;
    RestaurantUser ru2;
    Review rev1;
    Review rev2;

    @Before
    public void setUp(){
        r1 = new Restaurant("12314", "Hello", "this.is.a.url.com",
                "2308 Unit", "Coquitlam", new String[] {"a", "b", "c"}, "BC",
                1231.2, 21.2, 2.0, 2, 3, Arrays.asList(new String[]
                {"takeout", "pizza"}), Arrays.asList(new String[] {"pinetree", "gleneagle"}),
                "photourl");
        r2 = new Restaurant("12314", "Hello", "this.is.a.url.com",
                "2308 Unit", "Port Coquitlam", new String[] {"a", "b", "c"}, "BC",
                1231.2, 21.2, 4.0, 20, 3, Arrays.asList(new String[]
                {"takeout", "pizza"}), Arrays.asList(new String[] {"pinetree", "gleneagle"}),
                "photourl");
        ru = new RestaurantUser("charles", "lol123", "www.yelp.lol", 2.3,
                new int[] {2, 3, 0}, 3);
        ru2 = new RestaurantUser("nancy", "lol123", "www.yelp.lol", 3.4,
                new int[] {76, 223, 123}, 40);
        rev1 = new Review(4.5, "good restaurant", "rev1", "lol123", "12314",
                20192, new int[] {3, 4, 5});
        rev2 = new Review(3.2, "bad restaurant", "rev1", "lol124", "12316",
                20192, new int[]{4,3,1});
    }
    //test the equals method of the restaurant
    @Test
    public void test1(){
        assertTrue(r1.equals(r2));
    }

    //tests if everything was inputted and saved correctly into restaurant
    @Test
    public void test2(){
        assertEquals(r1.url, "this.is.a.url.com");
        assertEquals(r1.city, "Coquitlam");
        assertEquals(r1.full_address, "2308 Unit");
        assertEquals(r1.name, "Hello");
        assertEquals(r1.state, "BC");
        assertTrue((new HashSet<String>(Arrays.asList(r1.neighbourhood))).contains("a"));
        assertTrue(r1.getCategory().contains("takeout"));
        assertEquals(r1.getPrice(), 3);
        assertEquals(r1.getPhotoUrl(), "photourl");
        assertTrue(r1.getSchool().contains("pinetree"));
        assertTrue(Math.abs(r1.getLocation()[0] - 1231.2) < 1e-9);
    }

    //tests other functions of restaurant
    @Test
    public void test3(){
        r1.addCategory("mexican");
        r1.addSchool("ubc");
        assertTrue(r1.getCategory().contains("mexican"));
        assertTrue(r1.getSchool().contains("ubc"));
    }

    //tests how restaurants interact with reviews
    @Test
    public void test4(){
        //checking conditions beforehand
        assertTrue(Math.abs(r1.getRating()-2) < 1e-9);
        assertEquals(r1.getReviewCount(), 2);
        //adding 2 different reviews, one of which should throw exception
        r1.addReview(rev1);
        try{
            r1.addReview(rev2);
            fail("should've thrown exception");
        }
        catch(Exception e){}
        //checking results
        assertTrue(Math.abs(r1.getRating()-3) < 1e-9);
        assertEquals(r1.getReviewCount(), 3);
        assertTrue(r1.getReviews().contains("rev1"));

        //deleting the review
        r1.deleteReview(rev1);
        assertTrue(Math.abs(r1.getRating() - 2) < 1e-9);
        assertEquals(r1.getReviewCount(), 2);
    }

    //generating a new restaurant
    @Test
    public void test5(){
        Restaurant r3 = new Restaurant(12.32, 231.3, "lol", new String[] {"a", "b"},
                "full_address", "city", "state");
        Restaurant r4 = new Restaurant(12.32, 231.3, "lol", new String[] {"a", "b"},
                "full_address", "city", "state");
        assertNotEquals(r3.id, r4.id);
    }

    //testing restauraunt user
    //see if everything was parameterized correctly
    @Test
    public void test6(){
        assertEquals(ru.url, "www.yelp.lol");
        assertEquals(ru.getName(), "charles");
        assertEquals(ru.UserID, "lol123");
        assertTrue(Math.abs(ru.getAverageStar() - 2.3) < 1e-9);
        assertEquals(ru.getVotes()[0], 2);
        assertEquals(ru.getReviewCount(), 3);

    }

    //tests if equals works
    @Test
    public void test7(){
        assertTrue(ru.equals(ru2));
    }

    //testing other methods
    @Test
    public void test8(){
        assertEquals(ru.getName(), "charles");
        ru.editName("nancy");
        assertEquals(ru.getName(), "nancy");
    }

    //testing interaction with reviews
    @Test
    public void test9(){
        assertEquals(ru.getReviewCount(), 3);
        assertTrue(Math.abs(ru.getAverageStar() - 2.3) < 1e-9);
        ru.addReview(rev1);
        assertTrue(Math.abs(ru.getAverageStar() - 2.85)< 1e-6);
        assertEquals(ru.getReviewCount(), 4);
        assertTrue(ru.getReviews().contains(rev1.id));
        ru.deleteReview(rev1);
        assertEquals(ru.getReviewCount(), 3);
        assertTrue(Math.abs(ru.getAverageStar() - 2.3) < 1e-9);
    }

    //testing generation of a new user
    @Test
    public void test10(){
        RestaurantUser ru3 = new RestaurantUser("holly");
        RestaurantUser ru4 = new RestaurantUser("hills");
        assertNotEquals(ru3.UserID, ru4.UserID);
        assertEquals(ru3.getVotes()[0], 0);
        assertTrue(ru3.getReviews().isEmpty());
    }

    //light tests for review
    //test if new review generation works
    @Test
    public void test11(){
        Review rev3 = new Review(4.5, "lol", "lol123", "lol1234");
        Review rev4 = new Review(4.5, "lol", "lol123", "lol1234");
        assertNotEquals(rev3.id, rev4.id);
    }

    //testing equals method
    @Test
    public void test12(){
        assertEquals(rev1, rev2);
    }



}
