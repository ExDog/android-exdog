package ex.widget.book;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ex.launcherpad.R;




import ex.utils.ReturnBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
* @ClassName: BookAdapter
* @Description: adapter 
* @author ExDog
* @date 2013年11月28日 下午2:29:48
*
 */
public class BookAdapter implements IAdapter{

	private Map<Integer,File> mBitmapIds;
	private Context mContext;
	String bookPath;
	public BookAdapter(Context context,String filePath) {
		super();
		this.mContext = context;
		bookPath=filePath;
		mBitmapIds=getFileList(filePath);
	}
	
	/**
	 * 更换数据内容
	 * @param filePath
	 */
	public void changeData(String filePath)
	{
		if(mBitmapIds!=null)
		{
			mBitmapIds.clear();
			bookPath=filePath;
			mBitmapIds=getFileList(filePath);
		}
	}
	public int getCount() {
		return mBitmapIds.size();
	}

	public String getItem(int position) {
		return String.valueOf(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position) {
		ImageView iv=new ImageView(mContext);
	
		iv.setImageBitmap(loadBitmap(500,300,position));
		return iv;
	}
	
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
			String str = name.substring(0,name.lastIndexOf("."));
			//posit = Integer.parseInt(str);
			map.put(posit, file);
			posit++;
		}
		
		return map;
	}



	private Bitmap loadBitmap(int width, int height, int index) {
		Bitmap b = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		b.eraseColor(0xFFFFFFFF);
		Canvas c = new Canvas(b);
//		Drawable d = getResources().getDrawable(mBitmapIds[index]);
		 Bitmap bm;
		bm= ReturnBitmap.decodeBitmap(bookPath+mBitmapIds.get(index+1).getName(), 0);
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


}
