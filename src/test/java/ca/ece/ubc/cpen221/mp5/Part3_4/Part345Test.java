package ca.ece.ubc.cpen221.mp5.Part3_4;

import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import ca.ece.ubc.cpen221.mp5.Server.YelpClient;
import ca.ece.ubc.cpen221.mp5.Server.YelpServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Part345Test {
    YelpDataBase ydb;
    YelpServer ys;

    Thread server;
    Runnable serverrun;

    @Before
    public void setUp(){
        serverrun = new Runnable(){
            public void run(){
                try{
                    YelpServer ys = new YelpServer();
                    ys.serve();
                }
                catch(Exception e){}
            }
        };
        server = new Thread(serverrun);
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
