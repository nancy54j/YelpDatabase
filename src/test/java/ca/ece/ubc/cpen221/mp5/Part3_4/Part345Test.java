
package ca.ece.ubc.cpen221.mp5.Part3_4;

import ca.ece.ubc.cpen221.mp5.Business.Restaurant;
import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import ca.ece.ubc.cpen221.mp5.Server.YelpClient;
import ca.ece.ubc.cpen221.mp5.Server.YelpServer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Part345Test {

    //create a separate thread with the server running - this should be the only thing I need to test part 3-5

    @BeforeClass
    public static void setUp() throws IOException{
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
    public void test1(){
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

    //trying invliad getrestaurant strings
    @Test
    public void test2(){
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
    public void test3(){
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

    @Test
    public void test4(){
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
    public void test5(){
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
    public void test6(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitude\": 3.213213, \"longitude\": 23.123, " +
                    "\"name\": \"newrest1\", \"city\":\"newcity\", \"neighborhoods\": [\"newneighborhood\"], " +
                    "\"full_address\": \"new full_address\", \"state\":\"BC\"}");
            //see that the URL was generated correctly
            assertTrue(client.getReply().matches("Add success.*\"url\":\"http://www.yelp.com/biz/newrest1\".*"));
            //get the new restaurant to see if it exists in the database
            client.sendRequest("GETRESTAURANT +NEWREST+0");
            assertTrue(client.getReply().matches(".*\"name\":\"newrest1\".*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    //adding a second restaurant, this time with an extra parameter: it shouldn't change anything
    @Test
    public void test7(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitude\": 21.231, \"longitude\": 21.588, \"name\": " +
                    " \"newrest2\", \"city\":\"newcity\", \"neighborhoods\":[\"newneighborhood\", " +
                    "\"newneighborhood2\"], \"full_address\": \"new full_address\", \"state\":\"BC\", " +
                    "\"rating\": 123}");
            assertTrue(client.getReply().matches("Add success.*\"url\":\"http://www.yelp.com/biz/newrest2\".*"));
            client.sendRequest("GETRESTAURANT +NEWREST+1");
            client.sendRequest("GETRESTAURANT +NEWREST+0");
            assertTrue(client.getReply().matches(".*\"url\":\"http://www.yelp.com/biz/newrest2\".*"));
            assertTrue(client.getReply().matches(".*\"state\":\"BC\".*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

    @Test
    public void test8(){
        try{
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);
            client.sendRequest("ADDRESTAURANT {\"latitiude\": 21.23, \"longitude\": 23123}");
            assertTrue(client.getReply().matches(".*ERR: INVALID_RESTAURANT_STRING \\(MISSING INFO\\).*"));
        }
        catch(IOException e){
            fail("Exception thrown: this shouldn't have failed");
        }
    }

/*TODO: TESTREVIEW:

Review format should be: {"stars": <int>, "user_id": <id>, "business_id": <id>, "text": <text(which is optional)>}
    - add 1 or 2 reviews between existing restaurants
    - add a review with a non-existent database user
    - add a review with a non-existent restaurant
    - add a review between the new generated restaurant and a new generated user
        > check if the rating of the new restaurant has changed
    - add another review between the same two new restaurants
        > check if the rating has been adjusted
    - add a review that doesn't have a text field
    - add a review that has over 5 stars
 */

    @Test
    public void test9(){

    }

}

/*
package ca.ece.ubc.cpen221.mp5.Part3_4;

import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import ca.ece.ubc.cpen221.mp5.Server.YelpClient;
import ca.ece.ubc.cpen221.mp5.Server.YelpServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Part345Test {
    YelpDataBase ydb;
    YelpServer ys;

    Thread server;
    Runnable serverrun;

    @Before
    public void setUp() throws IOException{
        YelpServer ys = new YelpServer();
        serverrun = new Runnable(){
            public void run(){
                try{
                    ys.serve();
                }
                catch(Exception e){}
            }
        };
        server = new Thread(serverrun);
        server.start();
    }

    @Test
    public void runAfterServer(){
        try {
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);

            client.sendRequest("GETRESTAURANT BJKIoQa5N2T_oDlLVf467Q");

            client.sendRequest("ADDUSER {\"name\": \"Sathish G.\"}");

            client.sendRequest("QUERY in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2");

            client.sendRequest("QUERY LDSKFJAw3 && dslakfa");

            client.sendRequest("ADDREVIEW ");

            String reply = client.getReply();
            String reply1 = client.getReply();
            String reply2 = client.getReply();
            String reply3 = client.getReply();

            assertTrue(reply.matches(".*\"name\":\"Jasmine Thai\".*"));
            assertTrue(reply1.matches(".*\"name\":\"Sathish G\\.\".*"));
            assertTrue(reply2.matches(".*EuvQIymdxIcHv_fS80rVbg, _mv3DhRD3L3okFXYjxX_Cg, 1E2MQLWfwpsId185Fs2gWw, ERRowW4pGO6pK9sVYyA1nQ.*"));
            assertEquals(reply3,"ERR: INVALID_QUERY");

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/