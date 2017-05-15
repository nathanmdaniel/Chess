import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nathan on 5/11/2017.
 */
public class DropListener implements ActionListener
{
    private boolean selected;

    public DropListener()
    {
        selected=false;
    }

    public void actionPerformed(ActionEvent e)
    {
        System.out.println("dropListener triggered");
        selected=true;
    }
    public boolean isSelected()
    {
        return selected;
    }
    public void unselect()
    {
        selected=false;
    }
}
