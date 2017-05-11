import java.util.List;
import java.util.ArrayList;

/**
 * Created by Nathan on 3/11/2017.
 */
public class Bishop implements Piece
{
    private int team;
    private NPoint location;

    public Bishop(int team, NPoint location)
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
        return "Bishop";
    }

    public String getImageLoc()
    {
        if(team==1)
            return "chessPieceImages/White Bishop.png";
        else
            return "chessPieceImages/Black Bishop.png";
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
        int row=location.getRow();
        int col=location.getCol();

        List<NPoint> ret=new ArrayList<NPoint>();

        for(int i=1;i<8;i++)    //down right
            if(row+i<8 && col+i<8)
                if(board[row+i][col+i] instanceof Empty)
                    ret.add(new NPoint(row+i,col+i));
                else
                {
                    if(board[row + i][col+i].getTeam() != team)
                        ret.add(new NPoint(row + i, col+i));
                    break;
                }
        for(int i=1;i<8;i++)    //down left
            if(row-i>-1 && col+i<8)
                if (board[row - i][col+i] instanceof Empty)
                    ret.add(new NPoint(row - i, col+i));
                else
                {
                    if (board[row - i][col+i].getTeam() != team)
                        ret.add(new NPoint(row - i, col+i));
                    break;
                }
        for(int i=1;i<8;i++)    //up right
            if(row+i<8 && col-i>-1)
                if(board[row+i][col-i] instanceof Empty)
                    ret.add(new NPoint(row+i,col-i));
                else
                {
                    if(board[row+i][col-i].getTeam() != team)
                        ret.add(new NPoint(row+i, col-i));
                    break;
                }
        for(int i=1;i<8;i++)    //up left
            if(row-i>-1 && col-i>-1)
                if (board[row-i][col-i] instanceof Empty)
                    ret.add(new NPoint(row-i, col-i));
                else
                {
                    if (board[row-i][col-i].getTeam() != team)
                        ret.add(new NPoint(row-i, col-i));
                    break;
                }

        return ret;
    }
}
