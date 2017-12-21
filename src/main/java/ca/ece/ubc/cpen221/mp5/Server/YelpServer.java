package ca.ece.ubc.cpen221.mp5.Server;

import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;

import javax.json.JsonException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * server implementation of the database. Once the server has been started, it will block onto a
 * client has successfully connected to the server. Each client that connects to the server will be
 * given its own thread and the thread will be closed at the termination of the connection.
 *
 * (Frankly, java server stuff is pretty abstracted, so I don't really have a concrete idea of
 * what's going on XD)
 */
public class YelpServer {

    public static int YELP_PORT = 4949;

    private ServerSocket serversocket;
    YelpDataBase ydb;

    public YelpServer() throws IOException {
        ydb = new YelpDataBase();
        serversocket = new ServerSocket(YELP_PORT);
    }

    public YelpServer(int port) throws IOException{
        ydb = new YelpDataBase();
        YELP_PORT = port;
        serversocket = new ServerSocket(YELP_PORT);
    }

    public void serve() throws IOException {
        while(true){
            final Socket socket = serversocket.accept();

            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            handle(socket);
                        } finally {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            handler.start();
        }
    }

    private void handle(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        /*
        Here is where the string recieved by the server is actually parsed. It is
        first matched by regex to be separated into the different commands this server can handle,
        and then it extracts the relevant text and calls the relevant YelpDataBase method.
        Errors are also caught here, and at the end, the corresponding return string is set back to
        the user.
        */
        try {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("String recieved:" + line);
                if (line.matches("^GETRESTAURANT .*")) {
                    //creating a matcher from a pattern seems really ugly, but honestly, I have no idea
                    //how else I'm supposed to capture specific text from regex LOL
                    Matcher m = Pattern.compile("^(?:GETRESTAURANT )(.*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.getRestaurant(m.group(1).trim()));
                        } catch (IllegalArgumentException iae) {
                            out.println("ERR: NO_SUCH_RESTAURANT");
                        }
                    }
                } else if (line.matches("^ADDUSER .*")) {
                    Matcher m = Pattern.compile("^(?:ADDUSER )(.*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.addUser(m.group(1)).trim());
                        } catch (JsonException e) {
                            out.println("ERR: INVALID_USER_STRING");
                        } catch (NullPointerException e){
                            out.println("ERR: INVALID_USER_STRING (MISSING INFO)");
                        }
                    }
                }
                else if (line.matches("^ADDREVIEW .*")) {
                    Matcher m = Pattern.compile("^(?:ADDREVIEW )(.*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.addReview(m.group(1)).trim());
                        } catch (JsonException e) {
                            out.println("ERR: INVALID_RATING_STRING");
                        } catch (NullPointerException e){
                            out.println("ERR: INVALID_RATING_STRING (MISSING INFO)");
                        } catch (IllegalArgumentException e){
                            out.println("ERR: STAR RATING INVALID");
                        }
                    }
                }
                else if (line.matches("^ADDRESTAURANT .*")) {
                    Matcher m = Pattern.compile("^(?:ADDRESTAURANT )(.*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.addRestaurant(m.group(1)).trim());
                        } catch (JsonException e) {
                            out.println("ERR: INVALID_RESTAURANT_STRING");
                        } catch (NullPointerException e){
                            out.println("ERR: INVALID_RESTAURANT_STRING (MISSING INFO)");
                        }
                    } else {
                        out.println("invalid input format");
                    }
                }
                else if(line.matches("^QUERY .*")) {
                    Matcher m = Pattern.compile("^(?:QUERY )(.*)$").matcher(line);
                    if(m.find()) {
                        try {
                            Set<String> restaurants = ydb.query(m.group(1).trim());
                            if(restaurants.isEmpty()) {
                                out.println("ERR: NO_MATCH");
                            }
                            else {
                                out.println(restaurants.toString());
                            }
                        }
                        //exceptions here should be a result of the parser failing, so just attribute it to
                        //faulty query
                        catch(Exception e) {
                            out.println("ERR: INVALID_QUERY_STRING");
                        }
                    }
                }
                else out.println("ERR: ILLEGAL_REQUEST");
                out.flush();
            }

        }
        finally {
            br.close();
            out.close();
        }
    }

    public static void main(String [] args) throws IOException{
        YelpServer ys = new YelpServer(Integer.parseInt(args[0]));
        ys.serve();
    }
}