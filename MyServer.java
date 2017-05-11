//import mayflower.net.*;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MyServer extends Server
{
    private Queue<Integer> clientsWaitingForGame;
    //private Map<Integer, TwoPlayerChess> games;
    private Map<Integer, Integer> otherPlayer;
    private Map<Integer, Integer> teammate;           //for bughouse
    private Set<Integer> whiteClients, blackClients;


    public MyServer(int port)
    {
        super(port);

        clientsWaitingForGame = new LinkedList<Integer>();
        //games = new HashMap<Integer, TwoPlayerChess>();
        otherPlayer = new HashMap<Integer, Integer>();
        whiteClients = new HashSet<Integer>();
        blackClients = new HashSet<Integer>();

        System.out.println("Waiting for clients on port " + getPort() + " at " + getIP());
    }

    /*
     *	Do something with a message sent from a client
     *
     *	Allowed Messages:
     *		movePiece [frow] [fcol] [trow] [tcol]
     *	    dropPiece [pieceType] [row] [col]
     *	    gameOver
     */
    public void process(int id, String message)
    {
        System.out.println("Message from client " +id+ ": " + message);

            //check if it is this players turn
            int thisTeam= whiteClients.contains(id) ? 1 : 2;

        //parse the row and col from the message
        String[] parts = message.trim().split(" ");
        if(parts[0].equals("movePiece") && parts.length >= 5)
        {
            try
            {
                send(otherPlayer.get(id), message);

            //    int currTeam=(thisTeam==1) ? 2:1;
            //    send(id,"currTeam "+currTeam);
            //   send(otherPlayer.get(id),"currTeam "+currTeam);
            }
            catch(Exception e)
            {
                send(id, "error invalid request: ["+message+"]");
            }
        }
        else if(parts[0].equals("dropPiece") && parts.length>=4)
        {
            //to be implemented for bughouse
        }
        else if(parts[0].equals("gameOver"))
        {
            send(otherPlayer.get(id),message);
            endGame(id, otherPlayer.get(id),teammate.get(id),otherPlayer.get(teammate.get(id)));
        }
        else
        {
            send(id, "error invalid request: ["+message+"]");
        }
    }

    /*
     *	Do something when a client connects.
     *
     *	For every 2nd client that connects:
     *		1. Create a new TicTacToe game for these clients
     *		2. Randomly assign each client X or O
     *		3. Assign clientId -> TicTacToe game in clientGames map
     */
    public void onJoin(int id)
    {
        //add new client to the game queue
        clientsWaitingForGame.add(id);
        System.out.println("Client connected: " + id);

        //If there are at least 2 clients waiting for a game...
        if(clientsWaitingForGame.size() >= 4)
        {
            //get the two clients that have been waiting the longest
            int clientA = clientsWaitingForGame.remove();
            int clientB = clientsWaitingForGame.remove();
            int clientC = clientsWaitingForGame.remove();
            int clientD = clientsWaitingForGame.remove();
            //create a mapping between each of they players so that you can always find the *other* player
            otherPlayer.put(clientA, clientB);
            otherPlayer.put(clientB, clientA);
            otherPlayer.put(clientC, clientD);
            otherPlayer.put(clientD, clientC);

            //flip a coin to pick which client is X and which is O
            //store each client in the appropriate Set (xClients, oClients)
            //send message to each client informing them of their piece (X, O)
            //This message also lets the client's know that their game is ready to be played
            teammate.put(clientA, clientC);
            teammate.put(clientB, clientD);


                whiteClients.add(clientA);
                whiteClients.add(clientD);
                blackClients.add(clientB);
                blackClients.add(clientC);

                send(clientA, "youare white");
                send(clientB, "youare black");
                send(clientD, "youare white");
                send(clientC, "youare black");

        }
    }

    /*
     *	Do something when a client disconnects
     *
     *	End the game, make the other player the winner!
     */
    public void onExit(int id)
    {
        System.out.println("Client disconnected: " + id);
        //check if this client is waiting in the queue
        if(clientsWaitingForGame.contains(id))
        {
            clientsWaitingForGame.remove(id);
        }

        //check if this player is already in a game
    /*    if(otherPlayer.get(id) != null)
        {
            //tell the other player that they win!
            send(otherPlayer.get(id), "winner disconnect");

            endGame(id, otherPlayer.get(id));
        }
     */
        try
        {
            send(otherPlayer.get(id), "winner disconnect");

            endGame(id, otherPlayer.get(id),teammate.get(id),otherPlayer.get(teammate.get(id)));

        }
        catch (Exception e)
        {
        }
    }

    private void endGame(int clientA, int clientB, int clientC, int clientD)
    {
        //disconnect both clients
        disconnect(clientA);
        disconnect(clientB);
        disconnect(clientC);
        disconnect(clientD);
        //remove the data associated with these clients from data structures:
        //otherPlayer, games, xClients, oClients
        otherPlayer.remove(clientA);
        otherPlayer.remove(clientB);
        otherPlayer.remove(clientC);
        otherPlayer.remove(clientD);

        whiteClients.remove(clientA);
        whiteClients.remove(clientB);
        whiteClients.remove(clientC);
        whiteClients.remove(clientD);
        blackClients.remove(clientA);
        blackClients.remove(clientB);
        blackClients.remove(clientC);
        blackClients.remove(clientD);
    }
}