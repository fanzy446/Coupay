package com.billme.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.futurePayment.http.MyHttpClient;

public class FileUtil {
	private MyHttpClient client = null;
	private String ROOTPATH;
	private String APP_NAME = "BillMe";
	private String USER_NAME = null;

	public FileUtil(String name) {
		USER_NAME = name;
		ROOTPATH = Environment.getExternalStorageDirectory() + File.separator
				+ APP_NAME + File.separator + USER_NAME + File.separator;
		client = new MyHttpClient(name);
		createSDRootDirectory();
		// ����һϵ���ļ���
		createSDDirectory("cache");
	}

	/**
	 * ����sd���Ƿ����
	 * 
	 * @return ���
	 */
	public boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	/**
	 * �õ��ļ���С
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @return �ļ���С
	 */
	public int getFileSize(String path) {
		FileInputStream fis = null;
		File file = new File(ROOTPATH + path);
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				return fis.available();
			} else
				return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * �õ��ļ���С
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @return �ļ���С
	 */
	public int getFileSize(String dir, String name) {
		FileInputStream fis = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				return fis.available();
			} else
				return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * �����ļ�
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @return �������ļ�������������null
	 * @throws IOException
	 */
	public File createSDFile(String path) {
		File file = null;
		try {
			file = new File(path);
			file.createNewFile();
			if (!file.isFile()) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * �����ļ�
	 * 
	 * @param dir
	 *            Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @return �������ļ�������������null
	 * @throws IOException
	 */
	public File createSDFile(String dir, String name) {
		File file = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		try {
			file = new File(ROOTPATH + dir, n);
			file.createNewFile();
			if (!file.isFile()) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * ������Ŀ¼
	 * 
	 * @param path
	 *            Ŀ¼·��
	 * @return ������Ŀ¼,����������null
	 */
	public File createSDRootDirectory() {
		File dir = new File(ROOTPATH);
		dir.mkdirs();
		if (!dir.isDirectory()) {
			return null;
		}
		return dir;
	}

	/**
	 * ����Ŀ¼
	 * 
	 * @param path
	 *            Ŀ¼·��
	 * @return ������Ŀ¼,����������null
	 */
	public File createSDDirectory(String path) {
		File dir = new File(ROOTPATH + path);
		dir.mkdirs();
		if (!dir.isDirectory()) {
			return null;
		}
		return dir;
	}

	/**
	 * ����ļ��Ƿ����
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @return �Ƿ�����ļ�
	 */
	public boolean isFileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * ����ļ��Ƿ����
	 * 
	 * @param dir
	 *            Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @return �Ƿ�����ļ�
	 */
	public boolean isFileExists(String dir, String name) {
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		return file.exists();
	}

	/**
	 * ����ļ��������ļ��Ƿ�Ϊͬһ�ļ���ͨ���Ƚϴ�Сȷ��
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @param size
	 *            �����ļ���С
	 * @return �Ƿ�Ϊͬһ�ļ�
	 */
	public boolean isSameFile(String path, int size) {
		if (getFileSize(path) == size) {
			return true;
		}
		return false;
	}

	/**
	 * ����ļ��������ļ��Ƿ�Ϊͬһ�ļ���ͨ���Ƚϴ�Сȷ��
	 * 
	 * @param dir
	 *            Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @param size
	 *            �����ļ���С
	 * @return �Ƿ�Ϊͬһ�ļ�
	 */
	public boolean isSameFile(String dir, String name, int size) {
		String n = name.replaceAll("/", "").replaceAll(":", "");
		if (getFileSize(dir, n) == size) {
			return true;
		}
		return false;
	}

	/**
	 * ͨ��inputStreamд�����ݵ�sd��
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼·��
	 * @param fileName
	 *            �ļ���
	 * @param input
	 *            ������
	 * @param cover
	 *            ���ļ����ڣ��Ƿ񸲸�
	 * @return ���ɵ��ļ�
	 */
	public File writeToSDFromInputStream(String dir, String fileName,
			InputStream input, boolean cover) {
		FileOutputStream output = null;
		String fn = fileName.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, fileName);
		try {
			createSDDirectory(dir);
			if (isFileExists(dir, fn) && cover == false) {
				output = new FileOutputStream(file, true);
			} else {
				file = createSDFile(dir, fn);
				output = new FileOutputStream(file);

			}
			byte buffer[] = new byte[1024];
			while (input.read(buffer) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * ͨ��inputStreamд�����ݵ�sd��
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼·��
	 * @param fileName
	 *            �ļ���
	 * @param input
	 *            ������
	 * @param outputSize
	 *            ���뻺���С
	 * @param inputSize
	 *            д�뻺���С
	 * @param cover
	 *            ���ļ����ڣ��Ƿ񸲸�
	 * @return ���ɵ��ļ�
	 */
	public File writeToSDFromInputStream(String dir, String fileName,
			InputStream input, int outputSize, int inputSize, boolean cover) {
		FileOutputStream output = null;
		String fn = fileName.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, fileName);
		try {
			createSDDirectory(dir);
			if (isFileExists(dir, fn) && cover == false) {
				output = new FileOutputStream(file, true);
			} else {
				file = createSDFile(dir, fn);
				output = new FileOutputStream(file);

			}
			byte inputBuffer[] = new byte[inputSize];
			byte outputBuffer[] = new byte[outputSize];
			int j = 0;
			while (input.read(inputBuffer) != -1) {
				for (int i = 0; i < inputSize; i++) {
					outputBuffer[j] = inputBuffer[i];
					if (j != outputSize - 1) {
						j++;
					} else {
						output.write(outputBuffer);
						output.flush();
						outputBuffer = new byte[outputSize];
						j = 0;
					}
				}
			}
			if (j > 0) {
				output.write(outputBuffer, 0, j - 1);
				output.flush();
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * ��SD����ȡ�ļ�
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @return
	 */
	public String readFromSD(String path) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		File file = new File(ROOTPATH + path);
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * ��SD����ȡ�ļ�
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @return
	 */
	public String readFromSD(String dir, String name) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * ��ȡsd�����ͼƬ
	 * 
	 * @param path
	 *            �ļ�����·��
	 * @return ͼƬdrawable
	 */
	public Drawable readImageFromSD(String path) {
		Drawable image = null;
		FileInputStream inputStream = null;
		File file = new File(ROOTPATH + path);
		if (isSDCardAvailable()) {
			try {
				inputStream = new FileInputStream(file);
				image = Drawable.createFromStream(inputStream, file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

	/**
	 * ��ȡsd�����ͼƬ
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼·��
	 * @param name
	 *            �ļ���
	 * @return ͼƬdrawable
	 */
	public Drawable readImageFromSD(String dir, String name) {
		Drawable image = null;
		FileInputStream inputStream = null;
		String n = name.replaceAll("/", "").replaceAll(":", "");
		File file = new File(ROOTPATH + dir, n);
		if (isSDCardAvailable()) {
			try {
				inputStream = new FileInputStream(file);
				image = Drawable.createFromStream(inputStream, file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return image;
	}

	/**
	 * 
	 * @param urlStr
	 *            �ļ�url��ַ
	 * @param dir
	 *            �洢��sdĿ¼·��
	 * @param fileName
	 *            �洢���ļ���
	 * @param cover
	 *            ���ļ��Ѵ��ڣ��Ƿ񸲸�
	 * @return �����Ƿ�ɹ�
	 */
	public boolean downloadFile(String urlStr, String dir, String fileName,
			boolean cover) {
		InputStream inputStream = null;
		String n = fileName.replaceAll("/", "").replaceAll(":", "");
		try {
			inputStream = client.getInputStreamFromUrl(urlStr);
			File file = writeToSDFromInputStream(dir, n, inputStream, cover);
			if (file == null) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * ��ʵ��ת���ɴ洢��ַ
	 * 
	 * @param urlStr
	 * @return
	 */
	public String modelToAddress(Object model) {
		String name = model.getClass().getName();
		return ROOTPATH + name;
	}

}
