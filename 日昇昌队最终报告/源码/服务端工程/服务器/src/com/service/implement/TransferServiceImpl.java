package com.service.implement;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.dao.AccountDao;
import com.dao.RegisterDao;
import com.dao.TransferRecordDao;
import com.domain.Account;
import com.domain.Register;
import com.domain.TransferRecord;
import com.service.dataObject.TransferData;
import com.service.dataObject.TransferRecordData;
import com.service.interfaces.TransferService;

public class TransferServiceImpl implements TransferService {
	
	AccountDao accountDao;
	TransferRecordDao transferRecordDao;
	RegisterDao registerDao;
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setTransferRecordDao(TransferRecordDao transferRecordDao) {
		this.transferRecordDao = transferRecordDao;
	}

	public void setRegisterDao(RegisterDao registerDao) {
		this.registerDao = registerDao;
	}
	
	@Override
	public int doTransfer(TransferData data) {
		int result = -1;
		
		String sender = data.getSender();
		String receiver = data.getReceiver();
		double amount = data.getAmount();
		String paypassword = data.getPayPassword();
		
		Account account = accountDao.getAccount(sender);
		if("QRCode".equals(data.getType())){
			//若是QRCode交易，则需要验证密码
			if(!account.getPayPassword().equals(paypassword))
				result = 0;
		}
	    if(account.getBalance() < amount)
			result = 1;
		if(accountDao.doTransfer(sender, receiver, amount));
			result = 2;
		
		TransferRecord record = new TransferRecord();
		record.setId(generateTranferRecordNumber());
		Register s = registerDao.getRegister(sender);
		Register r = registerDao.getRegister(receiver);
		record.setSender(s);
		record.setReceiver(r);
		record.setAmount(amount);
		record.setDescription("hello");
		record.setTradeDate(new Timestamp(System.currentTimeMillis()));
		saveTransferRecord(record);
		String message = sender + " 于 " + record.getTradeDate() + " 给你转账" + amount + "元。";
		System.out.println(message);
		return result;
	}

	/**
	 * 生成账单号
	 */
	@Override
	public String generateTranferRecordNumber() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String recordNumber = "TR" + time;
		recordNumber = recordNumber.replaceAll("-", "").replaceAll(":", "").replace(".", "").replace(" ", "");
		char c1 = (char)('a' + Math.random() * 25);
		char c2 = (char)('a' + Math.random() * 25);
		recordNumber = recordNumber + c1 + c2;
		return recordNumber;
	}

	@Override
	public void saveTransferRecord(TransferRecord record) {
		transferRecordDao.saveTransferRecord(record);
	}

	@Override
	public TransferRecord getTransferRecord(String recordNumber) {
		return transferRecordDao.getTransferRecord(recordNumber);
	}

	@Override
	public List<TransferRecord> getReceivedTransferRecord(String name,int curpage,int pageSize) {
		return transferRecordDao.getReceivedTransferRecord(name, curpage, pageSize);
	}
	
	@Override
	public List<TransferRecord> getSentTransferRecord(String name,String currecord,int direction,int number) {
		return transferRecordDao.getSentTransferRecord(name, currecord, direction,number);
	}

	@Override
	public void deleteTransferRecord(TransferRecord record) {
		transferRecordDao.deleteTransferRecord(record);
	}

	@Override
	public List<TransferRecord> getSentTransferRecord(String name, int curpage,int pageSize) {
		return transferRecordDao.getSentTransferRecord(name, curpage, pageSize);
	}

}
