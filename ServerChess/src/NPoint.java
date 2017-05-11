/**
 * Created by Nathan on 3/12/2017.
 */
public class NPoint
{
    private int row;
    private int col;

    public NPoint(int row, int col)
    {
        this.row=row;
        this.col=col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public boolean equals(NPoint p)
    {
        if(getRow()==p.getRow() && getCol()==p.getCol())
            return true;
        else
            return false;
    }
}
