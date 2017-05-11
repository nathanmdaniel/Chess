import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Nathan on 3/13/2017.
 */

//TODO: move castle function from King's availMoves into this class so you can also add third requirement(check). Use king instance variables to make it work

public class ChessManager
{
    private Board board;
    private ChessGUI gui;
    private boolean isGameOver;
    private int currTeam;
    private int myTeam;

    private King king1;
    private King king2;

    public ChessManager(Board board, ChessGUI gui)
    {
        this.board=board;
        this.gui=gui;
        currTeam=1;

        king1=(King)this.board.getBoard()[7][4];
        king2=(King)this.board.getBoard()[0][4];
    }

    public String getNextMove()
    {
        int clicked=0;
        NPoint moveFrom=null;
        List<NPoint> adjMoves=null;
        Piece[][] boardArr = board.getBoard();
        boolean moveSelected=false;


        isGameOver = true;                //checks for gameOver
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                if (boardArr[r][c].getTeam() == currTeam)
                    if (getAdjMoves(new NPoint(r,c)).size() > 0)
                    {
                        isGameOver = false;
                        break;
                    }
        if(!isGameOver)
        {
            while(true)
            {
                if (getThreat() != null) {                              //marks check
                    if (currTeam == 1)
                        gui.setCheckColor(king1.getLocation());
                    else
                        gui.setCheckColor(king2.getLocation());
                }

                for (int r = 0; r < 8; r++)
                    for (int c = 0; c < 8; c++) {
                        if (clicked == 0)                                                           //no tile has been selected
                        {
                            if (gui.getTiles()[r][c].getListener().isSelected()) {
                                if (boardArr[r][c].getTeam() == currTeam) {
                                    adjMoves = getAdjMoves(new NPoint(r, c));
                                    gui.showAvailableMoves(adjMoves);
                                    clicked++;
                                    moveFrom = new NPoint(r, c);
                                } else
                                    gui.getTiles()[r][c].getListener().unselect();
                            }
                        } else if (clicked == 1)                                                     //a tile has already been selected
                        {
                            if (gui.getTiles()[r][c].getListener().isSelected())
                            {
                                if (checkIfAvail(new NPoint(r, c), adjMoves))
                                {
                                    board.movePiece(moveFrom, new NPoint(r, c));
                                    if (board.getBoard()[r][c] instanceof King)
                                        castle(moveFrom, new NPoint(r, c));
                                    promotePawn(r, c);
                                    currTeam=(currTeam==1) ? 2:1;
                                    moveSelected=true;
                                }
                                clicked = 0;
                                gui.updateGUI(board.getBoard());
                                gui.updateTurn(currTeam);

                                if (getThreat() != null) {                              //marks check
                                    if (currTeam == 1)
                                        gui.setCheckColor(king1.getLocation());
                                    else
                                        gui.setCheckColor(king2.getLocation());
                                }
                            }
                        }
                        gui.getTiles()[r][c].getListener().unselect();
                        if(moveSelected)
                            return "movePiece "+moveFrom.getRow()+" "+moveFrom.getCol()+" "+r+" "+c+" "+board.getBoard()[r][c].getType();
                    }
            }
        }
        stateVictor(currTeam);
        return "gameOver "+currTeam;
    }

    public void movePiece(NPoint from, NPoint to)
    {
        int r=to.getRow();
        int c=to.getCol();

        board.movePiece(from, to);
        if (board.getBoard()[r][c] instanceof King)
            castle(from, to);
        promotePawn(r,c);
        currTeam = (currTeam==1) ? 2:1;
        gui.updateGUI(board.getBoard());
        gui.updateTurn(currTeam);
    }

    public void dropEnemyPiece(String name, int row, int col)
    {
        Piece p;
        NPoint loc=new NPoint(row,col);
        int team = (myTeam==1) ? 2:1;
        if(name.equals("Rook"))
            p=new Rook(team,loc);
        else if(name.equals("Bishop"))
            p=new Bishop(team,loc);
        else if(name.equals("Knight"))
            p=new Knight(team,loc);
        else if(name.equals("King"))
            p=new King(team,loc);
        else if(name.equals("Queen"))
            p=new Queen(team,loc);
        else
            p=new Pawn(team,loc);
        board.getBoard()[row][col]=p;
        gui.updateGUI(board.getBoard());
        gui.updateTurn(currTeam);
    }

    public void stateVictor(int i) {
        if (i != -1)                            //by checkmate
            gui.stateVictor(i);
        else                                    //by disconnect
            gui.stateVictor((myTeam==1)?2:1);

    }

    public void setMyTeam(int i)
    {
        myTeam=i;
        gui.addMyTeam(i);
    }

    public boolean checkIfAvail(NPoint p, List<NPoint> availMoves)
    {
        for(int i=0;i<availMoves.size();i++)
        {
            //System.out.println(availMoves.get(i).getRow()+","+availMoves.get(i).getCol()+" - "+p.getRow()+","+p.getCol());
            //System.out.println(availMoves.get(i).equals(p));
            if (availMoves.get(i).equals(p))
                return true;
        }
        return false;
    }

    public List<NPoint> getAdjMoves(NPoint p)
    {
        List<NPoint> availMoves=board.getBoard()[p.getRow()][p.getCol()].getAvailableMoves(board.getBoard());
        List<NPoint> adjMoves=new ArrayList<NPoint>();

        for(int i=0;i<availMoves.size();i++)
        {
            Piece displaced=board.testMovePiece(p,availMoves.get(i));           //@@ moves the piece and saves the taken piece
            if(!currTeamInCheck())                                          //@@ if king is not threatened by move
                adjMoves.add(availMoves.get(i));                            //@@ add this to adjusted moves
            board.testMovePiece(availMoves.get(i),p);                           //^^ moves pieces back
            board.setPiece(availMoves.get(i),displaced);                    //^^
        }

        King king=king1;                                                    //add castle moves
        if(currTeam==2)
            king=king2;
        if(p.equals(king.getLocation()))
        {
            List<NPoint> checkCastle=checkCastle();
            for (int i = 0; i < checkCastle.size(); i++)
                adjMoves.add(checkCastle.get(i));
        }

        return adjMoves;
    }


    public boolean currTeamInCheck()
    {
        int otherTeam=1;                                                                                    // %%% this section compiles one list of all the other team's available moves
        NPoint kingLoc=king2.getLocation();
        if(currTeam==1)
        {
            otherTeam = 2;
            kingLoc = king1.getLocation();
        }
        List<NPoint> pieces=board.getPieceLocs(otherTeam);
        List<NPoint> otherTeamMoves=new ArrayList<NPoint>();
        int size=0;

        for(int i=0;i<pieces.size();i++)
        {
            //List<NPoint> pieceMoves=getAdjMoves(pieces.get(i));
            List<NPoint> pieceMoves=board.getBoard()[pieces.get(i).getRow()][pieces.get(i).getCol()].getAvailableMoves(board.getBoard());
            for (int j = 0; j < pieceMoves.size(); j++)
                otherTeamMoves.add(pieceMoves.get(j));                // %%%
        }

        for(int i=0;i<otherTeamMoves.size();i++)                                                            //checks if other team is threatening this team's king
            if(otherTeamMoves.get(i).equals(kingLoc))
                return true;
        return false;
    }
    public Piece getThreat()
    {
        int otherTeam=1;                                                                                // %%% this version scrolls through all the adjusted moves of each piece
        NPoint kingLoc=king2.getLocation();
        if(currTeam==1)
        {
            currTeam=2;
            otherTeam = 2;
            kingLoc = king1.getLocation();
        }
        else
            currTeam=1;

        Piece ret=null;

        List<NPoint> pieces=board.getPieceLocs(otherTeam);

        for(int i=0;i<pieces.size();i++)
        {
            List<NPoint> adjMoves = getAdjMoves(pieces.get(i));
            for (int j = 0; j < adjMoves.size(); j++)
            {
                if(adjMoves.get(j).equals(kingLoc))
                    ret=board.getBoard()[pieces.get(i).getRow()][pieces.get(i).getCol()];
            }
        }                                                                                               // %%%

        if(currTeam==1)                                                                                 //this method switched the currTeam so getAdjMoves still works. Here it is switched back
            currTeam=2;
        else
            currTeam=1;
        return ret;
    }

    public List<NPoint> checkCastle()
    {
        List<NPoint> ret=new ArrayList<NPoint>();

        if(!currTeamInCheck())
        {
            King king = king1;
            if (currTeam == 2)
                king = king2;
            Piece[][] board = this.board.getBoard();


            int row = king.getLocation().getRow();
            int col = king.getLocation().getCol();

            if (!king.hasMoved())                                                   //TODO: add check for threats, and then in inOperation section, add rook's move
            {
                if (board[row][col + 3] instanceof Rook)
                    if (!((Rook) board[row][col + 3]).hasMoved())
                    {
                        boolean rightOpen = true;
                        for (int i = 1; i < 3; i++) {
                            if (!(board[row][col + i] instanceof Empty))
                                rightOpen = false;
                        }
                        if (rightOpen)
                        {
                            this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() + 1));
                            if (!currTeamInCheck())
                            {
                                this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() + 1));
                                if (!currTeamInCheck())
                                    ret.add(new NPoint(row, col + 2));    //**

                                this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() - 1));
                            }
                            this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() - 1));
                        }
                    }
                if (board[row][col - 4] instanceof Rook)
                    if (!((Rook) board[row][col - 4]).hasMoved())
                    {
                        boolean leftOpen = true;
                        for (int i = 1; i < 4; i++)
                            if (!(board[row][col - i] instanceof Empty))
                                leftOpen = false;
                        if (leftOpen)
                        {
                            this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() - 1));
                            if (!currTeamInCheck())
                            {
                                this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() - 1));
                                if (!currTeamInCheck())
                                    ret.add(new NPoint(row, col - 2));    //**

                                this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() + 1));
                            }
                            this.board.testMovePiece(king.getLocation(), new NPoint(king.getLocation().getRow(), king.getLocation().getCol() + 1));
                        }
                    }
            }
        }
        return ret;
    }

    public void promotePawn(int r,int c)
    {
        if (r == 0 && board.getBoard()[r][c] instanceof Pawn)
                board.setPiece(new NPoint(r, c), new Queen(1, new NPoint(r, c)));
        if (r == 7 && board.getBoard()[r][c] instanceof Pawn)
                board.setPiece(new NPoint(r, c), new Queen(2, new NPoint(r, c)));
    }

    public void castle(NPoint from, NPoint to)
    {
        int fcol=from.getCol();
        int tcol=to.getCol();

        if((fcol-tcol)==2)              //left castle
        {
            if(currTeam == 1)
                board.movePiece(new NPoint(7,0),new NPoint(7,3));
            else
                board.movePiece(new NPoint(0,0),new NPoint(0,3));
        }
        if((fcol-tcol)==-2)              //right castle
        {
            if(currTeam == 1)
                board.movePiece(new NPoint(7,7),new NPoint(7,5));
            else
                board.movePiece(new NPoint(0,7),new NPoint(0,5));
        }
    }
}
