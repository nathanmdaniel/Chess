import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 3/11/2017.
 */
public class King implements Piece
{
    private int team;
    private boolean hasMoved;
    private NPoint location;

    public King(int team, NPoint location)
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
        return "King";
    }

    public String getImageLoc()
    {
        if(team==1)
            return "chessPieceImages/White King.png";
        else
            return "chessPieceImages/Black King.png";
    }

    public void setLocation(NPoint p)
    {
        location=p;
        hasMoved=true;
    }
    public void testSetLocation(NPoint p)
    {
        location=p;
    }

    public NPoint getLocation()     //King special
    {
        return location;
    }

    public boolean hasMoved()       //King/Rook Special
    {
        return hasMoved;
    }


    public List<NPoint> getAvailableMoves(Piece[][] board)
    {
        int row=location.getRow();
        int col=location.getCol();

        List<NPoint> ret=new ArrayList<NPoint>();

        if(row+1<8)                                                         //
        {                                                                   //  o
            if(board[row+1][col] instanceof Empty)                          //x x x
                ret.add(new NPoint(row+1,col));
            else if(board[row+1][col].getTeam()!=team)
                ret.add(new NPoint(row+1,col));
            if(col+1<8)
            {
                if (board[row + 1][col + 1] instanceof Empty)       //if is empty
                    ret.add(new NPoint(row + 1, col + 1));
                else if(board[row+1][col+1].getTeam()!=team)        //else if can kill into
                    ret.add(new NPoint(row+1,col+1));
            }
            if(col-1>-1)
            {
                if (board[row + 1][col - 1] instanceof Empty)
                    ret.add(new NPoint(row + 1, col - 1));
                else if(board[row+1][col-1].getTeam()!=team)
                    ret.add(new NPoint(row+1,col-1));
            }
        }

        if(row-1>-1)                                                       //x x x
        {                                                                  //  o
            if(board[row-1][col] instanceof Empty)                         //
                ret.add(new NPoint(row-1,col));
            else if(board[row-1][col].getTeam()!=team)
                ret.add(new NPoint(row-1,col));
            if(col+1<8)
            {
                if (board[row - 1][col + 1] instanceof Empty)
                    ret.add(new NPoint(row - 1, col + 1));
                else if(board[row-1][col+1].getTeam()!=team)
                    ret.add(new NPoint(row-1,col+1));
            }
            if(col-1>-1)
            {
                if (board[row - 1][col - 1] instanceof Empty)
                    ret.add(new NPoint(row - 1, col - 1));
                else if(board[row-1][col-1].getTeam()!=team)
                    ret.add(new NPoint(row-1,col-1));
            }
        }

        if(col+1<8)                                                         //
        {                                                                   //x o x
            if (board[row][col + 1] instanceof Empty)                       //
                ret.add(new NPoint(row, col + 1));
            else if(board[row][col+1].getTeam()!=team)
                ret.add(new NPoint(row,col+1));
        }
        if(col-1>-1)
        {
            if (board[row][col - 1] instanceof Empty)
                ret.add(new NPoint(row, col - 1));
            else if(board[row][col-1].getTeam()!=team)
                ret.add(new NPoint(row,col-1));
        }

       /* if(!hasMoved)                                                   //castle
        {
            if(board[row][col+3] instanceof  Rook)
                if(!((Rook)board[row][col+3]).hasMoved())
                {
                    boolean rightOpen = true;
                    for (int i = 1; i < 3; i++)
                    {
                        if (!(board[row][col + i] instanceof Empty))
                            rightOpen = false;
                    }
                    if(rightOpen)
                        ret.add(new NPoint(row, col+2));
                }
            if(board[row][col-4] instanceof  Rook)
                if(!((Rook)board[row][col-4]).hasMoved())
                {
                    boolean rightOpen = true;
                    for (int i = 1; i < 4; i++)
                        if (!(board[row][col - i] instanceof Empty))
                            rightOpen = false;
                    if(rightOpen)
                        ret.add(new NPoint(row, col-2));
                }
        }*/

        return ret;
    }


}
