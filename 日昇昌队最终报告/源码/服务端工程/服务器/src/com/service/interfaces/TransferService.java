package com.service.interfaces;

import java.util.List;

import com.domain.TransferRecord;
import com.service.dataObject.TransferData;
import com.service.dataObject.TransferRecordData;


public interface TransferService {
	public int doTransfer(TransferData data);
	
	public String generateTranferRecordNumber();
	
	public void saveTransferRecord(TransferRecord record);
	
	public TransferRecord getTransferRecord(String recordNumber);
	
	public List<TransferRecord> getReceivedTransferRecord(String name,int curpage,int pageSize);
	
	public List<TransferRecord> getSentTransferRecord(String name,String currecord,int direction,int number);
	
	public List<TransferRecord> getSentTransferRecord(String name,int curpage,int pageSize);
	
	public void deleteTransferRecord(TransferRecord record);
}
