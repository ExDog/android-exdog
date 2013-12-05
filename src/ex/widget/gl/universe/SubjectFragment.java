package ex.widget.gl.universe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.ex.launcherpad.R;

import ex.framwork.util.ExUtility;
import ex.utils.Constant;
import ex.utils.L;
import ex.widget.book.BookAdapter;
import ex.widget.book.BookLayout;
import ex.widget.book2.BookView;
import ex.widget.gl.GlWorld;

@SuppressLint("NewApi")
/**
 * 
* @ClassName: SubjectFragment
* @Description: TODO  1.学科界面三维现实 2.学科界面书籍显示  3.学科界面书籍动画
* @author ExDog love_mobile@163.com
* @date 2013年12月5日 下午3:32:40
*
 */
public abstract class SubjectFragment extends GlWorld implements
		OnClickListener {
	//关于书
	 BookAdapter bookAdapter; 
	 View bookView;
	 View animationView;
	 int animationTime=1000;
	
	 /**
	  * 处理点击三维object点击事件的handler
	  */
	public Handler mHandler=new Handler()
	{
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case -1:
				if(bookView.getVisibility()==View.VISIBLE)
				{
				hide();
				}
				break;
			case 0:
				((BookView)bookView).setBookPath(Constant.MATHBOOK);
				show();
				break;
			case 1:
				((BookView)bookView).setBookPath(Constant.CHEMBOOK);
				show();
				break;
			case 2:
				((BookView)bookView).setBookPath(Constant.BIOLOGYBOOK);
				show();
				break;
			case 3:
				((BookView)bookView).setBookPath(Constant.FOREIGNLANGBOOK);
				show();
				break;
			case 4:
				((BookView)bookView).setBookPath(Constant.PHYSICALBOOK);
				show();
				break;
			case 5:
				((BookView)bookView).setBookPath(Constant.HISTORYBOOK);
				show();
				break;
			default:
				break;
			}
		}
	};
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mLayout.setOnClickListener(this);
		mLayout.addView(initTest());
		mLayout.addView(initBook2());
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
		bookView.setVisibility(View.INVISIBLE);
		LayoutParams lp=new LayoutParams(800, 600);
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		lp.setMargins(0, 80, 0, 0);
		bookView.setLayoutParams(lp);
		bookView.setOnClickListener(this);
		return bookView;
	}
	
	public View initTest()
	{
		animationView=new ImageView(getActivity());
		animationView.setBackgroundResource(R.drawable.ex_animation_test);
		LayoutParams lp=new LayoutParams(800, 600);
		animationView.setVisibility(View.INVISIBLE);
		lp.gravity=Gravity.CENTER_HORIZONTAL;
		lp.setMargins(0, 80, 0, 0);
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
		
		  //  animationView.setVisibility(View.VISIBLE);
            animation_alpha=new AlphaAnimation(0.1f,1.0f);  
            animation_alpha.setDuration(animationTime);
            animation_rotate = new RotateAnimation(0, -720,  
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,  
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);  
           
            animation_rotate.setDuration(animationTime);
              
            animation_scale=new ScaleAnimation(0.1f,1.0f,0.1f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);  
          
            animation_scale.setDuration(animationTime); 
              
            L.d(""+animationView.getX());
            //animation_translate=new TranslateAnimation(0,0,400,200);  
            
            //animation_translate.setDuration(animationTime);
              
            animationSet=new AnimationSet(true);  
            animationSet.addAnimation(animation_alpha);//透明度  
            animationSet.addAnimation(animation_scale);//尺寸伸缩  
           // animationSet.addAnimation(animation_translate);//移动  
            animationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					if(bookView.getVisibility()==View.VISIBLE)
					{
						bookView.setVisibility(View.INVISIBLE);
					}
					SubjectFragment.this.setSphereVisible(false);
				//	mSurfaceView.setVisibility(View.INVISIBLE);
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					//animationView.setVisibility(View.INVISIBLE);
					bookView.setVisibility(View.VISIBLE);
				}
			});
            animationView.startAnimation(animationSet);//开始播放  
	}
	
	/**
	 * 
	* @Title: hide
	* @Description: 书隐藏
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void hide()
	{
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
          // animation_translate=new TranslateAnimation(0,0,5,400);  
           //animation_translate.setDuration(animationTime);//设置时间持续时间为 5000毫秒  
             
           animationSet=new AnimationSet(true);  
             
           animationSet.addAnimation(animation_alpha);//透明度  
           animationSet.addAnimation(animation_scale);//尺寸伸缩  
           //animationSet.addAnimation(animation_translate);//移动  
           animationSet.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					bookView.setVisibility(View.GONE);
					ExUtility.debug("XIA", "3");
					//mSurfaceView.setVisibility(View.VISIBLE);
				//SubjectFragment.this.setSphereVisible(true);
					ExUtility.debug("XIA", "4");
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					//animationView.setVisibility(View.INVISIBLE);
					SubjectFragment.this.setSphereVisible(true);
				}
			});
           animationView.startAnimation(animationSet);//开始播放  
	}
	

	@Override
	public void onClick(View v) {
		if(v instanceof FrameLayout)
		{
			Message msg=new Message();
			msg.what=-1;
			mHandler.sendMessage(msg);
		}
	}

	/**
	 * 
	* @Title: setSphereVisible
	* @Description: 设置书是否可见的借口，在子类中调用
	* @param @param isVisible    设定文件
	* @return void    返回类型
	* @throws
	 */
	public abstract void setSphereVisible(boolean isVisible);

}
