package com.coupay.sellerclient.external;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.coupay.sellerclient.ui.ProductsTable;
import com.pengo.capture.CameraFrame;

public class ClientSystem {

	public ProductsTable table;
	CameraFrame camera;
	
	public ClientSystem() {
		table = new ProductsTable();
		try {
			camera = new CameraFrame();
		} catch(Exception e){
			e.printStackTrace();
		}
		addListenner();
		table.setVisible(true);
		camera.setVisible(false);
	}
	
	public void scan() {
		String content = camera.getQrcodeContent(1000 * 4);
		if(content == null) 
			JOptionPane.showMessageDialog(null, "…®√Ë ß∞‹");
		table.displayProducts(content);
	}
	
	public void addListenner() {
		table.getScanButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scan();
			}
		});
	}
	
	public static void main(String[] args) {
		ClientSystem sys = new ClientSystem();
	}

}
