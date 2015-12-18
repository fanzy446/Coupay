package com.subsystem.serverpush;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*Server server = Server.getInstance();
		Server.addReceiver("0001");
		new Thread(server).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Client client = new Client("0001", 5000);
		new Thread(client).start();*/
		
		/*String pname = "collect";
		String[] param = {"0001",new Date(System.currentTimeMillis()).toLocaleString()};
		DBProcess db = new DBProcess();
		db.setConnection(DBConnector.getConnection());
		CallableStatement cs = db.callProcedure(pname, param, null);
		try {
			ResultSet rs = cs.getResultSet();
			if(rs.next())
				System.out.println(rs.getString("payer"));
				System.out.println(rs.getTime("tradetime"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(formate.format(new Date(System.currentTimeMillis())));
	}
}
