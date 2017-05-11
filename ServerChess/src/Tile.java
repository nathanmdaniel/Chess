import javax.swing.*;

/**
 * Created by Nathan on 3/13/2017.
 */
public class Tile extends JButton
{
    private NPoint location;
    private NButtonListener listener;

    public Tile(ImageIcon i,NPoint p,NButtonListener l)
    {
        //super(""+r+c,i);	    //this will make it print the row and column numbers
        super(i);	            //this will make it just put the image

        location=p;
        this.addActionListener(l);
        listener=l;
    }
    public void setImage(String q)
    {
        this.setIcon(new ImageIcon(q));
    }
    public NPoint getLoc()
    {
        return location;
    }
    public NButtonListener getListener()
    {
        return listener;
    }
}