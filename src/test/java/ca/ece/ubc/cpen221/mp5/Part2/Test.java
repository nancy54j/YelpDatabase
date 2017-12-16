package ca.ece.ubc.cpen221.mp5.Part2;

import ca.ece.ubc.cpen221.mp5.Server.YelpServer;
import org.junit.Before;

import java.io.IOException;

public class Test {

    Thread client;
    Thread server;

    Runnable clientrun;
    Runnable serverrun;

    @Before
    public void setUp(){
        clientrun = new Runnable(){
            public void run(){
                try {
                    YelpServer ys = new YelpServer();
                    ys.serve();
                }
                catch(Exception e){}
            }
        };
        client = new Thread(clientrun);
    }
}
