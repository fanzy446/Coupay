package com.coupay.sellerclient.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

 

public class Client implements Runnable {
	private String clientId;           //the id of the server
	private String serverUrl = "";     // the url of the server
	private int serverPost;            //the post of the server
	private final long INTERVAL;       // interval is set to 30 seconds 
	
	private LinkedList<Message> unReadMessage = new LinkedList<Message>();
	
	public Client(){
		INTERVAL = 30 * 1000;
	}
	
	public Client(String clientId,int serverPost,int interval){
		this.clientId = clientId;
		this.serverPost = serverPost;
		INTERVAL = interval;
	}
	
	public boolean hasUnReadMessage(){
		return unReadMessage.size() > 0;
	}
	
	public LinkedList<Message> getUnReadMessage(){
		return unReadMessage;
	}
	
	/**
	 * this method defines the job of the client thread.
	 * the client connects to the server to see if there is
	 * message for it in every other interval.
	 */
	@Override
	public void run() {
		while(true){
			Socket socket;
			try {
				Thread.sleep(INTERVAL);
			    socket = new Socket(serverUrl,serverPost);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(clientId);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String hasMessage = in.readUTF();
				
				//check to see if there is unread message
				if(hasMessage != null && hasMessage.equals("yes")){
					boolean done = false;
					while(!done){
						String messageString = in.readUTF();
						if(messageString != null){
							if (messageString.equals("done!"))
								break;
							else{
								Message message = getMessage(messageString);
								JOptionPane.showMessageDialog(null,message.getContent());
							}
						}
					}
				}
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public int getServerPost() {
		return serverPost;
	}

	public void setServerPost(int serverPost) {
		this.serverPost = serverPost;
	}

	public long getINTERVAL() {
		return INTERVAL;
	}
	
	public Message getMessage(String messageString){
		JSONTokener parser = new JSONTokener(messageString);
		JSONObject json = (JSONObject)parser.nextValue();
		Message message= new Message();
		try{
			message.setContent(json.getString("content"));
			message.setDate(json.getString("date"));
			System.out.println(message.getContent());
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
}
