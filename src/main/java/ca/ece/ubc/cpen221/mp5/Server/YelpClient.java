package ca.ece.ubc.cpen221.mp5.Server;

import javax.json.stream.JsonParsingException;
import java.io.*;
import java.net.Socket;


public class YelpClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // Rep invariant: socket, in, out != null

    public YelpClient(String hostname, int port) throws IOException{
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendRequest(String line) throws IOException {
        out.print(line +"\n");
        out.flush();
    }

    public String getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }

        try {
            return new String(reply);
        } catch (JsonParsingException jpe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    /**
     * Closes the client's connection to the server.
     * This client is now "closed". Requires this is "open".
     * @throws IOException if close fails
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /**
     * Use a FibonacciServer to find the first N Fibonacci numbers.
     */
    public static void main(String[] args) {
        try {
            YelpClient client = new YelpClient("localhost", YelpServer.YELP_PORT);

            client.sendRequest("GETRESTAURANT h_we4E3zofRTf4G0JTEF0A");

            String reply = client.getReply();
            System.out.println("reply:" + reply);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}