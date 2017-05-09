import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 3/13/2017.
 */
public class Board
{
    private Piece[][] board;

    public Board()
    {
        board=new Piece[8][8];

        board[0][0]=new Rook(2,new NPoint(0,0));        //black side
        board[0][1]=new Knight(2,new NPoint(0,1));
        board[0][2]=new Bishop(2,new NPoint(0,2));
        board[0][3]=new Queen(2,new NPoint(0,3));
        board[0][4]=new King(2,new NPoint(0,4));
        board[0][5]=new Bishop(2,new NPoint(0,5));
        board[0][6]=new Knight(2,new NPoint(0,6));
        board[0][7]=new Rook(2,new NPoint(0,7));
        for(int i=0;i<8;i++)
            board[1][i]=new Pawn(2,new NPoint(1,i));

        for(int i=2;i<6;i++)                                            //empty
            for(int j=0;j<8;j++)
                board[i][j]=new Empty();

        for(int i=0;i<8;i++)                                            //white side
            board[6][i]=new Pawn(1,new NPoint(6,i));
        board[7][0]=new Rook(1,new NPoint(7,0));
        board[7][1]=new Knight(1,new NPoint(7,1));
        board[7][2]=new Bishop(1,new NPoint(7,2));
        board[7][3]=new Queen(1,new NPoint(7,3));
        board[7][4]=new King(1,new NPoint(7,4));
        board[7][5]=new Bishop(1,new NPoint(7,5));
        board[7][6]=new Knight(1,new NPoint(7,6));
        board[7][7]=new Rook(1,new NPoint(7,7));


        //board[6][3]=new Empty();                // BLANK THIS OUT
        //board[7][5]=new Empty();
        //board[7][6]=new Empty();
    }

    public Piece movePiece(NPoint from, NPoint to)              //TODO: when you do bughouse, this returns the captured piece just FYI
    {
        Piece ret=board[to.getRow()][to.getCol()];
        board[to.getRow()][to.getCol()]=board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()]=new Empty();

        board[to.getRow()][to.getCol()].setLocation(to);

        return ret;
    }

    public Piece testMovePiece(NPoint from, NPoint to)
    {
        Piece ret=board[to.getRow()][to.getCol()];
        board[to.getRow()][to.getCol()]=board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()]=new Empty();

        board[to.getRow()][to.getCol()].testSetLocation(to);

        return ret;
    }

    public void setPiece(NPoint location, Piece piece)
    {
        board[location.getRow()][location.getCol()]=piece;
    }

    public Piece[][] getBoard()
    {
        return board;
    }

    public List<Piece> getPieces(int team)
    {
        List<Piece> ret = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].getTeam() == team)
                    ret.add(board[i][j]);
        return ret;
    }

    public List<NPoint> getPieceLocs(int team)
    {
        List<NPoint> ret = new ArrayList<NPoint>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].getTeam() == team)
                    ret.add(new NPoint(i,j));
        return ret;
    }
}
