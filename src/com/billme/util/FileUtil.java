package com.billme.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class FileUtil
{
	private String ROOTPATH;
	private String APP_NAME = "BillMe";

	public FileUtil()
	{
		ROOTPATH = Environment.getExternalStorageDirectory() + File.separator;
		createSDDirectory(APP_NAME);
		ROOTPATH += APP_NAME + File.separator;
	}

	public boolean checkSDCardAvailable()
	{
		Log.i("error", Environment.getExternalStorageState());
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
		{
			return true;
		}
		return false;
	}

	public File createSDFile(String fileName) throws IOException
	{
		File file = new File(ROOTPATH + fileName);
		file.createNewFile();
		return file;
	}

	public File createSDDirectory(String dirName)
	{
		File dir = new File(ROOTPATH + dirName);
		dir.mkdir();
		return dir;
	}

	public boolean checkFileExists(String fileName)
	{
		File file = new File(ROOTPATH + fileName);
		return file.exists();
	}

	public File writeToSDFromInputStream(String path, String fileName,
			InputStream input)
	{
		String dir = ROOTPATH + path;
		File file = null;
		FileOutputStream output = null;
		try
		{
			createSDDirectory(dir);
			file = createSDFile(dir + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while (input.read(buffer) != -1)
			{
				output.write(buffer);
			}
			output.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				output.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return file;
	}

	// ∂¡»°sdø®Õº∆¨
	public Drawable readImageFromSD(String path, String name)
	{
		Drawable image = null;
		FileInputStream inputStream = null;
		File file = new File(ROOTPATH + path, name);
		if (checkSDCardAvailable())
		{
			try
			{
				inputStream = new FileInputStream(file);
				image = Drawable.createFromStream(inputStream, file.getName());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (inputStream != null)
				{
					try
					{
						inputStream.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

}
