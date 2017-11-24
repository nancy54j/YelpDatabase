package ca.ece.ubc.cpen221.mp5;

public class Business implements Reviewable{
    private static int gen_id = 0;

    public final String id;
    public final String name;
    public final String full_address;
    public final String city;
    public final String neighbourhood;
    public final String state;
    private double latitude;
    private double longitude;
    private double stars; //between 0 and 5;
    private int reviewCount;

    public Business(String business_id, String name, String full_address, String city, double latitude,
                    double longitude, String neighbourhood, String state, double stars, int reviewCount){
        this.id = business_id;
        this.name = name;
        this.full_address = full_address;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.state = state;
        this.stars = stars;
        this.reviewCount = reviewCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Business(int latitude, int longitude, String name, String neighbourhood, String full_address,
                    String city, String state){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.full_address = full_address;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.state = state;
        this.stars = 0;
        this.reviewCount = 0;

        this.id = "+NEW+" + gen_id++;
    }

    //gets the longtitude and latitude of a location
    public double[] getLocation(){
        return new double[] {latitude, longitude};
    }

    public void addReview(Review r){
        if(!r.business.equals(id)){
            throw new IllegalArgumentException("Review must pertain to this restaurant");
        }
        stars = (stars*reviewCount + r.rating) / ++reviewCount;
    }

    //return the rating in increments of .5's
    public double getRating(){
        return Math.round(stars * 2) / 2f;
    }

}
