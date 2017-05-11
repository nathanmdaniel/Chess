import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Nathan on 5/6/2017.
 */
public class Logger
{
    PrintWriter writer;

    public Logger()
    {
        try
        {
            writer=new PrintWriter("gameLog.txt","UTF-8");
        }
        catch (IOException e)
        {
            System.out.println("error in creating logger");
        }
    }
    public void log(String s)
    {
        writer.println(s);
        writer.close();
    }
}
