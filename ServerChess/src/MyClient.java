/**
 * Created by Nathan on 5/6/2017.
 */
import java.util.Scanner;

public class MyClient extends Client
{
    private ChessManager game;

    public MyClient()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Use localhost to connect to a server running on your computer.");
        //System.out.print("IP Address > ");
        //String ip = in.next();
        String ip="localhost";

        //System.out.print("Port > ");
        //int port = in.nextInt();
        int port = 1234;				//default server port

        System.out.println("Connecting...");
        connect(ip, port);			//connect to the server at the specified ip and port
    }

    /*
     *	Messages:
     *		youare [piece]
     *		movePiece [frow] [fcol] [trow] [tcol]
     *		winner disconnect
     *		error [message...]
     *	    currTeam [currTeam]
     *	    dropPiece [pieceType] [row] [col]
     *	    gameOver
     *	    addDropPiece [pieceType]
     */
    public void process(String message)
    {
        //output the message recieved from the server. This is useful for debugging
        System.out.println("Message from server: " + message);

        //split message into an array of Strings separated by space characters
        //	"addpiece 1 2" would become the array ["addpiece", "1", "2"]
        String[] parts = message.split(" ");

        //use the first value in the array as the "command"
        //use if statements to determine which command to process
        if("youare".equals(parts[0]))
        {

            Board board=new Board();
            ChessGUI gui=new ChessGUI(board.getBoard());
            game=new ChessManager(board,gui);


            int myTeam=(parts[1].equals("white")) ? 1:2;
            game.setMyTeam(myTeam);
            if(myTeam==1)
                send(game.getNextMove());

        }
        else if("movePiece".equals(parts[0]))
        {
            //Integer.parseInt() can throw an exception
            int frow = Integer.parseInt(parts[1]);
            int fcol = Integer.parseInt(parts[2]);
            int trow = Integer.parseInt(parts[3]);
            int tcol = Integer.parseInt(parts[4]);

            NPoint from=new NPoint(frow, fcol);
            NPoint to=new NPoint(trow, tcol);

            game.movePiece(from, to);

            String nextMove=game.getNextMove();
            String[] response=nextMove.split(" ");
            if(response[0].equals("movePiece"))
            {
                String piece=response[5];
                if(!piece.equals("Empty"))
                    send("giveAlly "+piece);
            }
            send(nextMove);
        }
        else if("error".equals(parts[0]))
        {
            //Output the error message sent from the server
            System.out.println(message);
        }
        else if("winner".equals(parts[0]))
        {
            //Congratulations, you win because you opponent quit!
            System.out.println("Opponent disconnected. You win!");
            game.stateVictor(-1);
        }
        else if("dropPiece".equals(parts[0]))
        {
            game.dropEnemyPiece(parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
        }
        else if("addDropPiece".equals(parts[0]))
        {
            game.addDropPiece(parts[1]);
        }
        else if("gameOver".equals(parts[0]))
        {
            game.stateVictor(Integer.parseInt(parts[1]));
        }

    }

    public void onDisconnect(String message)
    {
        System.out.println("Disconnected from server.");
    }

    public void onConnect()
    {
        System.out.println("Connected!");
    }


}