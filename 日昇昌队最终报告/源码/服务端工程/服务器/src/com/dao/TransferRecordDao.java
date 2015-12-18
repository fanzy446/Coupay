package com.dao;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import com.domain.TransferRecord;
import com.service.dataObject.TransferRecordData;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 *
 * 封装TransferRecord实例CURD操作的dao类。
 */
public class TransferRecordDao extends MyDaoSupport {

	/**
	 * 保存TransferRecord实例
	 * @param record       要保存的实例
	 * @return             保存对象的标识属性值
	 */
	public String saveTransferRecord(TransferRecord record) {
		return (String) getHibernateTemplate().save(record);
	}
	
	/**
	 * 根据id查询TransferRecord实例
	 * @param id
	 * @return
	 */
	public TransferRecord getTransferRecord(String id) {
		return getHibernateTemplate().get(TransferRecord.class, id);
	}
	
	/**
	 * 分页查询收款纪录
	 * @param name            用户名
	 * @param page            当前页
	 * @param pageSize        每页记录数
	 * @return                当前页所有记录
	 */
	public List<TransferRecord> getReceivedTransferRecord(String name, int page, int pageSize) {
		String hql = "from TransferRecord where receiver.name='" + name + "'";
		@SuppressWarnings("unchecked")
		List<TransferRecord> list = findByPage(hql, page, pageSize);
		List<TransferRecord> records = new LinkedList<TransferRecord>();
		for(TransferRecord record : list) {
			TransferRecord data = new TransferRecord();
			data.setId(record.getId());
			data.setSender(record.getSender());
			data.setReceiver(record.getReceiver());
			data.setTradeDate(record.getTradeDate());
			data.setAmount(record.getAmount());
			records.add(data);
		}
		return records;
	}
	
	/**
	 * 分页查询付款记录
	 * @param name             用户名
	 * @param page             当前页
	 * @param pageSize         每页记录数
	 * @return                 当前页所有记录
	 */
	public List<TransferRecord> getSentTransferRecord(String name, int page,int pageSize) {
		String hql = "from TransferRecord where sender.name='" + name + "' order by tradeDate desc";
		@SuppressWarnings("unchecked")
		List<TransferRecord> list = findByPage(hql, page, pageSize);
		List<TransferRecord> records = new LinkedList<TransferRecord>();
		for(TransferRecord record : list) {
			TransferRecord data = new TransferRecord();
			data.setId(record.getId());
			data.setSender(record.getSender());
			data.setReceiver(record.getReceiver());
			data.setTradeDate(record.getTradeDate());
			data.setAmount(record.getAmount());
			records.add(data);
		}
		return records;
	}
	
	/**
	 * 获取以某一个账单号为基准的前/后指定数目的交易记录
	 * @param name                    用户名
	 * @param currecord               基准账单号
	 * @param direction               偏移方向（-1向上,1向下）
	 * @param number                  偏移范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransferRecord> getSentTransferRecord(String name,String currecord,int direction,int number) {
		String hql ="from TransferRecord where sender.name = '" + name + "' and tradeDate ";
		if(direction == -1)
			hql = hql + "< cast('";
		else hql = hql + " > cast('";
		TransferRecord currentrecord = getTransferRecord(currecord);
		Timestamp date = currentrecord.getTradeDate();
		hql = hql + date + "' as date) order by tradeDate desc";
		List<TransferRecord> list = new LinkedList<TransferRecord>();
		if(direction == 1)
			list = getHibernateTemplate().find(hql);
		else
			list = findByPage(hql, 1, number);
		List<TransferRecord> records = new LinkedList<TransferRecord>();
		for(TransferRecord record : list) {
			TransferRecord data = new TransferRecord();
			data.setId(record.getId());
			data.setSender(record.getSender());
			data.setReceiver(record.getReceiver());
			data.setTradeDate(record.getTradeDate());
			data.setAmount(record.getAmount());
			records.add(data);
		}
		return records;
	}
	
	/**
	 * 删除TransferRecord实例
	 * @param record             要删除的实例
	 */
	public void deleteTransferRecord(TransferRecord record) {
		getHibernateTemplate().delete(record);
	}

}
