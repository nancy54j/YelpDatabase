package ca.ece.ubc.cpen221.mp5.Part2;
import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class Part2 {
    YelpDataBase ydb;

    @Before
    public void setUp(){
        ydb = new YelpDataBase();
    }

    @Test
    //getmatches gives a list of restaurant ids that correspond to restaurants that match the query
    public void matchestest(){
        Set<String> coffee = ydb.getMatches("coffee");
        assertTrue(coffee.contains("QK92fducCS79yYkuTEfG0w"));
        assertTrue(coffee.contains("ozmMymxyx_EPnCGlIGe1lw"));
        assertEquals(coffee.size(), 16);

        Set<String> chinese = ydb.getMatches("chinese");
        assertTrue(chinese.contains("t-xuA4yR02gud00gTS2iyw"));
        assertEquals(chinese.size(), 8);

        chinese.addAll(coffee);
        Set<String> both = ydb.getMatches("coffee chinese");
        assertEquals(chinese, both);
    }

    @Test
    public void matchestest2(){
        Set<String> empty = ydb.getMatches("l12kl3");
        assertTrue(empty.isEmpty());
    }

    //this is just seeing if it takes a reasonable amount of time
    @Test(timeout = 40000)
    public void kmeans(){
        System.out.println(ydb.kMeansClusters_json(10));
    }

    @Test(timeout = 40000)
    public void kmeans1(){
        System.out.println(ydb.kMeansClusters_json(3));
    }

    @Test
    public void kmeans2(){
        try{
            ydb.kMeansClusters_json(0);
            fail("should've thrown exception");
        }
        catch(Exception e){
        }
        try{
            ydb.kMeansClusters_json(200);
            fail("should've thrown exception");
        }
        catch(Exception e) {
        }
    }
}
