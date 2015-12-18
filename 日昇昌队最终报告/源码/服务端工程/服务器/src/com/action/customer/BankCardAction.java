package com.action.customer;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.BankAccount;
import com.domain.BankCard;
import com.domain.Register;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.BankAccountService;
import com.service.interfaces.BankCardService;
import com.service.interfaces.RegisterService;
import com.util.ResultCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BankCardAction extends ActionSupport implements SessionAware{
	private BankCardService bankCardService;
	private BankAccountService bankAccountService;
	private RegisterService registerService;
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	
	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setBankCardService(BankCardService bankCardService) {
		this.bankCardService = bankCardService;
	}

	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	
	
	public BankCardService getBankCardService() {
		return bankCardService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public String bindCard() {
		System.out.println("begin");
		int result;
		BankCard card  = new BankCard();
		JSONObject data = (JSONObject) session.get("data");
		String cardNumber = data.getString("cardNumber");
		String password = data.getString("password");
		BankAccount account = bankAccountService.getBankAccount(cardNumber);
		if(account == null) 
			result = ResultCode.ACCOUNT_NOT_EXISTS;
		if(!account.getPassword().equals(password)) 
			result= ResultCode.WRONG_PASSWORD;
		else {
			card.setBankName(account.getBankName());
			card.setCardNumber(cardNumber);
			String name = data.getString("name");
			Register owner = registerService.getRegister(name);
			card.setOwner(owner);
			bankCardService.addBankCard(card);
			result = ResultCode.SUCCESS;
		}
		System.out.println("success");
		data.clear();
		data.put("cardNumber", card.getCardNumber());
		data.put("bankName", card.getBankName());
		response.put("result", result);
		return "success";
	}
	
	public String getBindedCards() {
		try {
			System.out.println("begin");
			JSONObject data = (JSONObject) session.get("data");
			String name = data.getString("name");
			List<BankCard> cards = bankCardService.getBankCards(name);
			JSONArray array = new JSONArray();
			for(BankCard card: cards) {
				JSONObject jobj = new JSONObject();
				jobj.put("cardNumber", card.getCardNumber());
				jobj.put("bankName", card.getBankName());
				array.add(jobj);
			}
			
			data.clear();
			data.put("cardList",array);
			System.out.println("success");
			return "success";
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
