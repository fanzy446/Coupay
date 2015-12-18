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
	 * 获取Bitmap
	 * 
	 * @param uri
	 *            图片地址
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
	 * 获取Drawable
	 * 
	 * @param uri
	 *            图片地址
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
	 * 异步将imageUrl中的图片载入到imageView中
	 * 
	 * @param imageUrl
	 * @param imageView
	 * @return
	 */
	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView) {
		FileUtil fileUtil = new FileUtil(MainService.getUser().getName());
		if (imagesCache.containsKey(imageUrl)) {
			// 从缓存中获取
			SoftReference<Drawable> softReference = imagesCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (null != drawable) {
				return drawable;
			}
		}
		if (fileUtil.isFileExists("cache", imageUrl)) {
			// 从sd卡中获取
			return fileUtil.readImageFromSD("cache", imageUrl);
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageView.setImageDrawable((Drawable) message.obj);
			}
		};
		// 建立一个新的线程下载图片
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
	 * 将drawable转换成字节码
	 * 
	 * @param drawable
	 *            图片
	 * @return 字节码
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
			// 创建一个字节数组输出流,流的大小为size
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// 将字节数组输出流转化为字节数组byte[]
			byte[] imagedata = baos.toByteArray();
			return imagedata;
		}
		return null;
	}

	/**
	 * 将字节码转换成drawable
	 * 
	 * @param img
	 *            图片字节码
	 * @return 返回图片drawable
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
