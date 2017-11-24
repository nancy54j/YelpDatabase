package ca.ece.ubc.cpen221.mp5;

public interface Business {

    /** returns the unique ID of the business, used for identification
     *
     * @return
     */
    String getID();

    /**
     *  returns the name of the business as a set of words
     */
    String[] getName();


}
