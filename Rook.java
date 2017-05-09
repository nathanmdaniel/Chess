import java.util.ArrayList;
import java.util.List;

/**
 * Created bcol Nathan on 3/11/2017.
 */
public class Rook implements Piece
{
    private int team;
    private boolean hasMoved;
    private NPoint location;

    public Rook(int team, NPoint location)
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
        return "Rook";
    }

    public String getImageLoc()
    {
        if(team==1)
            return "chessPieceImages/White Rook.png";
        else
            return "chessPieceImages/Black Rook.png";
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

    public boolean hasMoved()       //Rook/King Special
    {
        return hasMoved;
    }

    public List<NPoint> getAvailableMoves(Piece[][] board)
    {
        int row=location.getRow();
        int col=location.getCol();

        List<NPoint> ret=new ArrayList<NPoint>();

        for(int i=1;i<8;i++)    //hori right
            if(row+i<8)
                if(board[row+i][col] instanceof Empty)
                    ret.add(new NPoint(row+i,col));
                else
                {
                    if(board[row + i][col].getTeam() != team)
                        ret.add(new NPoint(row + i, col));
                    break;
                }
        for(int i=1;i<8;i++)    //hori left
            if(row-i>-1)
                if (board[row - i][col] instanceof Empty)
                    ret.add(new NPoint(row - i, col));
                else
                {
                    if (board[row - i][col].getTeam() != team)
                        ret.add(new NPoint(row - i, col));
                    break;
                }
        for(int i=1;i<8;i++)    //vert down
            if(col+i<8)
                if(board[row][col+i] instanceof Empty)
                    ret.add(new NPoint(row,col+i));
                else
                {
                    if(board[row][col+i].getTeam() != team)
                        ret.add(new NPoint(row, col+i));
                    break;
                }
        for(int i=1;i<8;i++)    //vert up
            if(col-i>-1)
                if (board[row][col-i] instanceof Empty)
                    ret.add(new NPoint(row, col-i));
                else
                {
                    if (board[row][col-i].getTeam() != team)
                        ret.add(new NPoint(row, col-i));
                    break;
                }

        return ret;
    }
}
