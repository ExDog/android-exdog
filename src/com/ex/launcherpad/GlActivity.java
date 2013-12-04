package com.ex.launcherpad;

import rajawali.RajawaliActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import ex.framwork.callback.BitmapAjaxCallback;
import ex.framwork.util.ExUtility;
import ex.widget.gl.universe.ExWidget;

/**
 * 
* @ClassName: GlActivity
* @Description: 启动类
* @author ExDog love_mobile@163.com
* @date 2013年12月3日 下午4:35:25
*
 */
public class GlActivity extends RajawaliActivity {

	private static final String FRAGMENT_TAG = "rajawali";
	private DrawerLayout mDrawerLayout;
    boolean init=false;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//设置启动信息
		if(!init){
			ExUtility.setDebug(true);
			ExUtility.setCacheDir(null);
			BitmapAjaxCallback.setPixelLimit(600 * 600);
			BitmapAjaxCallback.setCacheLimit(200);
			BitmapAjaxCallback.setIconCacheLimit(100);
			BitmapAjaxCallback.setMaxPixelLimit(10000000);	// 
			init = true;	
			//ErrorReporter.installReporter(getApplicationContext());
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Configure the drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		launchFragment(-1);
	}

	@Override
	protected void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
	}

	/**
	 * 
	* @Title: launchFragment
	* @Description: launcher the fragment
	* @param @param position    设定文件
	* @return void    返回类型
	* @throws
	 */
	@SuppressLint("NewApi")
	private void launchFragment(int position) {
		final FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = null;

		// Close the drawer
		mDrawerLayout.closeDrawers();
		// Set fragment title
		final FragmentTransaction transaction = fragmentManager
				.beginTransaction();
		try {
			//测试
			fragment = new ExWidget();
			final Bundle bundle = new Bundle();
			transaction.add(R.id.content_frame, fragment, String.valueOf(position));
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
