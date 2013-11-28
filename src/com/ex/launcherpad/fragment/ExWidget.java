package com.ex.launcherpad.fragment;



import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.EllipticalOrbitAnimation3D;
import rajawali.animation.IAnimation3DListener;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.methods.DiffuseMethod;
import rajawali.materials.methods.SpecularMethod;
import rajawali.materials.textures.AlphaMapTexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.math.MathUtil;
import rajawali.math.vector.Vector3;
import rajawali.math.vector.Vector3.Axis;
import rajawali.primitives.Sphere;
import rajawali.util.ObjectColorPicker;
import rajawali.util.OnObjectPickedListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;



public class ExWidget extends SubjectFragment implements OnTouchListener{

	DirectionalLightRenderer mDirectionalLightRenderer;
	
	//是否转动
	private boolean isTurn=true;
	
	//是否点中三维中的view
	private boolean isGeted=false;
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSurfaceView.setOnTouchListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected AExampleRenderer createRenderer() {
		mDirectionalLightRenderer=new DirectionalLightRenderer(getActivity());
		return mDirectionalLightRenderer;
	}

	@Override
	protected boolean isTransparentSurfaceView() {
		return true;
	}
	
	private final class DirectionalLightRenderer extends AExampleRenderer implements OnObjectPickedListener {
		Sphere rootSphere;
		//纹理bitmap
		private Bitmap mTimeBitmap;
		private Canvas mItemCanvas;
		private AlphaMapTexture mTimeTexture;
		
		
		//内部控件列表
		ArrayList<Object3D> list;
		private Paint mTextPaint;
		public DirectionalLightRenderer(Context context) {
			super(context);
		}
		private ObjectColorPicker mPicker;
		@SuppressLint("NewApi")
		protected void initScene() {
			super.initScene();
			list=new ArrayList<Object3D>();
			mPicker = new ObjectColorPicker(this);
			mPicker.setOnObjectPickedListener(this);
			final DirectionalLight directionalLight = new DirectionalLight();
			directionalLight.setPower(1.5f);
			getCurrentScene().addLight(directionalLight);
			getCurrentCamera().setPosition(0,0, 6);
			getCurrentCamera().setLookAt(0, 0, 0);
			
			//纹理 
			//InputStream is = getActivity().getResources().openRawResource(R.drawable.ic_launcher);    
			//mTimeBitmap = BitmapFactory.decodeStream(is); 
			//mTimeBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_drawer).copy(Bitmap.Config.ARGB_8888, true);
			mTimeBitmap = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
			mTimeTexture = new AlphaMapTexture("timeTexture", mTimeBitmap);
			
			Material sphereMaterial = new Material();
			SpecularMethod.Phong phongMethod = new SpecularMethod.Phong();
			phongMethod.setShininess(180);
			sphereMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());
			sphereMaterial.setSpecularMethod(phongMethod);
			sphereMaterial.enableLighting(true);
            
			rootSphere = new Sphere(.2f, 20, 20);
			rootSphere.setMaterial(sphereMaterial);
			rootSphere.setRenderChildrenAsBatch(true);
			rootSphere.setVisible(false);
			rootSphere.setPosition(0,-1.3,0);
			addChild(rootSphere);
			try {
				sphereMaterial.addTexture(mTimeTexture);
			} catch (TextureException e) {
				e.printStackTrace();
			}
			// -- inner ring
			float radius = 2.5f;
			int count = 0;
			for (int i = 0; i < 360; i += 64) {
				double radians = MathUtil.degreesToRadians(i);
				int color = 0xfed14f;
				count++;
				Object3D sphere = rootSphere.clone(false);
				sphere.setPosition((float) Math.sin(radians) * radius, 0,
						(float) Math.cos(radians) * radius);
				sphere.setMaterial(sphereMaterial);
				sphere.setColor(color);
				mPicker.registerObject(sphere);
				list.add(sphere);
				sphere.setName(String.valueOf(i/64));
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

		
		
		public void updateTimeBitmap() {
			new Thread(new Runnable() {
				public void run() {
					if (mItemCanvas == null) {

						mItemCanvas = new Canvas(mTimeBitmap);
						mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
						mTextPaint.setColor(Color.WHITE);
						mTextPaint.setTextSize(75);
						
					}
					//
					// -- Clear the canvas, transparent
					//
					mItemCanvas.drawColor(0, Mode.CLEAR);
					//
					// -- Draw the time on the canvas
					//
					
					mItemCanvas.drawText(("数  学"), 75,
							128, mTextPaint);
					//
					// -- Indicates that the texture should be updated on the OpenGL thread.
					//
				
				}
			}).start();
		}
		
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			if (mFrameCount++ >= mFrameRate) {
				mFrameCount = 0;
				updateTimeBitmap();
			}
			//
			// -- update the texture because it is ready
			//
		//	if (mShouldUpdateTexture) {
				mTimeTexture.setBitmap(mTimeBitmap);
				mTextureManager.replaceTexture(mTimeTexture);
			//	mShouldUpdateTexture = false;
			//}
				if(isTurn==true)
				{
			//mDirectionalLightRenderer.rootSphere.setRotY(mDirectionalLightRenderer.rootSphere.getRotY()-1);
				}
		}
		
	}

	private int mLastMotionX, mLastMotionY;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub\
	
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTurn=false;
			 setIsGeted(false);
			((DirectionalLightRenderer) mRenderer).getObjectAt(event.getX(),
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
			mDirectionalLightRenderer.rootSphere.setRotY(mDirectionalLightRenderer.rootSphere.getRotY()-1);
		}else if(dx<-3)
		{
			mDirectionalLightRenderer.rootSphere.setRotY(mDirectionalLightRenderer.rootSphere.getRotY()+1);
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
	
	private synchronized void setIsGeted(boolean isGeted)
	{
		isGeted=false;
	}

}
