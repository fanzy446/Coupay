package com.externalSystemInteface;

import com.domain.Message;

public interface IServerPushSystem {
	public void addReceiver(String clientId);
	public boolean hasMessage(String clientId);
	public void recordMessage(String clientId, Message message);
	public void removeReceiver(String clientId);
	public void removeMessage(String clientId);
	public void start();
	public void stop();
}
