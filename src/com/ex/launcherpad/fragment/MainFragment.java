package com.ex.launcherpad.fragment;

import com.ex.launcherpad.GlActivity;
import com.ex.launcherpad.R;
import com.launcher.pad.adapter.MainAdapter;

import ex.widget.gl.photowall.Optimized2000PlanesFragment;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class MainFragment extends Fragment{

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (RelativeLayout) inflater.inflate(R.layout.main,
				container, false);
		ll = (RelativeLayout) view.findViewById(R.id.rl_mian_icons_main);
		gv = (GridView) view.findViewById(R.id.gv_main_icon);
		init();
		return view;
	}
	
	
	RelativeLayout ll, rl_message, office_num_re;
	MainAdapter mMainAdapter;
	GridView gv;
	public void init() {
		mMainAdapter = new MainAdapter(getActivity());
		gv.setAdapter(mMainAdapter);
		gv.setOnItemClickListener(new MainListener());
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
		//mDrawerLayout.closeDrawers();
		// Set fragment title
		final FragmentTransaction transaction = fragmentManager
				.beginTransaction();
		try {
			
			Fragment oldFrag = fragmentManager.findFragmentByTag(String.valueOf(position));
			if (oldFrag != null)
				transaction.remove(oldFrag);
			//测试
			if(position==0)
			{
			fragment = new ExWidget();
			}else
			{
			fragment = new Optimized2000PlanesFragment();
			}
			final Bundle bundle = new Bundle();
			transaction.add(R.id.content_frame, fragment, String.valueOf(position));
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class MainListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:// 校情展示
				//intent.putExtra("Key", Constant.SCHOOLE_SHOW);
				//intent.setClass(LauncherActivity.this, CommonActivity.class);
				launchFragment(position);
			
				break;
			case 1:// 产品介绍
				launchFragment(position);
				break;
			
			}

		}
	}
}
