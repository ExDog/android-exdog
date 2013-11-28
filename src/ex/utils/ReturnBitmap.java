package ex.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ReturnBitmap {
	public static Bitmap decodeBitmap(String path,int pixl) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
		BitmapFactory.decodeFile(path, opts);
		if(pixl>3){
			opts.inSampleSize = pixl;
		}else if(pixl != 2){
			opts.inSampleSize = computeSampleSize(opts, -1, 1280 * 800);
		}
		opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
		opts.inPurgeable = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[16 * 1024];
		FileInputStream is = null;
		Bitmap bmp = null;
		Bitmap bmpRetun = null;
		try {
			is = new FileInputStream(path);
			if(path == null){
				
			}
			bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
			double scale = getScaling(opts.outWidth * opts.outHeight,
					1280 * 800);
			bmpRetun = Bitmap.createScaledBitmap(bmp,
					(int) (opts.outWidth * scale),
					(int) (opts.outHeight * scale), true);
			if(pixl == 2){
				return bmp;
			}else{
			bmp.recycle();}
			return bmpRetun;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.gc();
		}
		return bmpRetun;
	}

	public static OutputStream decodeOS(String path,int pixl) {
		Bitmap bm = decodeBitmap(path,pixl);
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			bm.recycle();
			baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return baos;
	}

	private static double getScaling(int src, int des) {
		/**
		 * 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比
		 */
		double scale = Math.sqrt((double) des / (double) src);
		return scale;
	}

	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
}
