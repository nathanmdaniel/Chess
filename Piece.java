import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.awt.*;
import java.util.List;

/**
 * Created by Nathan on 3/11/2017.
 */
public interface Piece
{
    int getTeam();
    String getType();
    List<NPoint>  getAvailableMoves(Piece[][] board);
    String getImageLoc();
    void setLocation(NPoint p);
    void testSetLocation(NPoint p);
}
