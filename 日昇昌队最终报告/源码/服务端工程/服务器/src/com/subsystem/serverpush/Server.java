package com.subsystem.serverpush;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dao.MessageDao;
import com.externalSystemInteface.IServerPushSystem;

/**
 * 
 * @author luo
 *  server class of the push solution
 *
 */
public class Server implements Runnable , IServerPushSystem{
	private ServerSocket server ;   // the server
	private ExecutorService executor;  
	private MessageDao dao;     
	private HashMap<String,Integer> receiverMap = new HashMap<String, Integer>();// map of message to be sent
	private Thread serverThread;
	
	public static void main(String[] args){
		
	}

	/**
	 *Constructor
	 */
	public Server(){
		try {
			server = new ServerSocket(5000,100);
			executor = Executors.newCachedThreadPool();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server(int serverPost,int max) {
		try {
			server = new ServerSocket(serverPost,max);
			executor = Executors.newCachedThreadPool();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * define a job to be done by the server
	 */
	public void run() {
		while(true){
			try {
				// wait for the client to get connection
				Socket client = server.accept();
				System.out.println("h");
				DataInputStream in = new DataInputStream(client.getInputStream());
				String clientId = in.readUTF();
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				if(hasMessage(clientId)){
					System.out.println("soehtow");
					out.writeUTF("yes"); // notify the client that there are messages for it
					executor.execute(new CheckService(client, clientId));
				}
				else 
					out.writeUTF("no");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @author luo
	 * a inner class that defines the service provided to the client
	 * which checks whether there are unsent message to be sent to the
	 * client
	 */
	private class CheckService implements Runnable {
		private Socket client;                 // the client
		private String clientId;               // the id of the client
		private DataOutputStream out;
		public CheckService(Socket client,String clientId){
			this.client = client;
			this.clientId = clientId;
			initialize();
		}
		private void initialize(){
			try {
				out = new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			List<com.domain.Message> messages = getMessage(clientId);
			Iterator<com.domain.Message> it = messages.iterator();
			try {
				while(it.hasNext()){
					com.domain.Message message = it.next();
					out.writeUTF(message.getContent());
					out.flush();
				}
				
				out.writeUTF("done!");
				removeReceiver(clientId);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	/**
	 * 
	 * @param clientId  the id of the target client
	 * add target to the map
	 */
	public void addReceiver(String clientId) {
		receiverMap.put(clientId, 1);
	}

	/**
	 * 
	 * @param clientId   the id of the client
	 * @return whether there are message for the client
	 */
	public boolean hasMessage(String clientId) {
		return receiverMap.get(clientId) != null;
	}

	public void recordMessage(com.domain.Message message) {
		dao.saveMessage(message);
	}


	/**
	 * 
	 * @param clientId   the id of the client
	 * after messages sent to the receiver ,remove the receiver in the map
	 */
	public void removeReceiver(String clientId) {
		receiverMap.remove(clientId);	
	}
	
	public void removeMessage(String clientId) {

	}

	public List<com.domain.Message> getMessage(String clientId) {	
		return dao.getMessages(clientId);
	}
	
	public void start(){
		serverThread = new Thread(this);
		serverThread.start();
	}
	@SuppressWarnings("deprecation")
	public void stop(){
		serverThread.destroy();
		try {
			if(server != null)
				server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void recordMessage(String clientId, com.domain.Message message) {
		dao.saveMessage(message);
	}
	
	
	public void setDao(MessageDao dao) {
		this.dao = dao;
		System.out.println("dao is set!");
	}

}
