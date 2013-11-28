package com.ex.launcherpad.fragment;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ex.launcherpad.R;
import com.ex.launcherpad.R.drawable;
import com.ex.launcherpad.R.id;
import com.ex.launcherpad.R.layout;

import ex.utils.Constant;
import ex.utils.L;
import ex.widget.book.BookAdapter;
import ex.widget.book.BookLayout;
import ex.widget.book2.BookView;
import rajawali.RajawaliFragment;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.FrameLayout.LayoutParams;



@SuppressLint("NewApi")
public abstract class SubjectFragment extends RajawaliFragment implements
		OnClickListener {

	public static final String BUNDLE_EXAMPLE_URL = "BUNDLE_EXAMPLE_URL";
	
	protected RajawaliRenderer mRenderer;
	protected ProgressBar mProgressBarLoader;

	protected String mExampleUrl;

	//关于书
	 BookAdapter bookAdapter; 
	 View bookView;
	 View animationView;
	 int animationTime=1000;
	 
	public Handler mHandler=new Handler()
	{
		public void handleMessage(Message msg) {
			
			Log.i("xia","msg::::::::::"+msg.what);
			switch (msg.what) {
			case -1:
				if(bookView.getVisibility()==View.VISIBLE)
				{
				hide();
				}
				break;
			case 0:
				show();
				break;
			case 1:
				show();
				break;
			case 2:
				show();
				break;
			case 3:
				show();
				break;
			case 4:
				show();
				break;
			case 5:
				show();
				break;
			default:
				break;
			}
			
			 
			 super.handleMessage(msg);   
		};
	};
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isTransparentSurfaceView())
			setGLBackgroundTransparent(true);

		mRenderer = createRenderer();
		if (mRenderer == null)
			mRenderer = new NullRenderer(getActivity());

		mRenderer.setSurfaceView(mSurfaceView);
		setRenderer(mRenderer);
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayout = (FrameLayout) inflater.inflate(R.layout.rajawali_fragment,
				container, false);
		mLayout.findViewById(R.id.relative_layout_loader_container)
				.bringToFront();
		mProgressBarLoader=new ProgressBar(getActivity());
		mLayout.addView(mSurfaceView);
		mLayout.addView(initBook2(),2);
		mLayout.addView(initTest(),2);
		return mLayout;
	}

	@SuppressLint("NewApi")
	public View initBook1()
	{
		 bookView = new BookLayout(getActivity());
		FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(800, 450);
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		lp.setMargins(0, 5, 0, 0);
		bookView.setLayoutParams(lp);
        bookAdapter = new BookAdapter(getActivity(),Constant.CHINESEBOOK);
        ((BookLayout)bookView).setPageAdapter(bookAdapter);
		return bookView;
	}
	
	/**
	 * 
	* @Title: initBool2
	* @Description: 测试翻书效果2
	* @param @return    
	* @return Fragment    返回类型
	* @throws
	 */
	@SuppressLint("NewApi")
	public View initBook2()
	{
		bookView=new BookView(getActivity(),Constant.CHINESEBOOK);
		bookView.setVisibility(View.GONE);
		LayoutParams lp=new LayoutParams(800, 450);
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		lp.setMargins(0, 5, 0, 0);
		bookView.setLayoutParams(lp);
		return bookView;
	}
	
	public View initTest()
	{
		animationView=new ImageView(getActivity());
		animationView.setBackgroundResource(R.drawable.chinesebook);
		LayoutParams lp=new LayoutParams(800, 450);
		animationView.setVisibility(View.GONE);
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		lp.setMargins(0, 5, 0, 0);
		animationView.setLayoutParams(lp);
		return animationView;
	}
	  private Animation animation_alpha,animation_scale,animation_translate,animation_rotate;  
	    private AnimationSet animationSet;  
	    
	/**
	 * 书出现
	 */
	public void show()
	{
		if(bookView.getVisibility()==View.VISIBLE)
		{
		bookView.setVisibility(View.GONE);
		}
		    animationView.setVisibility(View.VISIBLE);
            animation_alpha=new AlphaAnimation(0.1f,1.0f);  
            animation_alpha.setDuration(animationTime);
            animation_rotate = new RotateAnimation(0, -720,  
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,  
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);  
           
            animation_rotate.setDuration(animationTime);
              
            animation_scale=new ScaleAnimation(0.1f,1.0f,0.1f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);  
          
            animation_scale.setDuration(animationTime); 
              
            L.d(""+animationView.getX());
            animation_translate=new TranslateAnimation(0,0,400,5);  
            
            animation_translate.setDuration(animationTime);
              
            animationSet=new AnimationSet(true);  
            animationSet.addAnimation(animation_alpha);//透明度  
            animationSet.addAnimation(animation_scale);//尺寸伸缩  
            animationSet.addAnimation(animation_translate);//移动  
            animationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					animationView.setVisibility(View.GONE);
					bookView.setVisibility(View.VISIBLE);
				}
			});
            animationView.startAnimation(animationSet);//开始播放  
	}
	
	public void hide()
	{
		   animationView.setVisibility(View.VISIBLE);
		    //透明度控制动画效果 alpha  
           animation_alpha=new AlphaAnimation(1f,0.1f);  
           
           animation_alpha.setDuration(animationTime);//设置时间持续时间为 5000毫秒  
             
           // 旋转效果rotate  
           animation_rotate = new RotateAnimation(0, -720,  
                   RotateAnimation.RELATIVE_TO_SELF, 0.5f,  
                   RotateAnimation.RELATIVE_TO_SELF, 0.5f);  
            
           animation_rotate.setDuration(animationTime);//设置时间持续时间为 5000毫秒  
             
           //尺寸伸缩动画效果 scale  
           animation_scale=new ScaleAnimation(1f,0.1f,1f,0.1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);  
           animation_scale.setDuration(animationTime);//设置时间持续时间为 5000毫秒  
             
           //移动动画效果translate  
           L.d(""+animationView.getX());
           animation_translate=new TranslateAnimation(0,0,5,400);  
           animation_translate.setDuration(animationTime);//设置时间持续时间为 5000毫秒  
             
           animationSet=new AnimationSet(true);  
             
           animationSet.addAnimation(animation_alpha);//透明度  
           animationSet.addAnimation(animation_scale);//尺寸伸缩  
           animationSet.addAnimation(animation_translate);//移动  
           animationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					animationView.setVisibility(View.VISIBLE);
					bookView.setVisibility(View.GONE);
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					animationView.setVisibility(View.GONE);
				}
			});
           animationView.startAnimation(animationSet);//开始播放  
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		}
	}

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
		mRenderer.onSurfaceDestroyed();
	}

	/**
	 * Create a renderer to be used by the fragment. Optionally null can be returned by fragments
	 * that do not intend to display a rendered scene. Returning null will cause a warning to be
	 * logged to the console in the event null is in error.
	 * 
	 * @return
	 */
	protected abstract AExampleRenderer createRenderer();

	protected void hideLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.GONE);
			}
		});
	}

	protected boolean isTransparentSurfaceView() {
		return false;
	}

	protected void showLoader() {
		mProgressBarLoader.post(new Runnable() {
			@Override
			public void run() {
				mProgressBarLoader.setVisibility(View.VISIBLE);
			}
		});
	}

	protected abstract class AExampleRenderer extends RajawaliRenderer {

		public AExampleRenderer(Context context) {
			super(context);
			setFrameRate(60);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}

	}

	private static final class NullRenderer extends RajawaliRenderer {

		public NullRenderer(Context context) {
			super(context);
			RajLog.w(this + ": Fragment created without renderer!");
		}

		@Override
		public void onSurfaceDestroyed() {
			stopRendering();
		}
	}

}
