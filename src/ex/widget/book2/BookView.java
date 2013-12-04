package ex.widget.book2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import com.ex.launcherpad.R;

import ex.framwork.util.ExUtility;
import ex.utils.Constant;
import ex.utils.ReturnBitmap;
/**
 * 
* @ClassName: BookFragment
* @Description: 双面翻书效果
* @author ExDog
* @date 2013年11月28日 下午2:46:30
*
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BookView extends CurlView {
	
	//翻书时显示的bitmap
	private Map<Integer,File> mBitmapIds;
	
	//翻书内容的提供者
	private PageProvider pageProvider;
	
	//书籍路径
	private String  bookPath=Constant.CHINESEBOOK;
	
	public BookView(Context ctx,String path) {
		super(ctx);
		onCreateView();
		if(!path.isEmpty()){
			bookPath=path;
		}
		// TODO Auto-generated constructor stub
	}
	
	public void setBookPath(String path)
	{
		if(!path.isEmpty()){
			bookPath=path;
			//pageProvider = new PageProvider();
			mBitmapIds = pageProvider.getFileList(bookPath);
			//this.setPageProvider(pageProvider);
		    this.setCurrentIndex(0);
		}
	}

	public void onCreateView(){
		pageProvider = new PageProvider();
		mBitmapIds = pageProvider.getFileList(bookPath);
		int index = 0;
		//显示内容
		this.setPageProvider(pageProvider);
		this.setSizeChangedObserver(new SizeChangedObserver());
		this.setCurrentIndex(index);
		this.setBackgroundColor(0xFFFFFFFF);
		this.setBackgroundResource(R.drawable.book_back);
	}

	
	/**
	 * Bitmap provider.
	 */
	private class PageProvider implements CurlView.PageProvider {
		
		
		
		public Map<Integer,File> getFileList(String bootPath){
			Map<Integer, File> map = new HashMap<Integer, File>();
			File root = new File(bootPath);
			File[] files = root.listFiles();
			 List<File> list = new ArrayList<File>();
			for (File file : files) {
				if(!file.getName().contains(".temp")){
					list.add(file);
				} 
			}
			
			int posit=0;
			for(File file : list){
				String name = file.getName();
				map.put(posit, file);
				posit++;
			}
			
			return map;
		}

		@Override
		public int getPageCount() {
			if ((mBitmapIds.size()) % 2 == 1) {
				return (mBitmapIds.size()) / 2 + 1;
			} else {
				return (mBitmapIds.size()) / 2;
			}
		}

		private Bitmap loadBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Bitmap bm;
			bm= ReturnBitmap.decodeBitmap(bookPath+mBitmapIds.get(index).getName(), 0);
			if(index%2==1)
			{
				bm=convert(bm,bm.getWidth(), bm.getHeight());
			}
			BitmapDrawable d = new BitmapDrawable(bm);
			int margin = 7;
			int border = 3;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}

		/**
		 * 书本部分内容翻转
		 * @param a        需要处理的bitmap
		 * @param width    bitmap宽度
		 * @param height   bitmap高度
		 * @return         处理后的bitmap
		 */
		Bitmap convert(Bitmap a, int width, int height)
		{
		int w = a.getWidth();
		int h = a.getHeight();
		Bitmap newb = Bitmap.createBitmap(width, height, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		Matrix m = new Matrix();
		//m.postScale(1, -1);   //镜像垂直翻转
		m.postScale(-1, 1);   //镜像水平翻转
		//m.postRotate(-90);  //旋转-90度
		Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
		cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()),new Rect(0, 0, width, height), null);
		return newb;
		}
		
		
		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {
			
			ExUtility.debug("Jiangx", "index==:" + index);
				if (index == (mBitmapIds.size()) / 2) {
					Bitmap front = loadBitmap(width, height,
							mBitmapIds.size() - 1);
					page.setTexture(front, CurlPage.SIDE_BOTH);
					page.setColor(Color.argb(127, 255, 255, 255),
							CurlPage.SIDE_BACK);
				} else if (index < (mBitmapIds.size()) / 2) {
					Bitmap front = loadBitmap(width, height, 2 * index);
					Bitmap back = loadBitmap(width, height, 2 * index + 1);
					page.setTexture(front, CurlPage.SIDE_FRONT);
					page.setTexture(back, CurlPage.SIDE_BACK);
				}
		}
	}
	

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				BookView.this.setViewMode(CurlView.SHOW_TWO_PAGES);
				BookView.this.setMargins(.1f, .05f, .1f, .05f);
			} else {
				BookView.this.setViewMode(CurlView.SHOW_ONE_PAGE);
				BookView.this.setMargins(.1f, .1f, .1f, .1f);
			}
		}
	}

}