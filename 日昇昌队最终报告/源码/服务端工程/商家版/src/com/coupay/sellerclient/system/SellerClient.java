package com.coupay.sellerclient.system;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.security.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.coupay.sellerclient.data.TransferRecord;
import com.coupay.sellerclient.util.DateChooserJButton;

public class SellerClient extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel tip1;
	private JLabel tip2;
	private JLabel tip3;
	private JLabel tip4;
	private JLabel tip5;
	private JLabel tip6;
	private JLabel titleLable;
	
	private DateChooserJButton startDateButton;
	private DateChooserJButton endDateButton;
	
	private JTable table;
	
	private JButton prePageButton;
	private JButton nextPageButton;
	private JButton firstPageButton;
	private JButton lastPageButton;
	private JSpinner minAmountSpinner;
	private JSpinner maxAmountSpinner;
	
	JTextField clientNameFiled;
	JTextField currentPageFiled;
	
	public SellerClient() {
		super();
		initializeComponent();
		arrangeLayout();
		setSize(860, 640);
		setLocation(50, 30);
		setVisible(true);
	}
	
	private void initializeComponent() {
		tip1 = new JLabel("开始时间：");
		tip1.setFont(new Font("Dialog", 1, 20));
		tip2 = new JLabel("结束时间：");
		tip2.setFont(new Font("Dialog", 1, 20));
		tip3 = new JLabel("金额范围");
		tip3.setFont(new Font("Dialog", 1, 20));
		tip4 = new JLabel(">");
		tip4.setFont(new Font("Dialog", 1, 20));
		tip5 = new JLabel("<");
		tip5.setFont(new Font("Dialog", 1, 20));
		tip6 = new JLabel("客户名：");
		tip6.setFont(new Font("Dialog", 1, 20));
		titleLable = new JLabel("销售收款纪录：");
		titleLable.setFont(new Font("Dialog", 1, 30));
		startDateButton = new DateChooserJButton();
		endDateButton = new DateChooserJButton();
		
		table = new JTable();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnCount(4);
		Object[] header = { "记录号","客户名称","付款金额","付款日期"};
		model.setColumnIdentifiers(header);
		table.setModel(model);
		table.setRowHeight(25);
		
		clientNameFiled = new JTextField();
		currentPageFiled = new JTextField("0");
		currentPageFiled.setFont(new Font("Dialog", 1, 20));
		
		minAmountSpinner = new JSpinner();
		maxAmountSpinner = new JSpinner();
		
		firstPageButton = new JButton("首页");
		prePageButton = new JButton("上一页");
		nextPageButton = new JButton("下一页");
		lastPageButton = new JButton("尾页");
		
	}
	
	public void arrangeLayout() {
		JPanel panel1 = new JPanel(new FlowLayout());
		panel1.add(tip1);
		panel1.add(startDateButton);
		panel1.add(tip2);
		panel1.add(endDateButton);
		panel1.add(tip3);
		panel1.add(tip4);
		panel1.add(minAmountSpinner);
		panel1.add(maxAmountSpinner);
		panel1.add(tip5);
		panel1.add(maxAmountSpinner);
		panel1.add(tip6);
		panel1.add(clientNameFiled);
		
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(firstPageButton);
		panel2.add(prePageButton);
		panel2.add(currentPageFiled);
		panel2.add(nextPageButton);
		panel2.add(lastPageButton);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.add(panel1,"North");
		
		JPanel panel4 = new JPanel();
		panel4.add(titleLable);
		
		panel3.add(panel4,"South");
		
		JScrollPane pane = new JScrollPane(table);
		
		Container container = this.getContentPane();
		container.add(panel3,"North");
		container.add(pane,"Center");
		container.add(panel2,"South");
	}
	
	public void displayRecords(List<TransferRecord> records) {
		int i = 0;
		for(TransferRecord record : records) {
			table.setValueAt(record.getRecordNumber(), i, 0);
			table.setValueAt(record.getCustomerName(), i, 1);
			table.setValueAt(record.getAmount(), i, 2);
			table.setValueAt(record.getDate(), i, 3);
		}
	}
	
	public static void main(String[] args) {
		SellerClient client = new SellerClient();
		client.setVisible(true);
	}

	public List<TransferRecord> getRecords(JSONObject jobj) {
		JSONArray array = jobj.getJSONArray("records");
		List<TransferRecord> records = new LinkedList<TransferRecord>();
		for(int i = 0; i < array.size(); i++) {
			JSONObject item = array.getJSONObject(i);
			TransferRecord record = new TransferRecord();
			record.setRecordNumber(item.getString("recordNumber"));
			record.setCustomerName(item.getString("sender"));
			record.setAmount(item.getDouble("amount"));
			record.setDate(item.getString("date"));
		}
		return null;
	}
}
