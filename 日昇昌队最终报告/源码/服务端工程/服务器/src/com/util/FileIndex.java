package com.util;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
/**
* 
* 此类完成指定文件的遍历。
* File[] files--查询范围，默认系统可用根目录
* String fileName--查询文件名字
* List<File> resultFile--存放遍历结果
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
//字符串比较均不区分大小写。
//比较类型全部或部分文件名：比较方式，文件名是否以指定字符串开始
public static final int EQUALS_STARR=1;
//比较类型：文件名含有指定字符串
public static final int EQUALS_LIKE=2;
//精确比较
public static final int EQUALS_ALL=3;
//查询范围，默认为系统可用根目录
public   File[] files=File.listRoots();

//查询文件名字
private String fileName;

//存放文件结果数组
private List<File> resultFile=new LinkedList<File>();
  
private int eqType;
//参数构造
public FileIndex(File[] files,String fileName,int eqType)
{
   this.files=files;
   this.fileName=fileName;
   this.eqType=eqType;
}

public boolean Equals(String str,String childStr)
{
   //将比较字符串转为小写形式
   String strLowerCase=str.toLowerCase();
   String childStrLowerCase=childStr.toLowerCase();
   //指定比较类型
   switch(eqType)
   {
    case 1: return strLowerCase.startsWith(childStrLowerCase);
   
    case 2: if(strLowerCase.indexOf(childStrLowerCase)!=-1)return true;else return false;
   
    case 3: return strLowerCase.equals(childStrLowerCase);
   
    default:return false;
   }
}

//遍历搜索范围文件数组。
private void getFiles()throws RuntimeException,IOException
{
   for(int i=0;i<files.length;i++)
   {
    //调用遍历方法完成遍历
    getFile(files[i]);
   }
}

//递归遍历文件树，搜索指定文件,并回避了RuntimeException，IOException
private void getFile(File files)throws RuntimeException,IOException
{
   //返回files目录下所有文件对象
   File[] filesArray=files.listFiles();
   //如果文件目录下不为空或者不产生IO异常，开始遍历文件下目录
   if(filesArray!=null&&filesArray.length!=0)
   {
    //遍历开始
    for(int i=0;i<filesArray.length;i++)
    {
     File f=filesArray[i];
     //如果File对象为目录
     if( f.isDirectory())
     {
      //测试输出
      System.out.println("Directory Path="+f.getPath());
      //继续递归遍历
      getFile(f);
     }else if(f.isFile())//如果File对象为文件
     {
      //取出文件名字
      String filename=f.getName();
      //测试输出
      System.out.println("File Path="+f.getPath());
      //文件名比较，如果相同将文件对象放入LinkedList表中
      boolean falg=Equals(filename,fileName);
      if(falg)
      {
       //放入LinkedList列表
       resultFile.add(f);
      }
     }else
     {
      throw new RuntimeException("文件类型错误！");
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
