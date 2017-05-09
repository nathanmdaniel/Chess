/**
 * Created by Nathan on 3/13/2017.
 */
import java.awt.event.*;

public class NButtonListener implements ActionListener
{
    private NPoint location;
    private boolean selected;

    public NButtonListener(NPoint p)
    {
        location=p;
        selected=false;
    }
    public void actionPerformed(ActionEvent e)
    {
        selected=true;
    }
    public NPoint getLoc()
    {
        return location;
    }
    public boolean isSelected()
    {
        return selected;
    }
    public void unselect()
    {
        selected=false;
    }
    public void select()
    {
        selected=true;
    }

}
