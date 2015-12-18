package com.coupay.sellerclient.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.coupay.sellerclient.data.Product;
import com.coupay.sellerclient.external.Client;
import com.coupay.sellerclient.external.productCatalog;
import com.coupay.sellerclient.util.IoDevice;
import com.pengo.capture.CameraDialog;

public class ProductsTable extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel titleLable;
	private JLabel tipLable;
	//private JLabel totalLable;
	private JButton scanButton;
	private JButton clearButton;
	private JButton removeButton;
	private JButton qrcodeButton;
	
	private JTextField totalField;
	
	private String clientId;
	private HashMap<String, Product> products;
	private HashMap<String, Integer> quantity;
	private productCatalog catalog; 
	private IoDevice device;
	
	private Client pushClient;
	private Thread clientThread;
	
	public ProductsTable() {
		super();
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(100, 50);
		setSize(680, 540);
		initializeComponent();
		arrangeLayout();
		addListener();	
	}
	
	/**
	 * initialize the components
	 */
	private void initializeComponent() {
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return column == 4;
			}
		};
		model.setColumnCount(7);
		model.setRowCount(0);
		Object[] headers = { "商品号","商品名称","商品种类","商品单价","商品数量"
				,"商品单位","小结"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		table.setRowHeight(25);
		//table.setPreferredScrollableViewportSize(new Dimension(600,30));
		
		titleLable = new JLabel("商品信息列表");
		titleLable.setFont(new Font("Dialog", 1, 30));
		tipLable = new JLabel("合计：");
		tipLable.setFont(new Font("Dialog",1,24));
		/*totalLable = new JLabel("0");
		totalLable.setFont(new Font("Dialog",1,24));*/
		
		totalField = new JTextField();
		totalField.setFont(new Font("Dialog",1,22));
		totalField.setSize(100, 25);
		totalField.setText("    0    ");
		
		scanButton = new JButton("扫描");
		clearButton = new JButton("清除");
		removeButton = new JButton("删除");
		qrcodeButton = new JButton("生成二维码");
		
		catalog = new productCatalog();
		quantity = new HashMap<String, Integer>();
		products = new HashMap<String, Product>();
		device = new IoDevice();
	}
	
	public void addListener() {
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE && e.getColumn() == 4) {
					int row = e.getLastRow();
					updateQuantity(row);
				}
			}
		});
		
		scanButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scan();
			}
		});
		
		qrcodeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateQrcode();
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		
	}
	
	/**
	 * arrange the layout of the components
	 */
	public void arrangeLayout() {
		 JPanel panel1 = new JPanel();
		 panel1.add(titleLable);
		 
		 JScrollPane pane = new JScrollPane(table);
		 
		 JPanel panel2 = new JPanel(new FlowLayout());
		 panel2.add(scanButton);
		 panel2.add(qrcodeButton);
		 panel2.add(removeButton);
		 panel2.add(clearButton);
		 panel2.setOpaque(false);
		 
		 JPanel panel3 = new JPanel();
		 panel3.add(tipLable, "Center");
		 /*panel3.add(totalLable,"East");*/
		 panel3.add(totalField,"East");
		 
		 JPanel panel4 = new JPanel(new BorderLayout());
		 panel4.add(panel1,"North");
		 panel4.add(panel3,"South");
		 
		 Container contentPane = getContentPane();
		 contentPane.add(panel4, "North");
		 contentPane.add(pane,"Center");
		 contentPane.add(panel2,"South");
		 
	}
	
	public void scan() {
		Thread thread = new Thread(new ScanThread());
		thread.run();
	}
	
	public void generateQrcode() {
		String content = clientId + "-" + totalField.getText();
		device.generateAndDisplayQrcode(content);
	}

	public void clear() {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		if(quantity != null)
			quantity.clear();
		if(products != null)
			products.clear();
		/*totalLable.setText("0");*/
		totalField.setText("0");
	}
	
	/**
	 * 删除商品
	 */
	public void remove() {
		int[] rows = table.getSelectedRows();	
		if(rows.length == 0) {
			JOptionPane.showMessageDialog(this, "请选择要删除的商品");
			return ;
		}
		System.out.println(rows.length);
		for(int i = 0; i < rows.length; i++) {
			String productId = (String) table.getValueAt(rows[i]-i, 0);
			quantity.remove(productId);
			products.remove(productId);
			((DefaultTableModel)table.getModel()).removeRow(rows[i]-i);
		}
		/*totalLable.setText(String.valueOf(getTotal()));*/
		totalField.setText(String.valueOf(getTotal()));
	}
	
	/**
	 * 修改商品数量
	 * @param index
	 */
	public void updateQuantity(int row) {
		if(table.getValueAt(row, 4) == null || table.getValueAt(row, 4).equals("")) return ;
		int value = Integer.parseInt(String.valueOf(table.getValueAt(row, 4)));
		String productId = (String) table.getValueAt(row, 0);
		if(quantity.get(productId) != value) {
			quantity.put(productId, value);
			double price = (Double) table.getValueAt(row, 3);
			table.setValueAt(value * price, row, 6);
			/*totalLable.setText(String.valueOf(getTotal()));*/
			totalField.setText(String.valueOf(getTotal()));
		}
	}
	
	/**
	 * 获取商品总价格
	 * @return
	 */
	public double getTotal() {
		TableModel model = table.getModel();
		double total = 0;
		for(int i = 0; i < model.getRowCount(); i++) {
			double subTotal = (Double) table.getValueAt(i, 6);
			total += subTotal;
		}
		return total;
	}
	
	public void fetchQuantity(String qrcodeContent) {
		if(qrcodeContent == null)
			return ;
		String[] productIds = qrcodeContent.split("-"); 
		for(int i = 0; i < productIds.length; i++) {
			if(quantity.get(productIds[i]) != null) {
				int value = quantity.get(productIds[i]);
				quantity.put(productIds[i], value+1);
			}
			else quantity.put(productIds[i], 1);
		}
	}
	
	public void fetchProducts(String qrcodeContent) {
		 Set<String> keys = quantity.keySet();
		 Object[] productIds =  keys.toArray(); 
		 products = catalog.getProduct(productIds);
	}
	
	public void displayProducts(String qrcodeContent) {
		fetchQuantity(qrcodeContent);
		fetchProducts(qrcodeContent);
		if(products == null)
			return ;
		Set<String> keys = products.keySet();
		Iterator<String> it = keys.iterator();
		int rowCount = keys.size();
		((DefaultTableModel)table.getModel()).setRowCount(rowCount);
		int i = 0;
		while(it.hasNext()) {
			String productId = it.next();
			Product product = products.get(productId);
			table.setValueAt(product.getProductId(), i, 0);
			table.setValueAt(product.getProductName(), i, 1);
			table.setValueAt(product.getProductType(), i, 2);
			table.setValueAt(product.getPrice(), i, 3);
			table.setValueAt(quantity.get(productId), i, 4);
			table.setValueAt(product.getProductUnit(), i, 5);
			table.setValueAt(product.getPrice()*quantity.get(productId), i, 6);
			i++;
		}
		/*totalLable.setText(String.valueOf(getTotal()));*/
		totalField.setText(String.valueOf(getTotal()));
	}
	
	public void dispose() {
		if(clientThread != null && clientThread.isAlive())
			clientThread.stop();
		if(device != null)
			device.stop();
		super.dispose();
	}
	
	public JButton getScanButton() {
		return scanButton;
	}
	
	private class ScanThread implements Runnable{
		@Override
		public void run() {
			IoDevice io = new IoDevice();
			String content = io.scanQrcode();
			if(content == null)
				JOptionPane.showMessageDialog(null, "扫描失败");
			displayProducts(content);
			io.stop();
		}
		
	}
	
	public void startPushClient() {
		pushClient = new Client();
		pushClient.setClientId(this.clientId);
		pushClient.setServerPost(5000);
		pushClient.setServerUrl("");
		clientThread = new Thread(pushClient);
		clientThread.start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProductsTable table = new ProductsTable();
		table.setVisible(true);
	}
	
}
