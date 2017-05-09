import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 3/11/2017.
 */
public class Empty implements Piece
{
    public int getTeam()
    {
        return 0;
    }
    public String getType()
    {
        return "Empty";
    }
    public List<NPoint> getAvailableMoves(Piece[][] board)
    {
        return new ArrayList<NPoint>();
    }
    public String getImageLoc()
    {
           // return "chessPieceImages/Empty.png";          // leaves a visible pixel
        return "";
    }
    public void setLocation(NPoint p)
    {
        //do nothing
    }

    public void testSetLocation(NPoint p)
    {
        //do nothing
    }
}
