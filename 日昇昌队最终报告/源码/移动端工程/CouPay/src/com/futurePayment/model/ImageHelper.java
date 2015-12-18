package com.futurePayment.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.billme.logic.MainService;
import com.billme.util.FileUtil;
import com.futurePayment.http.MyHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageHelper {
	private static ImageHelper imageHelper = null;
	private MyHttpClient client = null;
	private HashMap<String, SoftReference<Drawable>> imagesCache;

	private ImageHelper(String name) {
		client = new MyHttpClient(name);
		imagesCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public static ImageHelper getInstance() {
		if (imageHelper != null)
			return imageHelper;
		return new ImageHelper(null);
	}

	/**
	 * ��ȡBitmap
	 * 
	 * @param uri
	 *            ͼƬ��ַ
	 * @return
	 */
	public Bitmap GetBitmapByUrl(String uri) {
		Bitmap bitmap;
		InputStream is;
		try {
			is = client.getInputStreamFromUrl(uri);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return bitmap;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡDrawable
	 * 
	 * @param uri
	 *            ͼƬ��ַ
	 * @return
	 */
	public Drawable GetDrawableByUrl(String uri) {
		FileUtil fileUtil = new FileUtil(MainService.getUser().getName());
		Drawable drawable;
		InputStream is;
		try {
			is = client.getInputStreamFromUrl(uri);
			// fileUtil.writeToSDFromInputStream("cache", uri, is, false);
			fileUtil.writeToSDFromInputStream("cache", uri, is, 1024, 1, false);
			drawable = Drawable.createFromStream(is, "src");
			is.close();
			return drawable;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �첽��imageUrl�е�ͼƬ���뵽imageView��
	 * 
	 * @param imageUrl
	 * @param imageView
	 * @return
	 */
	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView) {
		FileUtil fileUtil = new FileUtil(MainService.getUser().getName());
		if (imagesCache.containsKey(imageUrl)) {
			// �ӻ����л�ȡ
			SoftReference<Drawable> softReference = imagesCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (null != drawable) {
				return drawable;
			}
		}
		if (fileUtil.isFileExists("cache", imageUrl)) {
			// ��sd���л�ȡ
			return fileUtil.readImageFromSD("cache", imageUrl);
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageView.setImageDrawable((Drawable) message.obj);
			}
		};
		// ����һ���µ��߳�����ͼƬ
		new Thread() {
			public void run() {
				Drawable drawable = getInstance().GetDrawableByUrl(imageUrl);
				imagesCache
						.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message msg = handler.obtainMessage(0, drawable);
				handler.sendMessage(msg);
			}
		}.start();

		return null;
	}

	/**
	 * ��drawableת�����ֽ���
	 * 
	 * @param drawable
	 *            ͼƬ
	 * @return �ֽ���
	 */
	public synchronized byte[] drawableToByte(Drawable drawable) {

		if (drawable != null) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			int size = bitmap.getWidth() * bitmap.getHeight() * 4;
			// ����һ���ֽ����������,���Ĵ�СΪsize
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			// ����λͼ��ѹ����ʽ������Ϊ100%���������ֽ������������
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// ���ֽ����������ת��Ϊ�ֽ�����byte[]
			byte[] imagedata = baos.toByteArray();
			return imagedata;
		}
		return null;
	}

	/**
	 * ���ֽ���ת����drawable
	 * 
	 * @param img
	 *            ͼƬ�ֽ���
	 * @return ����ͼƬdrawable
	 */
	public synchronized Drawable byteToDrawable(byte[] img) {
		Bitmap bitmap;
		if (img != null) {
			bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}
		return null;

	}

}
