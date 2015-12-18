package com.service.interfaces;

import java.util.List;


import com.domain.Customer;
import com.domain.Focus;
import com.domain.Friendship;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;
import com.domain.Share;
public interface CircleService {

	public void addRequest(Request request);
	
	public List<Register> getRequester(String responder);
	
	public void updateRequest(Request request);
	
	public void deleteRequest(Request request);
	
	public void respond(String responderName,String requesterName);
	
	public void respond(Request request);
	
	public void ignore(Request request);
	
	public boolean addFriendship(Friendship friendship);
	
	public void updateFriendship(Friendship friendship);
	
	public void deleteFriendship(String name1, String name2);
	
	public List<Register> getFriends(String name);
	
//	public void addComment(Comment comment);
//	
//	public List<Comment> getComment(List<String> friends);
	
	public List<Share> getTopicByPage(String name,int page,int pageSize);
	
	public void addFocus(Focus focus);
	
	public List<Seller> getFocusedSeller(String name, int page, int pageSize);
	
	public void deleteFocus(Focus focus);
	
	public List<Customer>getFocusingCustomer(String name, int page, int pageSize);

	public List<Share> getShareByPage(String name, int page, int pageSize);

	public Share getShare(int shareId);

	public void deleteShare(Share share);

	public void addShare(Share share);
	
	public boolean checkPicturePath(String path);
	
}
