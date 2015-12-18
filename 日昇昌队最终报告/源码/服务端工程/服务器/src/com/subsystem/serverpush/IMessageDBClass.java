package com.subsystem.serverpush;

import java.util.List;

public interface IMessageDBClass {
	public void recordMessage(String clientId,String message);
	public List<Message> getMessage(String clientId);
	public void removeMessage(String clientId);
	
}
