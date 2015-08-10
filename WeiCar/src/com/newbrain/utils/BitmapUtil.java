package com.newbrain.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.util.Base64;



public class BitmapUtil {

	/**
	 * bitmap杞负base64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 灏哹ase64杞崲鎴恇itmap鍥剧墖
	 * 
	 * @param string
	 * @return bitmap
	 */

	public static Bitmap stringtoBitmap(String string) {

		// 灏嗗瓧绗︿覆杞崲鎴怋itmap绫诲瀷
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	public static Bitmap getImageThumbnail(String imagePath, int imageType,
			Context context) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 鑾峰彇杩欎釜鍥剧墖鐨勫鍜岄珮锛屾敞鎰忔澶勭殑bitmap涓簄ull
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		//

		options.inJustDecodeBounds = false; // 璁句负 false
		int beWidth = 0;
		int beHeight = 0;
		int h = options.outHeight;
		int w = options.outWidth;
		int be = 1;
		switch (imageType) {
		case 0:
			beWidth = w / 30;
			beHeight = h / 30;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 30, 30,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 1:
			beWidth = w / 40;
			beHeight = h / 40;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 40, 40,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 2:
			beWidth = w / 60;
			beHeight = h / 60;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;

			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 60, 60,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 3:
			beWidth = w / 80;
			beHeight = h / 80;

			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 80, 80,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 4:
			beWidth = w / 128;// CXJ*****
			beHeight = h / 128;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 100,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 5:
			beWidth = w / 225;
			beHeight = h / 300;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 241, 381,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		case 6:
			beWidth = w / 512;
			beHeight = h /512;
			if (beWidth < beHeight) {
				be = beWidth;
			} else {
				be = beHeight;
			}
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 閲嶆柊璇诲叆鍥剧墖锛岃鍙栫缉鏀惧悗鐨刡itmap锛屾敞鎰忚繖娆¤鎶妎ptions.inJustDecodeBounds 璁句负 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 鍒╃敤ThumbnailUtils鏉ュ垱寤虹缉鐣ュ浘锛岃繖閲岃鎸囧畾瑕佺缉鏀惧摢涓狟itmap瀵硅薄
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, w, h,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			break;
		default:
			break;

		}
//		if (bitmap == null) {
//			switch (imageType) {
//			case 0:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			case 1:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			case 2:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			case 3:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			case 4:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			case 5:
//				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_common_picture);
//				break;
//			default:
//				break;
//			}
//			return bitmap;
//		}
		
		
		
		
		// 璁＄畻缂╂斁姣�
		return bitmap;
	}

	// 鎶奲it杞崲鎴愭祦
	/*public static String createFile(Bitmap photo) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bs = stream.toByteArray(); // 灏嗗浘鐗囨祦浠ュ瓧绗︿覆褰㈠紡瀛樺偍涓嬫潵
		FileOutputStream out = null;
		String picPath = null;
		try {
			picPath = Constant.IAMGES_PATH
					+ "/"
					+ (new SimpleDateFormat("yyyyMMddHHmmss"))
							.format(new Date()) + ".png";
			File fi = new File(Constant.IAMGES_PATH);
			if (!fi.exists()) {
				fi.mkdirs();
			}
			out = new FileOutputStream(picPath);
			out.write(bs);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return picPath;
	}*/

	// 璁剧疆鍥剧墖鐨勫ぇ灏�
	public static Bitmap setBitmap(Bitmap bitMap, int width1, int heght2) {
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		// 璁剧疆鎯宠鐨勫ぇ灏�
		int newWidth = width1;
		int newHeight = heght2;
		// 璁＄畻缂╂斁姣斾緥
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 鍙栧緱鎯宠缂╂斁鐨刴atrix鍙傛暟
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 寰楀埌鏂扮殑鍥剧墖
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		return bitMap;
	}

	// 瀛樺偍杩汼D鍗�
	public static void saveFile(Bitmap bm, String fileName) throws Exception {
		File dirFile = new File(fileName);
		// 妫�娴嬪浘鐗囨槸鍚﹀瓨鍦�
		if (dirFile.exists()) {
			dirFile.delete(); // 鍒犻櫎鍘熷浘鐗�
		}
		File myCaptureFile = new File(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		// 100琛ㄧず涓嶈繘琛屽帇缂╋紝70琛ㄧず鍘嬬缉鐜囦负30%
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		bos.flush();
		bos.close();
	}

	// 鍘嬬缉鍥剧墖灏哄
	public static Bitmap compressBySize(String pathName, int targetWidth,
			int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 涓嶅幓鐪熺殑瑙ｆ瀽鍥剧墖锛屽彧鏄幏鍙栧浘鐗囩殑澶撮儴淇℃伅锛屽寘鍚楂樼瓑锛�
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
		// 寰楀埌鍥剧墖鐨勫搴︺�侀珮搴︼紱
		float imgWidth = opts.outWidth;
		float imgHeight = opts.outHeight;
		// 鍒嗗埆璁＄畻鍥剧墖瀹藉害銆侀珮搴︿笌鐩爣瀹藉害銆侀珮搴︾殑姣斾緥锛涘彇澶т簬绛変簬璇ユ瘮渚嬬殑鏈�灏忔暣鏁帮紱
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		opts.inSampleSize = 1;
		if (widthRatio > 1 || widthRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 璁剧疆濂界缉鏀炬瘮渚嬪悗锛屽姞杞藉浘鐗囪繘鍐呭锛�
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(pathName, opts);
		return bitmap;
	}

	private static final Size ZERO_SIZE = new Size(0, 0);
	private static final Options OPTIONS_GET_SIZE = new Options();
	private static final Options OPTIONS_DECODE = new Options();
	private static final byte[] LOCKED = new byte[0];

	private static final LinkedList<String> CACHE_ENTRIES = new LinkedList<String>(); // 姝ゅ璞＄敤鏉ヤ繚鎸丅itmap鐨勫洖鏀堕『搴�,淇濊瘉鏈�鍚庝娇鐢ㄧ殑鍥剧墖琚洖鏀�
	private static final Queue<QueueEntry> TASK_QUEUE = new LinkedList<QueueEntry>(); // 绾跨▼璇锋眰鍒涘缓鍥剧墖鐨勯槦鍒�
	private static final Set<String> TASK_QUEUE_INDEX = new HashSet<String>(); // 淇濆瓨闃熷垪涓鍦ㄥ鐞嗙殑鍥剧墖鐨刱ey,鏈夋晥闃叉閲嶅娣诲姞鍒拌姹傚垱寤洪槦鍒�

	private static final Map<String, Bitmap> IMG_CACHE_INDEX = new HashMap<String, Bitmap>(); // 缂撳瓨Bitmap
																								// 閫氳繃鍥剧墖璺緞,鍥剧墖澶у皬

	private static int CACHE_SIZE = 200; // 缂撳瓨鍥剧墖鏁伴噺

	static {
		OPTIONS_GET_SIZE.inJustDecodeBounds = true;
		// 鍒濆鍖栧垱寤哄浘鐗囩嚎绋�,骞剁瓑寰呭鐞�
		new Thread() {
			{
				setDaemon(true);
			}

			public void run() {
				while (true) {
					synchronized (TASK_QUEUE) {
						if (TASK_QUEUE.isEmpty()) {
							try {
								TASK_QUEUE.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					QueueEntry entry = TASK_QUEUE.poll();
					String key = createKey(entry.path, entry.width,
							entry.height);
					TASK_QUEUE_INDEX.remove(key);
					// createBitmap(entry.path, entry.width, entry.height);
					// 淇杩囩殑浠ｇ爜
					getBitmap(entry.path, entry.width, entry.height);
				}
			}
		}.start();

	}

	/**
	 * 鍒涘缓涓�寮犲浘鐗� 濡傛灉缂撳瓨涓凡缁忓瓨鍦�,鍒欒繑鍥炵紦瀛樹腑鐨勫浘,鍚﹀垯鍒涘缓涓�涓柊鐨勫璞�,骞跺姞鍏ョ紦瀛�
	 * 瀹藉害,楂樺害,涓轰簡缂╂斁鍘熷浘鍑忓皯鍐呭瓨鐨�,濡傛灉杈撳叆鐨勫,楂�,姣斿師鍥惧ぇ,杩斿洖鍘熷浘
	 * 
	 * @param path
	 *            鍥剧墖鐗╃悊璺緞 (蹇呴』鏄湰鍦拌矾寰�,涓嶈兘鏄綉缁滆矾寰�)
	 * @param width
	 *            闇�瑕佺殑瀹藉害
	 * @param height
	 *            闇�瑕佺殑楂樺害
	 * @return
	 */
	public static Bitmap getBitmap(String path, int width, int height) {
		Bitmap bitMap = null;
		try {
			if (CACHE_ENTRIES.size() >= CACHE_SIZE) {
				destoryLast();
			}
			bitMap = useBitmap(path, width, height);
			if (bitMap != null && !bitMap.isRecycled()) {
				return bitMap;
			}
			bitMap = createBitmap(path, width, height);
			String key = createKey(path, width, height);
			synchronized (LOCKED) {
				IMG_CACHE_INDEX.put(key, bitMap);
				CACHE_ENTRIES.addFirst(key);
			}
		} catch (OutOfMemoryError err) {
			destoryLast();
			System.out.println(CACHE_SIZE);
			// return createBitmap(path, width, height);
			// 淇杩囩殑浠ｇ爜
			return getBitmap(path, width, height);
		}
		return bitMap;
	}

	/**
	 * 璁剧疆缂撳瓨鍥剧墖鏁伴噺 濡傛灉杈撳叆璐熸暟,浼氫骇鐢熷紓甯�
	 * 
	 * @param size
	 */
	public static void setCacheSize(int size) {
		if (size <= 0) {
			throw new RuntimeException("size :" + size);
		}
		while (size < CACHE_ENTRIES.size()) {
			destoryLast();
		}
		CACHE_SIZE = size;
	}

	/**
	 * 鍔犲叆涓�涓浘鐗囧鐞嗚姹傚埌鍥剧墖鍒涘缓闃熷垪
	 * 
	 * @param path
	 *            鍥剧墖璺緞(鏈湴)
	 * @param width
	 *            鍥剧墖瀹藉害
	 * @param height
	 *            鍥剧墖楂樺害
	 */
	public static void addTask(String path, int width, int height) {
		QueueEntry entry = new QueueEntry();
		entry.path = path;
		entry.width = width;
		entry.height = height;
		synchronized (TASK_QUEUE) {
			String key = createKey(path, width, height);
			if (!TASK_QUEUE_INDEX.contains(key)
					&& !IMG_CACHE_INDEX.containsKey(key)) {
				TASK_QUEUE.add(entry);
				TASK_QUEUE_INDEX.add(key);
				TASK_QUEUE.notify();
			}
		}
	}

	/**
	 * 閫氳繃鍥剧墖璺緞杩斿洖鍥剧墖瀹為檯澶у皬
	 * 
	 * @param path
	 *            鍥剧墖鐗╃悊璺緞
	 * @return
	 */
	public static Size getBitMapSize(String path) {
		File file = new File(path);
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				BitmapFactory.decodeStream(in, null, OPTIONS_GET_SIZE);
				return new Size(OPTIONS_GET_SIZE.outWidth,
						OPTIONS_GET_SIZE.outHeight);
			} catch (FileNotFoundException e) {
				return ZERO_SIZE;
			} finally {
				closeInputStream(in);
			}
		}
		return ZERO_SIZE;
	}

	// ------------------------------------------------------------------
	// private Methods
	// 灏嗗浘鐗囧姞鍏ラ槦鍒楀ご
	private static Bitmap useBitmap(String path, int width, int height) {
		Bitmap bitMap = null;
		String key = createKey(path, width, height);
		synchronized (LOCKED) {
			bitMap = IMG_CACHE_INDEX.get(key);
			if (null != bitMap) {
				if (CACHE_ENTRIES.remove(key)) {
					CACHE_ENTRIES.addFirst(key);
				}
			}
		}
		return bitMap;
	}

	// 鍥炴敹鏈�鍚庝竴寮犲浘鐗�
	private static void destoryLast() {
		synchronized (LOCKED) {
			String key = CACHE_ENTRIES.removeLast();
			if (key.length() > 0) {
				Bitmap bitMap = IMG_CACHE_INDEX.remove(key);
				if (bitMap != null && !bitMap.isRecycled()) {
					bitMap.recycle();
					bitMap = null;
				}
			}
		}
	}

	// 鍒涘缓閿�
	private static String createKey(String path, int width, int height) {
		if (null == path || path.length() == 0) {
			return "";
		}
		return path + "_" + width + "_" + height;
	}

	// 閫氳繃鍥剧墖璺緞,瀹藉害楂樺害鍒涘缓涓�涓狟itmap瀵硅薄
	private static Bitmap createBitmap(String path, int width, int height) {
		File file = new File(path);
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				Size size = getBitMapSize(path);
				if (size.equals(ZERO_SIZE)) {
					return null;
				}
				int scale = 1;
				int a = size.getWidth() / width;
				int b = size.getHeight() / height;
				scale = Math.max(a, b);
				synchronized (OPTIONS_DECODE) {
					OPTIONS_DECODE.inSampleSize = scale;
					Bitmap bitMap = BitmapFactory.decodeStream(in, null,
							OPTIONS_DECODE);
					return bitMap;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				closeInputStream(in);
			}
		}
		return null;
	}

	// 鍏抽棴杈撳叆娴�
	private static void closeInputStream(InputStream in) {
		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 鍥剧墖澶у皬
	static class Size {
		private int width, height;

		Size(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

	// 闃熷垪缂撳瓨鍙傛暟瀵硅薄
	static class QueueEntry {
		public String path;
		public int width;
		public int height;
	}

}