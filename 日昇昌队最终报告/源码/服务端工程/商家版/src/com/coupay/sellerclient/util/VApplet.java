package com.coupay.sellerclient.util;

import java.awt.BorderLayout;  
import java.awt.Component;  
import java.awt.Container;  
import java.awt.FileDialog;  
 
import java.awt.Frame;  

import java.awt.Image;   
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;   
import java.awt.image.RenderedImage;  
  
import java.io.File;  
import java.io.IOException;  
import java.util.Vector;  
  
import javax.imageio.ImageIO;  
import javax.media.Buffer;  
import javax.media.CaptureDeviceInfo;  
import javax.media.CaptureDeviceManager;  
import javax.media.DataSink;  
import javax.media.Format;  
import javax.media.IncompatibleSourceException;  
import javax.media.Manager;  
import javax.media.MediaLocator;  
import javax.media.NoProcessorException;  
import javax.media.Player;  
import javax.media.Processor;    
import javax.media.control.FrameGrabbingControl;  
import javax.media.datasink.DataSinkListener;   
import javax.media.format.VideoFormat;  
import javax.media.protocol.DataSource;  
import javax.media.protocol.FileTypeDescriptor;  
import javax.media.protocol.SourceCloneable;  
import javax.media.util.BufferToImage;  
import javax.swing.JApplet;  
import javax.swing.JButton;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import java.awt.*;  

public class VApplet extends JApplet implements ActionListener{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane= null;  
    private JButton jbok,jbcancel,tuichuluxiang ;     
    private MediaLocator ml = null;  
    private Player player = null;          
    private DataSource datasource=null;        
    private Buffer buffer=null;  
    private BufferToImage bufferToImage=null;  
    private Image image=null;  
    private Processor processor=null;     
    //��ԭʼ����Դת��ɵģ����Ա���¡������Դ   
    private DataSource cloneabledatasource = null;    
    //�ɿ��Կ�¡������Դcloneabledatasource��¡������cloneddatasource   
    private DataSource cloneddatasource = null;   
    //����¼�����ݵ����ݳ�   
    private DataSink dataSink = null;  
    private StateHelper sh=null;  
  
    //���캯��  
    public VApplet() {  
    super();  
    }  
      
    public void init() {          
        jbok=new JButton("��   ��");  
        jbok.addActionListener(this);         
        jbcancel=new JButton("��ʼ¼��");  
        jbcancel.addActionListener(this);         
        tuichuluxiang=new JButton("ֹͣ¼��");  
        tuichuluxiang.addActionListener(this);  
        this.setSize(400, 320); //(650, 550)  
          
        jContentPane=getJContentPane();  
        jbok.setVisible(true);  
        jbcancel.setVisible(true);  
        tuichuluxiang.setVisible(true);  
          
        Container cp=getContentPane();  
        cp.add(jContentPane,BorderLayout.NORTH);  
        cp.add(jbok,BorderLayout.WEST);  
        cp.add(jbcancel,BorderLayout.CENTER);  
        cp.add(tuichuluxiang,BorderLayout.EAST);  
          
        this.setContentPane(cp);  
        this.setName("VApplet");  
    }  
      
    //ȡϵͳ���пɲɼ���Ӳ���豸�б�  
    private CaptureDeviceInfo[] getDevices() {  
        Vector<?> devices = CaptureDeviceManager.getDeviceList(null);  
        CaptureDeviceInfo[] info = new CaptureDeviceInfo[devices.size()];  
        for (int i = 0; i < devices.size(); i++) {  
            info[i]= (CaptureDeviceInfo) devices.get(i);  
        }  
        return info;  
    }  
      
    //����֪�豸��ȡ������Ƶ�豸���б�  
    private CaptureDeviceInfo[] getVideoDevices() {  
        CaptureDeviceInfo[] info = getDevices();  
        CaptureDeviceInfo[] videoDevInfo;  
        Vector<CaptureDeviceInfo> vc = new Vector<CaptureDeviceInfo>();  
        for (int i = 0; i < info.length; i++) {  
            //ȡ�豸֧�ֵĸ�ʽ�������һ������Ƶ��ʽ������Ϊ���豸Ϊ��Ƶ�豸  
            Format[] fmt = info[i].getFormats();  
            for (int j = 0; j < fmt.length; j++) {  
                if (fmt[j] instanceof VideoFormat) {  
                    vc.add(info[i]);  
                }  
                break;  
            }  
        }  
        videoDevInfo = new CaptureDeviceInfo[vc.size()];  
        for (int i = 0; i < vc.size(); i++) {  
            videoDevInfo[i]= (CaptureDeviceInfo) vc.get(i);  
        }  
        return videoDevInfo;  
    }   
      
private JPanel getJContentPane() {  
    if (jContentPane == null) {  
        BorderLayout borderLayout = new BorderLayout();  
        jContentPane = new JPanel();  
        jContentPane.setLayout(borderLayout);  
      
        try {  
          
            //������ֻ��һ����Ƶ�豸��ֱ��ȡ��һ��  
        // ȡ�õ�ǰ�豸��MediaLocator  
        ml = getVideoDevices()[0].getLocator();////����֪�豸��ȡ������Ƶ�豸���б�  
          
        datasource = Manager.createDataSource(ml);   
        cloneabledatasource = Manager.createCloneableDataSource(datasource);  
        cloneddatasource = ((SourceCloneable) cloneabledatasource).createClone();   
          
        //���Ѿ�ȡ�õ�MediaLocator�õ�һ��Player  
        player = Manager.createRealizedPlayer(cloneabledatasource);   
        player.start();  
          
        //ȡ��Player��AWT Component   
        Component comp = player.getVisualComponent();   
        //�������Ƶ�豸�������������null,����Ҫ���ж�һ��  
        if (comp != null) {  
            //��Component�ӵ�����   
            jContentPane.add(comp,BorderLayout.NORTH);   
         }  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
      }  
      return jContentPane;  
    }  
  
       
public void actionPerformed(ActionEvent e) {  
    //����ͼƬ     
    if(e.getSource()==jbok){//����  
        Component comp = player.getVisualComponent();   
        comp.getGraphics();  
        FrameGrabbingControl fgc=(FrameGrabbingControl)player.getControl("javax.media.control.FrameGrabbingControl");  
        buffer=fgc.grabFrame();  
              
        bufferToImage=new BufferToImage((VideoFormat)buffer.getFormat());  
        image=bufferToImage.createImage(buffer);  
        if(image==null||image.equals("null")||image.equals(null)){  
            JOptionPane.showMessageDialog(null, "���������ٱ���");   
        }else{  
         try {  
            ImageIO.write((RenderedImage)image, "GIF", new File("D:\\2.jpg"));  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
            }  
        }              
        }else if(e.getSource()==jbcancel&&processor!=null){//¼��  
            JOptionPane.showMessageDialog(null, "����¼��");   
        }else if(e.getSource()==jbcancel&&processor==null){//¼��  
            Frame saveluxiangframe = new Frame("������Ƶ�ļ�");   
            FileDialog fshipin = new FileDialog(saveluxiangframe, "������Ƶ�ļ�",   
            FileDialog.SAVE);   
            fshipin.setFile("w.QUICKTIME");   
            fshipin.setVisible(true);   
            String luxiangsavepath = fshipin.getDirectory();   
            String luxiangsavename = fshipin.getFile();   
//           ��������˱������֣�û�е�ȡ�����Ļ����ſ�����   
            if (luxiangsavename != null) {   
//           �����·������\\��ֻ��/   
            luxiangsavepath.replace("\\", "/");   
//           ¼��Դ����   
            try {// player��cloneabledatasource����Դ��processor��cloneddatasource������Դ   
            processor = Manager.createProcessor(cloneddatasource);   
            sh = new StateHelper(processor);   
            } catch (IOException ez5) {   
            ez5.printStackTrace();   
            System.exit(-1);   
            } catch (NoProcessorException ez6) {   
            ez6.printStackTrace();   
            System.exit(-1);   
            }   
  
//           Configure the processor,��processor����configured״̬   
            if (!sh.configure(10000)) {   
            System.out.println("configure wrong!");   
            System.exit(-1);   
            }   
  
            /*  
            * if ( processor instanceof Processor) processor.configure();  
            */   
  
//           ������Ƶ�����ʽ   
            processor.setContentDescriptor(new FileTypeDescriptor(   
            FileTypeDescriptor.QUICKTIME));   
  
//           realize the processor����processor����realized״̬   
            if (!sh.realize(10000)) {   
            System.out.println("realize wrong!");   
            System.exit(-1);   
            }   
  
//           get the output of the processor����������processor   
            DataSource outsource = processor.getDataOutput();   
            MediaLocator dest = new MediaLocator(new java.lang.String(   
            "file:///" + luxiangsavepath + luxiangsavename));   
  
  
            processor.start();   
            try{  
            ////////////////////////////////////  
                dataSink = Manager.createDataSink(outsource, dest);                   
                dataSink.open();                  
                dataSink.start();   
              }catch(Exception ez1) {   
                ez1.printStackTrace();   
                System.exit(-1);   
              }   
            }  
        }else if(e.getSource()==tuichuluxiang && processor == null){//�˳�¼��  
            JOptionPane.showMessageDialog(null, "���ȵ���¼�����ֹͣ��");   
        }else if(e.getSource()==tuichuluxiang && processor!= null){//�˳�¼��  
            /*  
            * ���Ҫ���ܹ�����¼�񣬹ؼ��������㣺 1 ����������cloneddatasource ��  
            * ����Ϊ��cloneddatasource�����ú� �� cloneddatasource���ı��� 2�����processor  
            */   
            processor.close();   
            processor.deallocate();   
            dataSink.close();   
            cloneddatasource = (DataSource)((SourceCloneable) cloneabledatasource).createClone();   
            processor = null;   
        }  
     }  

//����ͼƬ�Ŀ�Ⱥ͸߶�   
private int imgWidth = 400;  
private int imgHeight = 300;   
////////////////////////ͼ���������///////////////////////////////////////////////////  
class ImagePanel extends Panel {   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image ImagePanelimg = null;   

	public ImagePanel() {   
		setLayout(null);   
		setSize(imgWidth, imgHeight);   
	}   

	public void setImage(Image img) {   
		this.ImagePanelimg = img;   
		this.setVisible(true);   
		repaint();   
	}   

	public void update(Graphics graphics) {   
		if (ImagePanelimg != null) {   
			graphics.drawImage(ImagePanelimg, 0, 0, this);   
		}    
	}   
}  
//////////////////////dataSink����//////////////////////////////////////////////////    
class dataSinkS implements DataSink{  
	public void addDataSinkListener(DataSinkListener arg0) {  
		// TODO Auto-generated method stub            
	}  

	public void close() {  
		try {  
			super.clone();  
		} catch (CloneNotSupportedException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}  
	}  

	public String getContentType() {  
		// TODO Auto-generated method stub  
		return null;  
	}  

	public MediaLocator getOutputLocator() {  
		// TODO Auto-generated method stub  
		return null;  
	}  

	public void open() throws IOException, SecurityException {  
		// TODO Auto-generated method stub            
	}  

	public void removeDataSinkListener(DataSinkListener arg0) {  
		// TODO Auto-generated method stub            
	}  

	public void setOutputLocator(MediaLocator arg0) {  
		// TODO Auto-generated method stub            
	}  

	public void start() throws IOException {  
		// TODO Auto-generated method stub            
	}  

	public void stop() throws IOException {  
		// TODO Auto-generated method stub            
	}  

	public void setSource(DataSource arg0) throws IOException, IncompatibleSourceException {  
		// TODO Auto-generated method stub            
	}  

	public Object getControl(String arg0) {  
		// TODO Auto-generated method stub  
		return null;  
	}  

	public Object[] getControls() {  
		// TODO Auto-generated method stub  
		return null;  
	}         
}  
 
}
  