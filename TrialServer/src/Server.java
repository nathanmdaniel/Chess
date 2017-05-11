import java.util.Map;

//import mayflower.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Server extends Thread
{
	private int port;
	private boolean running;
	private Map<Integer, ServerSideClient> clients;
	private int nextClientId;
	private Socket clientSocket;
	private List<Logger> loggers;
	
	public Server(int port)
	{
		this(port, false);
	}
	
	public Server(int port, boolean delayStart)
	{
		this.port = port;
		this.clients = new HashMap<Integer, ServerSideClient>();
		this.loggers = new ArrayList<>();
		nextClientId = 1;
		if(!delayStart)
			start();
	}
	
	public void run()
	{
		running = true;
		ServerSocket serverSocket = null;
		
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch(Exception e)
		{
			log(e.getMessage());
			running = false;
		}
		
		ServerSideClient client;
		while(running)
		{
			try
			{
				//ServerSocket serverSocket = new ServerSocket(port);
				clientSocket = serverSocket.accept();
				client = new ServerSideClient(getNextClientId(), clientSocket, this);
				clients.put(client.getClientId(), client);
				client.start();
				onJoin(client.getClientId());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			if(null != clientSocket)
				clientSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void shutdown()
	{
		running = false;
		
		try
		{
			if(null != clientSocket)
				clientSocket.close();
			disconnectAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getPort()
	{
		return port;
	}
	
	public String getIP()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "???.???.???.???";
	}
	
	public void send(int id, String message)
	{
		ServerSideClient client = clients.get(id);
		if(null != client)
		{
			client.send(message);
		}
	}
	
	public void send(String message)
	{
		ServerSideClient[] clients = this.clients.values().toArray(new ServerSideClient[] {});
		for(ServerSideClient client : clients)
		{
			send(client.getClientId(), message);
		}
	}
	
	public void disconnect(int id)
	{
		ServerSideClient client = clients.remove(id);
		if(null != client)
		{
			client.send("disconnect");
			client.disconnect();
			onExit(id);
		}
	}
	
	public void disconnectAll()
	{
		ServerSideClient[] clients = this.clients.values().toArray(new ServerSideClient[] {});
		for(ServerSideClient client : clients)
		{
			disconnect(client.getClientId());
		}
	}
	
	public int numConnects()
	{
		return clients.size();
	}
	
	public void log(String message)
	{
		System.out.println(message);
		for(Logger logger : loggers)
			logger.log(message);
	}
	
	public void addLogger(Logger logger)
	{
		loggers.add(logger);
	}
	
	public abstract void process(int id, String message);
	public abstract void onJoin(int id);
	public abstract void onExit(int id);
	
	private int getNextClientId()
	{
		return nextClientId++;
	}
}
