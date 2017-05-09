/**
 * Created by Nathan on 3/3/2017.
 */
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ChessGUI
{
    private Tile[][] tiles;
    private Piece[][] board;
    private JLabel info;
    private JLabel team;

    public ChessGUI(Piece[][] board)
    {
        this.board=board;
        //frame
        JFrame j=new JFrame();
        j.setIconImage(new ImageIcon("Chess-icon.png").getImage());
        j.setSize(660,550);
        j.setTitle("Chess GUI");
        j.setLayout(new BorderLayout(3,3));


        //chess board section
        tiles=new Tile[8][8];
        JPanel p=new JPanel();
        j.add(p,BorderLayout.CENTER);
        p.setLayout(new GridLayout(8,8));
        for(int i=0;i<8;i++)
            for(int q=0;q<8;q++)
            {
                Tile button=new Tile(new ImageIcon(board[i][q].getImageLoc()), new NPoint(i,q), new NButtonListener(new NPoint(i,q)));
                tiles[i][q]=button;
                p.add(button);
                if((i+q)%2!=0)
                    button.setBackground(new Color(20,65,80));
                else
                    button.setBackground(new Color(200,230,240));

            }

        //right side section
        JPanel rs=new JPanel(new BorderLayout(3,3));
        j.add(rs,BorderLayout.EAST);
            //extra piece section
            JPanel ep= new JPanel(new GridLayout(8,2));
            rs.add(ep,BorderLayout.CENTER);
            ep.setPreferredSize(new Dimension(130,550));
            /*for(int i=0;i<8;i++)
                for(int q=0;q<2;q++)
                {
                    JButton button=new JButton();
                    ep.add(button);
                    button.setBackground(new Color(205, 121, 106)); //kinda ugly color
                }*/
            //info text section
            info=new JLabel("White's Turn");
            rs.add(info,BorderLayout.SOUTH);

            team=new JLabel("Playing as");
            rs.add(team,BorderLayout.NORTH);


        //timer section         //prob wont do this lol


        j.setVisible(true);
    }

    public Tile[][] getTiles() {
        return tiles;
    }


    public void showAvailableMoves(List<NPoint> availMoves)
    {

        for(int i=0;i<availMoves.size();i++)
            if(board[availMoves.get(i).getRow()][availMoves.get(i).getCol()] instanceof Empty)
                tiles[availMoves.get(i).getRow()][availMoves.get(i).getCol()].setBackground(new Color(210, 190, 150));
            else
                tiles[availMoves.get(i).getRow()][availMoves.get(i).getCol()].setBackground(new Color(164, 76, 60));
    }

    public void updateGUI(Piece[][] board)
    {
        this.board=board;
        for(int i=0;i<8;i++)
            for(int q=0;q<8;q++)
            {
                tiles[i][q].setImage(board[i][q].getImageLoc());
                if((i+q)%2!=0)
                    tiles[i][q].setBackground(new Color(20,65,80));
                else
                    tiles[i][q].setBackground(new Color(200,230,240));

            }
    }

    public void setCheckColor(NPoint p)
    {
        //tiles[p.getRow()][p.getCol()].setBackground(new Color(170,70,70));
        tiles[p.getRow()][p.getCol()].setBackground(new Color(128, 44, 28));
    }

    public void updateTurn(int team)
    {
        if(team==1)
            info.setText("White's Turn");
        else
            info.setText("Black's Turn");
    }

    public void stateVictor(int team)               //switched white/black from local
    {
        if(team==1)
            info.setText("White has Fallen");
        else
            info.setText("Black has Fallen");
    }

    public void addMyTeam(int i)
    {
        if(i==1)
            team.setText("Playing as White");
        else
            team.setText("Playing as Black");
    }
}
