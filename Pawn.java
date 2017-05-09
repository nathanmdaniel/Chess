import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 3/11/2017.
 */
public class Pawn implements Piece
{
    private int team;
    private NPoint location;

    public Pawn(int team, NPoint location)
    {
        this.team=team;
        this.location=location;
    }

    public int getTeam()
    {
        return team;
    }

    public String getType()
    {
        return "Pawn";
    }

    public String getImageLoc()
    {
        if(team==1)
            return "chessPieceImages/White Pawn.png";
        else
            return "chessPieceImages/Black Pawn.png";
    }

    public void setLocation(NPoint p)
    {
        location=p;
    }
    public void testSetLocation(NPoint p)
    {
        location=p;
    }

    public List<NPoint> getAvailableMoves(Piece[][] board)
    {
        int row = location.getRow();
        int col = location.getCol();

        List<NPoint> ret = new ArrayList<NPoint>();

        //TODO: make pawns be able to turn into any piece at the far end

        if (team == 1) {
            if (row - 1 > -1) {
                if (board[row - 1][col] instanceof Empty)
                    ret.add(new NPoint(row - 1, col));
                if (col + 1 < 8)
                    if (board[row - 1][col + 1].getTeam() == 2)
                        ret.add(new NPoint(row - 1, col + 1));
                if (col - 1 > -1)
                    if (board[row - 1][col - 1].getTeam() == 2)
                        ret.add(new NPoint(row - 1, col - 1));
            }
            if (row == 6)
                if (board[row - 1][col] instanceof Empty)
                    if (board[row - 2][col] instanceof Empty)
                        ret.add(new NPoint(row - 2, col));
        }
        if (team == 2)
        {
            if (row + 1 < 8) {
                if (board[row + 1][col] instanceof Empty)
                    ret.add(new NPoint(row + 1, col));
                if (col + 1 < 8)
                    if (board[row + 1][col + 1].getTeam() == 1)
                        ret.add(new NPoint(row + 1, col + 1));
                if (col - 1 > -1)
                    if (board[row + 1][col - 1].getTeam() == 1)
                        ret.add(new NPoint(row + 1, col - 1));
            }
            if (row == 1)
                if (board[row + 1][col] instanceof Empty)
                    if (board[row + 2][col] instanceof Empty)
                        ret.add(new NPoint(row + 2, col));
         }
        return ret;
    }
}
