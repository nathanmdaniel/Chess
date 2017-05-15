import javax.swing.*;

/**
 * Created by Nathan on 5/11/2017.
 */
public class DropPieceButton extends JButton
{
    private Piece piece;
    private DropListener listener;

    public DropPieceButton(Piece p, DropListener l, ImageIcon i)
    {
        super.setIcon(i);
        piece=p;
        listener=l;
    }
    public Piece getPiece(){return piece;}

    public DropListener getListener(){return listener;}
}
