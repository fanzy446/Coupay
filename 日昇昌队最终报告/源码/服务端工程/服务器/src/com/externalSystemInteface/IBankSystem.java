package com.externalSystemInteface;
/**
 * 
 * @author luo
 * 银行业务接口，有我们的系统提供给相应的银行，由银行实现
 */
public interface IBankSystem {
	public boolean checkAccount(String cardNumber);                     //检查账户是否存在
	public boolean checkPassword(String cardNumber,String password);           //验证密码
	public double getBalance(String cardNumber,String password);     //查询余额
	public boolean deposit(String cardNumber,double amount);                            //存钱
	public boolean withDraw(String cardNumber,String password,double amount);                          //取钱
	public String getUserName(String cardNumber);                           //查询客户姓名
	public void doTransfer(String sender,String receiver,double amount);
}
