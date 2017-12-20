package ca.ece.ubc.cpen221.mp5.Server;

import javax.json.stream.JsonParsingException;
import java.io.*;
import java.net.Socket;

/**
 * a pretty abstract representation of a client, most of which is copied from fibonacci server
 *
 * Requests are terminated by a \n, which are added automatically for the sendReply() command
 */
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
}