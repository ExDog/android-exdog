package ex.widget.gl.universe;



import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.SphereMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.math.MathUtil;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Sphere;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;
import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

import com.ex.launcherpad.R;

import ex.framwork.util.ExUtility;



/**
 * 
 * @ClassName: ExWidget
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ExDog
 * @date 2013年12月2日 下午1:30:03
 *
 */
public class ExWidget extends SubjectFragment implements OnTouchListener{

	UniverseWidget unverseRenderer;

	//是否转动
	private boolean isTurn=true;

	//是否点中三维中的view
	private boolean isGeted=false;

	//球体纹理列表
	int[] sphereResId ={R.drawable.book_chinese,
			R.drawable.book_math,R.drawable.book_english,
			R.drawable.book_chem,R.drawable.book_physical,
			R.drawable.book_chinese_2,R.drawable.book_chinese_1,
			R.drawable.book_chinese_1,R.drawable.book_chinese_1
			};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSurfaceView.setOnTouchListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected ExRenderer createRenderer() {
		unverseRenderer=new UniverseWidget(getActivity());
		return unverseRenderer;
	}

	@Override
	/**
	 * 是否让三维控件至顶 
	 */
	protected boolean isTransparentSurfaceView() {
		return true;
	}

	private final class UniverseWidget extends ExRenderer implements OnObjectPickedListener {
		Sphere rootSphere;
		//纹理bitmap
		//private Bitmap mTimeBitmap;
		private Canvas mItemCanvas;
		private SphereMapTexture mTimeTexture;


		//内部控件列表
		ArrayList<Object3D> list;
		private Paint mTextPaint;
		public UniverseWidget(Context context) {
			super(context);
		}
		private ObjectColorPicker mPicker;
		@SuppressLint("NewApi")
		protected void initScene() {
			super.initScene();
			list=new ArrayList<Object3D>();
			mPicker = new ObjectColorPicker(this);
			mPicker.setOnObjectPickedListener(this);
			getCurrentCamera().setPosition(0,5, 7);
			getCurrentCamera().setLookAt(0, 0, 0);

			Material material = new Material();
			try {
				material.addTexture(new Texture("earthColors",
						R.drawable.book_chinese_1));
				//material.addTexture(new Texture("aaa",
				//		R.drawable.book_chinese_2));
				
			} catch (TextureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			material.setColorInfluence(0);

			rootSphere = new Sphere(.0f, 20, 20);

			rootSphere.setMaterial(material);
			rootSphere.setRenderChildrenAsBatch(false);
			rootSphere.setVisible(true);
			rootSphere.setPosition(0,1,0);
			addChild(rootSphere);

			// -- inner ring
			float radius = 3.5f;
			int count = 0;
			for (int i = 0; i < 360; i += 40) {
				double radians = MathUtil.degreesToRadians(i);
				//int color = 0xfed14f;
				count++;
				//Object3D sphere = rootSphere.clone(false);
				Sphere sphere = new Sphere(.3f, 20, 20);
				sphere.setPosition((float) Math.sin(radians) * radius, 0,
						(float) Math.cos(radians) * radius);
                Material materialchild = new Material();
				try {
					materialchild.addTexture(new Texture("aaa"+i,sphereResId[i/40]
					));
				} catch (TextureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             materialchild.setColorInfluence(0);
				sphere.setMaterial(materialchild);
				mPicker.registerObject(sphere);
				list.add(sphere);
				sphere.setName(String.valueOf(i/40));
				rootSphere.addChild(sphere);
				int direction = Math.random() < .5 ? 1 : -1;
				RotateAnimation3D anim = new RotateAnimation3D(Axis.Y, 0,
						360 * direction);
				anim.setRepeatMode(RepeatMode.INFINITE);
				anim.setDuration(i == 0 ? 12000
						: 4000 + (int) (Math.random() * 4000));
				anim.setTransformable3D(sphere);
				registerAnimation(anim);
				anim.play();
			}
			//设置三维场景没有背景颜色
			getCurrentScene().setBackgroundColor(0);
		}

		public void getObjectAt(float x, float y) {
			mPicker.getObjectAt(x, y);
		}

		@SuppressLint("NewApi")
		@Override
		public void onObjectPicked(Object3D object) {
			// TODO Auto-generated method stub
			//object.setScale(object.getScaleX()==1 ? 1.5:1);
			Message message = new Message();   
			message.what = Integer.parseInt(object.getName());   

			mHandler.sendMessage(message); 
			setIsGeted(true);
		}

		/**
		 * 相当于平面中的ondraw函数
		 */
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			if(isTurn)
			{
				unverseRenderer.rootSphere.setRotY(unverseRenderer.rootSphere.getRotY()-0.3);
			}
		}

		public void setVisiable(boolean isVisiable)
		{
			rootSphere.setVisible(isVisiable);
		}
	}
	private int mLastMotionX, mLastMotionY;

	/**
	 * 重写touch函数
	 */
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub\

		int x = (int) event.getX();
		int y = (int) event.getY();
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTurn=false;
			setIsGeted(false);
			((UniverseWidget) mRenderer).getObjectAt(event.getX(),
					event.getY());
			if(!isGeted)
			{
				Message message = new Message();   
				message.what = -1;   
				mHandler.sendMessage(message);
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = x - mLastMotionX;
			int dy = y - mLastMotionY;
			if(dx>3)
			{
				unverseRenderer.rootSphere.setRotY(unverseRenderer.rootSphere.getRotY()-1);
			}else if(dx<-3)
			{
				unverseRenderer.rootSphere.setRotY(unverseRenderer.rootSphere.getRotY()+1);
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			isTurn=true;
			break;
		}
		return true;
	}

	/**
	 * 
	 * @Title: setIsGeted
	 * @Description: 描述是控制是否点中三维空间中的物体
	 * @param @param isGeted    yes 为选中
	 * @return void    无
	 * @throws
	 */
	private synchronized void setIsGeted(boolean isGeted)
	{
		isGeted=false;
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
	
	}

	@Override
	/**
	 * 设置球可见与不可见
	 */
	public void setSphereVisible(boolean isVisiable) {
		// TODO Auto-generated method stub
		unverseRenderer.setVisiable(isVisiable);
	}
	

}

