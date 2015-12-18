package com.util;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
/**
* 
* �������ָ���ļ��ı�����
* File[] files--��ѯ��Χ��Ĭ��ϵͳ���ø�Ŀ¼
* String fileName--��ѯ�ļ�����
* List<File> resultFile--��ű������
*
*/
public class FileIndex {
	public static void main(String[] args){
//		File[] files = null;
//		String newFilename = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ssh\\"+"1"+".jpg";
//		FileIndex i = new FileIndex(files, newFilename, 0);
//		System.out.println(files);
//		String newFilename = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ssh\\"+"name"+".png";
//        FileImageOutputStream imgout = new FileImageOutputStream(new File(newFilename));
//        byte[] photo = 
//		imgout.write(photo,0,photo.length);
//		imgout.close();
	}
//�ַ����ȽϾ������ִ�Сд��
//�Ƚ�����ȫ���򲿷��ļ������ȽϷ�ʽ���ļ����Ƿ���ָ���ַ�����ʼ
public static final int EQUALS_STARR=1;
//�Ƚ����ͣ��ļ�������ָ���ַ���
public static final int EQUALS_LIKE=2;
//��ȷ�Ƚ�
public static final int EQUALS_ALL=3;
//��ѯ��Χ��Ĭ��Ϊϵͳ���ø�Ŀ¼
public   File[] files=File.listRoots();

//��ѯ�ļ�����
private String fileName;

//����ļ��������
private List<File> resultFile=new LinkedList<File>();
  
private int eqType;
//��������
public FileIndex(File[] files,String fileName,int eqType)
{
   this.files=files;
   this.fileName=fileName;
   this.eqType=eqType;
}

public boolean Equals(String str,String childStr)
{
   //���Ƚ��ַ���תΪСд��ʽ
   String strLowerCase=str.toLowerCase();
   String childStrLowerCase=childStr.toLowerCase();
   //ָ���Ƚ�����
   switch(eqType)
   {
    case 1: return strLowerCase.startsWith(childStrLowerCase);
   
    case 2: if(strLowerCase.indexOf(childStrLowerCase)!=-1)return true;else return false;
   
    case 3: return strLowerCase.equals(childStrLowerCase);
   
    default:return false;
   }
}

//����������Χ�ļ����顣
private void getFiles()throws RuntimeException,IOException
{
   for(int i=0;i<files.length;i++)
   {
    //���ñ���������ɱ���
    getFile(files[i]);
   }
}

//�ݹ�����ļ���������ָ���ļ�,���ر���RuntimeException��IOException
private void getFile(File files)throws RuntimeException,IOException
{
   //����filesĿ¼�������ļ�����
   File[] filesArray=files.listFiles();
   //����ļ�Ŀ¼�²�Ϊ�ջ��߲�����IO�쳣����ʼ�����ļ���Ŀ¼
   if(filesArray!=null&&filesArray.length!=0)
   {
    //������ʼ
    for(int i=0;i<filesArray.length;i++)
    {
     File f=filesArray[i];
     //���File����ΪĿ¼
     if( f.isDirectory())
     {
      //�������
      System.out.println("Directory Path="+f.getPath());
      //�����ݹ����
      getFile(f);
     }else if(f.isFile())//���File����Ϊ�ļ�
     {
      //ȡ���ļ�����
      String filename=f.getName();
      //�������
      System.out.println("File Path="+f.getPath());
      //�ļ����Ƚϣ������ͬ���ļ��������LinkedList����
      boolean falg=Equals(filename,fileName);
      if(falg)
      {
       //����LinkedList�б�
       resultFile.add(f);
      }
     }else
     {
      throw new RuntimeException("�ļ����ʹ���");
     }
    } 
   }
}
public String getFileName() {
   return fileName;
}
public void setFileName(String fileName) {
   this.fileName = fileName;
}
public void setFiles(File[] files) {
   this.files = files;
}
public void setResultFile(List<File> resultFile) {
   this.resultFile = resultFile;
}
public List<File> getResultFile() {
   try
   {
    getFiles();
    return resultFile;
   }
   catch(IOException ex)
   {
    return null;
   }
  
}
}
