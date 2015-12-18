package com.coupay.sellerclient.util;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.luo.zxingutil.ZxingDecoderHandler;
import com.luo.zxingutil.ZxingEncoderHandler;
import com.pengo.capture.CameraDialog;
import com.pengo.capture.CameraFrame;

public class IoDevice {
	CameraDialog dialog;
	
	public IoDevice() {
		dialog = new CameraDialog();
	}
	
	public String scanQrcode() {
		
		try {		
			String content = dialog.getEan13CodeContent(10 * 1000);
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage generateQrcode(String content) {
		return ZxingEncoderHandler.encode(content, 150, 150);
	}
	
	public void generateAndDisplayQrcode(String content) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BufferedImage image = generateQrcode(content);
		JLabel lable = new JLabel(new ImageIcon(image));
		frame.add(lable);
		frame.setVisible(true);
		frame.setSize(200,200);
		frame.setLocation(400, 240);
	}
	
	public void generateQrcode(String content,String path) {
		ZxingEncoderHandler encoder = new ZxingEncoderHandler();
		encoder.encode(content, 150, 150, path);
	}
	
	public String decodeQrcode(String path) {
		ZxingDecoderHandler decoder = new ZxingDecoderHandler();
		return decoder.decode(path);
	}
	
	public void stop() {
		dialog.dispose();
	}
	
	public static void main(String[] args) {
		IoDevice device = new IoDevice();
		System.out.println(device.scanQrcode());
		device.stop();
	}
}
