package ca.ece.ubc.cpen221.mp5.Server;

import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;

import javax.json.stream.JsonParsingException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YelpServer {

    public static final int YELP_PORT = 4949;

    private ServerSocket serversocket;
    YelpDataBase ydb;

    public YelpServer() throws IOException {
        ydb = new YelpDataBase();
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


        //TODO: star rating cannot be over 5
        try {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("String recieved:" + line);
                if (line.matches("^GETRESTAURANT .*")) {
                    Matcher m = Pattern.compile("^(?:GETRESTAURANT )(.*)(?:\\s*)$").matcher(line);
                    if (m.find()) {
                        try {
                            System.out.println("getting restaurant: " + m.group(1));
                            out.println(ydb.getRestaurant(m.group(1)));
                        } catch (IllegalArgumentException iae) {
                            out.println("invalid restaurant id");
                        }
                    } else {
                        out.println("invalid input format");
                    }
                } else if (line.matches("^ADDUSER .*")) {
                    Matcher m = Pattern.compile("^(?:ADDUSER )(.*)(?:\\s*)$").matcher(line);
                    if (m.find()) {
                        try {
                            System.out.println("adding user:" + m.group(1));
                            out.println(ydb.addUser(m.group(1)));
                        } catch (IllegalArgumentException iae) {
                            out.println("invalid user string");
                        }
                    }
                }
                else if (line.matches("^ADDREVIEW .*")) {
                    Matcher m = Pattern.compile("^(?:ADDREVIEW )(.*)(?:\\s*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.addReview(m.group(1)));
                        } catch (IllegalArgumentException iae) {
                            out.println("invalid string");
                        }
                    }
                }
                else if (line.matches("^ADDRESTAURANT .*")) {
                    Matcher m = Pattern.compile("^(?:ADDRESTAURANT )(.*)(?:\\s*)$").matcher(line);
                    if (m.find()) {
                        try {
                            out.println(ydb.addRestaurant(m.group(1)));
                        } catch (IllegalArgumentException iae) {
                            out.println("invalid string");
                        }
                    } else {
                        out.println("invalid input format");
                    }
                }
                else if(line.matches("^QUERY .*")) {
                    Matcher m = Pattern.compile("^(?:QUERY )(.*)(?:\\s*)$").matcher(line);
                    if(m.find()) {
                        try {
                            Set<String> restaurants = ydb.query(m.group(1));
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
                            out.println("ERR: INVALID_QUERY");
                        }
                    }
                }
                else out.println("unrecognized input");
                out.flush();
            }

        }
        finally {
            br.close();
            out.close();
        }
    }


    public static void main(String[] args){
        try{
            YelpServer server = new YelpServer();
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}