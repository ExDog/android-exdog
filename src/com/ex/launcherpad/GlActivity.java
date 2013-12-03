package com.ex.launcherpad;

import java.util.Map;

import com.ex.launcherpad.fragment.ExWidget;


import ex.widget.gl.photowall.Optimized2000PlanesFragment;
import rajawali.RajawaliActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class GlActivity extends RajawaliActivity {

	private static final String FRAGMENT_TAG = "rajawali";
	private DrawerLayout mDrawerLayout;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
	 * Launch a fragment selected from the drawer or at application start.
	 * 
	 * @param fragClass
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
