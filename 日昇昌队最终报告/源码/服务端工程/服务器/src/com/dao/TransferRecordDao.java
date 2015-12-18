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
 * ��װTransferRecordʵ��CURD������dao�ࡣ
 */
public class TransferRecordDao extends MyDaoSupport {

	/**
	 * ����TransferRecordʵ��
	 * @param record       Ҫ�����ʵ��
	 * @return             �������ı�ʶ����ֵ
	 */
	public String saveTransferRecord(TransferRecord record) {
		return (String) getHibernateTemplate().save(record);
	}
	
	/**
	 * ����id��ѯTransferRecordʵ��
	 * @param id
	 * @return
	 */
	public TransferRecord getTransferRecord(String id) {
		return getHibernateTemplate().get(TransferRecord.class, id);
	}
	
	/**
	 * ��ҳ��ѯ�տ��¼
	 * @param name            �û���
	 * @param page            ��ǰҳ
	 * @param pageSize        ÿҳ��¼��
	 * @return                ��ǰҳ���м�¼
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
	 * ��ҳ��ѯ�����¼
	 * @param name             �û���
	 * @param page             ��ǰҳ
	 * @param pageSize         ÿҳ��¼��
	 * @return                 ��ǰҳ���м�¼
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
	 * ��ȡ��ĳһ���˵���Ϊ��׼��ǰ/��ָ����Ŀ�Ľ��׼�¼
	 * @param name                    �û���
	 * @param currecord               ��׼�˵���
	 * @param direction               ƫ�Ʒ���-1����,1���£�
	 * @param number                  ƫ�Ʒ�Χ
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
	 * ɾ��TransferRecordʵ��
	 * @param record             Ҫɾ����ʵ��
	 */
	public void deleteTransferRecord(TransferRecord record) {
		getHibernateTemplate().delete(record);
	}

}
