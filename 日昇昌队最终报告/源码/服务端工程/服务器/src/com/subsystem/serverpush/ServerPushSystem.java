package com.subsystem.serverpush;
import com.externalSystemInteface.IServerPushSystem;

public class ServerPushSystem implements IServerPushSystem {
	
	private IServerPushSystem server;
	
	public ServerPushSystem(){
	}
	
	public void setServer(IServerPushSystem server) {
		this.server = server;
		start();
	}

	public void start() {
		server.start();
		System.out.println("server starts!");
	}
	
	public void stop() {
		server.stop();
	}
	
	@Override
	public void addReceiver(String clientId) {
		server.addReceiver(clientId);
	}

	@Override
	public boolean hasMessage(String clientId) {
		return server.hasMessage(clientId);
	}

	@Override
	public void recordMessage(String clientId, com.domain.Message message) {
		server.recordMessage(clientId, message);
		addReceiver(clientId);		
	}

	@Override
	public void removeReceiver(String clientId) {
		server.removeReceiver(clientId);
	}

	@Override
	public void removeMessage(String clientId) {
		server.removeMessage(clientId);
	}
}
