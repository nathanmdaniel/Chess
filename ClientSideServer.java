/**
 * Created by Nathan on 5/6/2017.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSideServer extends Thread
{
    private Client client;
    private boolean running;
    private BufferedReader in;

    public ClientSideServer(Socket socket, Client client)
    {
        this.client = client;

        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        if(!running)
            return;

        running = false;

        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        client.disconnect();
    }

    public void run()
    {
        running = true;
        while(running)
        {
            try
            {
                String message = in.readLine();
                if(null == message)
                    break;
                else
                    client.process(message);
            }
            catch(Exception e)
            {
                running = false;
            }

            try
            {
                Thread.sleep(1);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        disconnect();
    }
    public void process(String message)
    {
        System.out.println(message);
    }
}
