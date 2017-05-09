import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 3/11/2017.
 */
public class Knight implements Piece
{
    private int team;
    private NPoint location;

    public Knight(int team, NPoint location)
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
        return "Knight";
    }

    public String getImageLoc()
    {
        if(team==1)
            return "chessPieceImages/White Knight.png";
        else
            return "chessPieceImages/Black Knight.png";
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

        if(row+1<8)                                         //down 1
        {
            if(col+2<8)                                             //right 2
                if(board[row+1][col+2].getTeam()!=team)
                    ret.add(new NPoint(row+1,col+2));
            if(col-2>-1)                                             //left 2
                if(board[row+1][col-2].getTeam()!=team)
                    ret.add(new NPoint(row+1,col-2));
        }
        if(row-1>-1)                                         //up 1
        {
            if(col+2<8)                                             //right 2
                if(board[row-1][col+2].getTeam()!=team)
                    ret.add(new NPoint(row-1,col+2));
            if(col-2>-1)                                             //left 2
                if(board[row-1][col-2].getTeam()!=team)
                    ret.add(new NPoint(row-1,col-2));
        }
        if(row-2>-1)                                         //up 2
        {
            if(col+1<8)                                             //right 1
                if(board[row-2][col+1].getTeam()!=team)
                    ret.add(new NPoint(row-2,col+1));
            if(col-1>-1)                                             //left 1
                if(board[row-2][col-1].getTeam()!=team)
                    ret.add(new NPoint(row-2,col-1));
        }
        if(row+2<8)                                         //down 2
        {
            if(col+1<8)                                             //right 1
                if(board[row+2][col+1].getTeam()!=team)
                    ret.add(new NPoint(row+2,col+1));
            if(col-1>-1)                                             //left 1
                if(board[row+2][col-1].getTeam()!=team)
                    ret.add(new NPoint(row+2,col-1));
        }
        return ret;
    }
}
