package ca.ece.ubc.cpen221.mp5.Part2;
import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import java.util.Set;
import java.util.function.ToDoubleBiFunction;

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
        System.out.println(ydb.kMeansClusters_json(25));
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

    @Test
    public void getPrediction1(){
        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("Djk49JjpKl9HQNpmiX669Q");

        assertEquals(4.0, func.applyAsDouble(ydb, "TUIDRJ_rUkdmYPSRAAEsPg"),0.1);
    }

    @Test
    public void getPrediction2(){
        try {
            ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("Djk49JjpKl9HQNpmiX669Q");
            func.applyAsDouble(ydb, "unknown business");
            fail("Should've thrown exception");
        }
        catch(Exception e){}
    }

    @Test
    public void getPrediction3(){
        try {
            ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("unknown user");

            func.applyAsDouble(ydb, "TUIDRJ_rUkdmYPSRAAEsPg");

            fail("should've thrown Exception");
        }
        catch(Exception e){}
    }

    @Test
    public void getPrediction4(){
        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("3Pxvvsub2mYh-t4Y3bdcRw");

        assertEquals(5.0, func.applyAsDouble(ydb,"TUIDRJ_rUkdmYPSRAAEsPg"), 0.1);
    }

    @Test
    public void getPrediction5(){
        try {
            ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("kliaIrCOEk9RZo7kI25xXg");

            System.out.println(func.applyAsDouble(ydb, "TUIDRJ_rUkdmYPSRAAEsPg"));

        }
        catch(Exception e){}
    }

    @Test
    public void getPrediction6(){
        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("Gcjr__StoE_lpZjDGc31Ew");

        assertEquals(2.6, func.applyAsDouble(ydb, "TUIDRJ_rUkdmYPSRAAEsPg"), 0.1);
    }

    @Test
    public void getPrediction7(){
        ToDoubleBiFunction<YelpDataBase, String> func = ydb.getPredictorFunction("Vw7Zi0EXqHmhru78zyFxaQ");

        assertEquals(3.5, func.applyAsDouble(ydb,"TUIDRJ_rUkdmYPSRAAEsPg"), 0.1);
    }

}
