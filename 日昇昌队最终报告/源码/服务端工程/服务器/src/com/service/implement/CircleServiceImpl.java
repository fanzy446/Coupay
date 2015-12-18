package com.service.implement;

import java.util.LinkedList;
import java.util.List;
import com.dao.CircleEntityDao;
import com.domain.Customer;
import com.domain.Focus;
import com.domain.Friendship;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;
import com.domain.Share;
import com.service.interfaces.CircleService;

public class CircleServiceImpl implements CircleService {

	private CircleEntityDao dao;
	
	
	public void setDao(CircleEntityDao dao) {
		this.dao = dao;
	}

	@Override
	public void addRequest(Request request) {
		dao.saveRequest(request);
	}

	@Override
	public List<Register> getRequester(String responder) {
		List<Request> list = dao.getReceivedRequest(responder);
		List<Register> registers = new LinkedList<Register>();
		for(Request r : list)
			registers.add(r.getRequester());
		return registers;
	}

	@Override
	public void updateRequest(Request request) {
		dao.updateRequest(request);
	}

	@Override
	public void deleteRequest(Request request) {
		dao.deleteRequest(request);
	}

	@Override
	public void respond(String responderName, String requesterName) {
		
	}

	@Override
	public void respond(Request request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignore(Request request) {
		dao.deleteRequest(request);
	}

	@Override
	public boolean addFriendship(Friendship friendship) {
		try{
			dao.saveFriendShip(friendship);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public void updateFriendship(Friendship friendship) {
		dao.updateFriendship(friendship);
	}

	@Override
	public void deleteFriendship(String name1, String name2) {
		List<Friendship> list = dao.getFriendship(name1, name2);
		if(list != null && list.size() == 2){
			dao.deleteFriendship(list.get(0));
			dao.deleteFriendship(list.get(1));
		}
	}

	@Override
	public void addShare(Share share) {
		dao.saveShare(share);
	}

	@Override
	public void deleteShare(Share share) {
		dao.deleteShare(share);
	}

	@Override
	public Share getShare(int shareId) {
		return dao.getShare(shareId);
	}

	@Override
	public List<Share> getShareByPage(String name, int page, int pageSize) {
		return dao.getShare(name, page, pageSize);
	}

	@Override
	public List<Share> getTopicByPage(String name, int page, int pageSize) {
		return dao.getTopic(name, page, pageSize);
	}

	@Override
	public void addFocus(Focus focus) {
		dao.saveFoucus(focus);
	}

	@Override
	public List<Seller> getFocusedSeller(String customerName, int page, int pageSize) {
		return dao.getFocusedSeller(customerName, page, pageSize);
	}

	@Override
	public void deleteFocus(Focus focus) {
		dao.deleteFocus(focus);
	}

	@Override
	public List<Customer> getFocusingCustomer(String sellerName, int page, int pageSize) {
		return dao.getFocusingCustomer(sellerName, page, pageSize);
	}

//	@Override
//	public void addComment(Comment comment) {
//		dao.saveComment(comment);
//		
//	}

	@Override
	public List<Register> getFriends(String name) {
		List<Friendship> friendship = dao.getFriendship(name);
		List<Register> friends = new LinkedList<Register>();
		for(Friendship f: friendship){
			friends.add(f.getPerson2());
		}
		return friends;
	}

	@Override
	public boolean checkPicturePath(String path) {
		return dao.checkPicturePath(path);
	}

//	@Override
//	public List<Comment> getComment(List<String> friends) {
//		for(String name: friends){
//			
//		}
//		return null;
//	}

}
