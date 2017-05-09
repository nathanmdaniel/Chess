/**
 * Created by Nathan on 5/6/2017.
 */
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client
{
    private ClientSideServer server;
    private boolean connected;
    private Socket socket;
    private PrintWriter out;

    public Client()
    {
    }

    public boolean connect(int port)
    {
        return connect("localhost", port);
    }

    public boolean connect(String ip, int port)
    {
        if(connected)
            disconnect();

        connected = false;
        try
        {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            connected = true;

            server = new ClientSideServer(socket, this);
            server.start();
            onConnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            disconnect();
        }

        return connected;
    }

    public void send(String message)
    {
        if(connected)
        {
            out.println(message);
        }
    }

    public void disconnect()
    {
        connected = false;
        server.disconnect();
        try
        {
            out.close();
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        onDisconnect("Disconnected from Server");
    }

    public abstract void process(String message);
    public abstract void onDisconnect(String message);
    public abstract void onConnect();
}