package com.externalSystemInteface;
/**
 * 
 * @author luo
 * ����ҵ��ӿڣ������ǵ�ϵͳ�ṩ����Ӧ�����У�������ʵ��
 */
public interface IBankSystem {
	public boolean checkAccount(String cardNumber);                     //����˻��Ƿ����
	public boolean checkPassword(String cardNumber,String password);           //��֤����
	public double getBalance(String cardNumber,String password);     //��ѯ���
	public boolean deposit(String cardNumber,double amount);                            //��Ǯ
	public boolean withDraw(String cardNumber,String password,double amount);                          //ȡǮ
	public String getUserName(String cardNumber);                           //��ѯ�ͻ�����
	public void doTransfer(String sender,String receiver,double amount);
}
