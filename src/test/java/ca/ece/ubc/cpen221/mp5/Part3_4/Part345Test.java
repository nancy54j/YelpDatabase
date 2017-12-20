package ca.ece.ubc.cpen221.mp5.Part3_4;

import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import ca.ece.ubc.cpen221.mp5.Server.YelpClient;
import ca.ece.ubc.cpen221.mp5.Server.YelpServer;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * We bunched part 3-5 testing together because all of them require interfacing with the server, and were
 * designed like that, so it is quite hard to test it individually.
 *
 * The structure of the test works with a
 * @BeforeClass to initialize a thread with the server running, and then the individual tests themselves
 * wil create clients that connect to the server and make the necessary requests.
 *
 * Also This test is structured in a way such that there is an order to testing - certain tests depend
 * on previous tests to pass: for example a later test might assume that a former test has successfully
 * added a new user and will try and create a review given that user. Hence, we have a @FixMethodOrder.
 *
 * Because the server mostly deals with strings instead of java objects. The main style of testing here is
 * checking if the json string representations of objects contains certain parameters and constants that
 * we are looking for.
 *
 * For example, if we wanted to check if a particular return json string "rest" represented a particular
 * restaurant, we would write in our test rest.matches(".*<rest_id>*), where <rest_id> is the particular
 * restaurant's id.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part345Test {

    //create a separate thread with the server running - this should be the only thing I need to test part 3-5

    //this method keeps track of the number of new restaurants that have been made
    static int numNewRest = -1;

    @BeforeClass
    public static void AsetUp() throws IOException{
        YelpServer ys = new YelpServer();
        Runnable serverrun = new Runnable(){
            public void run(){
                try{
                    ys.serve();
                }
                catch(Exception e){}
            }
        };
        YelpDataBase ydb = new YelpDataBase();
        Thread server = new Thread(serverrun);
        server.start();

    }

    //test two different restaurants to see if threading on the server's behalf works and can process
    //multiple requests
    @Test
    public void Btest1(){
        try {
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("GETRESTAURANT BJKIoQa5N2T_oDlLVf467Q");
            client.sendRequest("GETRESTAURANT e_8TvfKT6QT81snfrqYYTw");
            assertTrue(client.getReply().matches(".*\"name\":\"Jasmine Thai\".*"));
            //see if the array has been printed properly
            System.out.println(client.getReply().matches(".*\"neighborhoods\": [\"Downtown Berkeley\", \"UC Campus Area\"].*"));
        } catch (IOException e) {
            fail("Should not have thrown exception");
            e.printStackTrace();
        }
    }

    //trying invalid getrestaurant strings
    @Test
    public void Ctest2(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("GETRESTAURANT asldkfnlsad");
            assertTrue(client.getReply().matches("ERR: NO_SUCH_RESTAURANT"));
        }
        catch(IOException e){
            fail("Exception thrown: shouldn't have failed");
        }
    }

    //random string which shouldn't work
    @Test
    public void Dtest3(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("Invalid request");
            assertTrue(client.getReply().matches("ERR: ILLEGAL_REQUEST"));
            client.sendRequest("ADDUSERR SLKANL");
            assertTrue(client.getReply().matches("ERR: ILLEGAL_REQUEST"));
        }
        catch(IOException e){
            fail("Exception thrown: shouldn't have failed");
        }
    }

    //adding multiple users, some of which have more information that required
    @Test
    public void Etest4(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDUSER {\"name\": \"Sathish G.\"}");
            client.sendRequest("ADDUSER {\"name\": \"Nancy\"}");
            //adding more parameters shouldn't make a difference
            client.sendRequest("ADDUSER {\"name\": \"Charles\", \"review_count\": \"420\"}");
            //can't really check if they were added successfully - will have to check later when I try to
            //initialize reviews based based on them
            //check if the string contains reasonable information
            assertTrue(client.getReply().matches("Add success.*\"name\":\"Sathish G.\".*"));
            assertTrue(client.getReply().matches("Add success.*\"average_stars\":0.0.*"));
            assertTrue(client.getReply().matches("Add success.*\"review_count\":0.*"));
        }
        catch(IOException e){
            fail("Exception thrown: shouldn't have failed");
        }
    }

    //testing an inappropriate string
    @Test
    public void Ftest5(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDUSER {\"notname\": \"thishouldntwork\"}");
            assertTrue(client.getReply().matches(".*ERR: INVALID_USER_STRING \\(MISSING INFO\\).*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    //adding a restaurant
    @Test
    public void Gtest6(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitude\": 3.213213, \"longitude\": 23.123, " +
                    "\"name\": \"newrest1\", \"city\":\"newcity\", \"neighborhoods\": [\"newneighborhood\"], " +
                    "\"full_address\": \"new full_address\", \"state\":\"BC\"}");
            numNewRest++;           //see that the URL was generated correctly
            assertTrue(client.getReply().matches("Add success.*\"url\":\"http://www.yelp.com/biz/newrest1\".*"));
            //get the new restaurant to see if it exists in the database
            client.sendRequest("GETRESTAURANT +NEWREST+"+numNewRest);
            assertTrue(client.getReply().matches(".*\"name\":\"newrest1\".*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    //adding a second restaurant, this time with an extra parameter: it shouldn't change anything
    @Test
    public void Htest7(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitude\": 21.231, \"longitude\": 21.588, \"name\": " +
                    " \"newrest2\", \"city\":\"newcity\", \"neighborhoods\":[\"newneighborhood\", " +
                    "\"newneighborhood2\"], \"full_address\": \"new full_address\", \"state\":\"BC\", " +
                    "\"rating\": 123}");
            numNewRest++;
            assertTrue(client.getReply().matches("Add success.*\"url\":\"http://www.yelp.com/biz/newrest2\".*"));
            client.sendRequest("GETRESTAURANT +NEWREST+"+numNewRest);
            assertTrue(client.getReply().matches(".*\"url\":\"http://www.yelp.com/biz/newrest2\".*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    //trying to add a restaurant with insufficient information
    @Test
    public void Itest8(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitiude\": 21.23, \"longitude\": 23123}");
            assertTrue(client.getReply().matches(".*ERR: INVALID_RESTAURANT_STRING \\(MISSING INFO\\).*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

/*TODO: TESTREVIEW: Test 9 - 15 check the following:
Review format should be: {"stars": <int>, "user_id": <id>, "business_id": <id>, "text": <text(which is optional)>}
    - add 1 or 2 reviews between existing restaurants ~~~
    - add a review with a non-existent database user ~~~
    - add a review with a non-existent restaurant ~~~
    - add a review between the new generated restaurant and a new generated user~~~
        > check if the rating of the new restaurant has changed~~~~~
    - add another review between the same two new restaurants~~~~
        > check if the rating has been adjusted
    - add a review that doesn't have a text field
    - add a review that has over 5 stars~~~~~~~~
 */

    //test should add a review
    @Test
    public void Jtest9(){
        try{

            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 3, \"user_id\": \"zkjy_XoVgR2EFjLjtzFDNw\", \"business_id\": \"h_we4E3zofRTf4G0JTEF0A\", \"text\": \"THIS PLACE IS BLARGH\"}");
            assertTrue(client.getReply().matches(".*\"type\":\"review\".*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    //invalid user id

    @Test
    public void Ktest10(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 3.5, \"user_id\": \"null_user\", \"business_id\": \"2ciUQ05DREauhBC3xiA4qw\", \"text\": \"THIS PLACE IS BLARGH\"}");
            assertTrue(client.getReply().matches(".*NO_SUCH_USER.*"));

        }
        catch(IOException e){
            fail("Exception thrown: Expect an error, invalid user_id!");
        }
    }


    @Test
    public void Ltest11(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 3.5, \"user_id\": \"zkjy_XoVgR2EFjLjtzFDNw\", \"business_id\": \"null_restaurant\", \"text\": \"THIS PLACE IS BLARGH\"}");
            assertTrue(client.getReply().matches(".*NO_SUCH_RESTAURANT.*"));
        }
        catch(IOException e){
            fail("Exception thrown: Expect an error, invalid business_id!");
        }
    }

    @Test
    public void Mtest12(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 1.5, \"user_id\": \"+NEWUSER+0\", \"business_id\": \"+NEWREST+0\", \"text\": \"THIS PLACE IS BLARGH\"}");
            assertTrue(client.getReply().matches(".*\"user_id\":\"\\+NEWUSER\\+0\".*"));
            client.sendRequest("GETRESTAURANT +NEWREST+"+0);
            assertTrue(client.getReply().matches(".*\"stars\":1.5.*"));

        }

        catch(IOException e){
            fail("This should work!");
        }
    }

    @Test
    public void Ntest13(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 4.5, \"user_id\": \"+NEWUSER+0\", \"business_id\": \"+NEWREST+0\", \"text\": \"THIS PLACE IS great..\"}");
            assertTrue(client.getReply().matches(".*\"stars\":4.5.*"));
        }

        catch(IOException e){
            fail("This should work!");
        }
    }

    @Test
    public void Otest14(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 5.5, \"user_id\": \"+NEWUSER+0\", \"business_id\": \"+NEWREST+0\", \"text\": \"THIS PLACE IS great..\"}");
            assertTrue(client.getReply().matches(".*STAR RATING INVALID.*"));
        }
        catch(IOException e){
            fail("This should fail, stars have to be under or equal to 5");
        }
    }

    @Test
    public void Ptest15(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDREVIEW {\"stars\": 3.5, \"user_id\": \"+NEWUSER+0\", \"business_id\": \"+NEWREST+0\", \"text\":\"\"}");
            assertTrue(client.getReply().matches(".*\"stars\":3.5.*"));
        }
        catch(IOException e){
            fail("this should work! ");
        }
    }

    //Tests for Part 5, query

    //this was the example query string
    @Test
    public void Qtest16() {
        try {
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2");
            String s = client.getReply();
            assertTrue(s.matches(".*_mv3DhRD3L3okFXYjxX_Cg.*"));
            assertTrue(s.matches(".*t-xuA4yR02gud00gTS2iyw.*"));
            assertTrue(s.matches(".*5fneYCWLhgBZQUcNPOch-w.*"));
        }
        catch(IOException e){
            fail("this should work!");
        }
    }

    //shouldn't give me anything - doesn't contain coquitlam or richmond addresses or location
    @Test
    public void Rtest17(){
        try{
            //these places are not part of the database
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY in(Coquitlam) || in(Richmond)");
            assertTrue(client.getReply().matches(".*ERR: NO_MATCH.*"));
        }
        catch(IOException e){
            fail("this should work!");
        }
    }

    //another simple query test, this time without brackets
    @Test
    public void Stest18(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY category(Indian) && rating >= 3");
            String s = client.getReply();
            assertTrue(s.matches(".*TKDgFp9C7kf_pSHrQHZW_Q.*"));
            assertTrue(s.matches(".*xtyK6kDA4A2ASCVuU2vH7A.*"));
            assertTrue(s.matches(".*loBOs5ruFXSNL-ZM29cTrA.*"));
        }
        catch(IOException e){
            fail("this should work!");
        }
    }

    //tests to see if contradictory arguments make it so that there's no return restaurants
    @Test
    public void Ttest19(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY price < 2 && price > 3");
            assertTrue(client.getReply().matches(".*ERR: NO_MATCH.*"));
        }
        catch(IOException e){
            fail("this should work!");
        }
    }

    //tests another query that contains spaces
    @Test
    public void Utest20(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY in(UC Campus Area) && (rating > 2 || rating < 3)");
            String s = client.getReply();
            assertTrue(s.matches(".*TKDgFp9C7kf_pSHrQHZW_Q.*"));
            assertTrue(s.matches(".*XD5ybqI0BHcTj5cLQyIPLA.*"));
            assertTrue(s.matches(".*PUK0RoIvq8pgi38FSCg7cQ.*"));
            assertTrue(s.matches(".*W_14XPx-En1MmvaZykjxpQ.*"));
        }
        catch(IOException e){
            fail("this should work!");
        }
    }

    //test to see if numbers can be used as part of the string and check to make sure the number isn't
    //parsed as a rating/price instead
    @Test
    public void Vtest21(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY name(Cafe 3)");
            assertTrue(client.getReply().matches(".*gclB3ED6uk6viWlolSb_uA.*"));
        }
        catch(IOException e){
            fail("should've worked..");
        }
    }

    //tests an invalid query string
    @Test
    public void Wtest22(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("QUERY in(thisshouldnotwork)) & price < three");
            assertTrue(client.getReply().matches("ERR: INVALID_QUERY_STRING"));
        }
        catch(IOException e){
            fail("should've worked :/");
        }
    }
}