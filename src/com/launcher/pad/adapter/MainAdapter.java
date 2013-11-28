package com.launcher.pad.adapter;



import com.ex.launcherpad.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MainAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	private static int[] icons ={
		R.drawable.ico_r1_c1,R.drawable.ico_r1_c2,R.drawable.ico_r1_c3,R.drawable.ico_r1_c9,R.drawable.ico_r1_c11,
		R.drawable.ico_r3_c1,R.drawable.ico_r3_c3,R.drawable.ico_r3_c7,R.drawable.ico_r3_c9,R.drawable.ico_r3_c11}; 
	
	 
	public MainAdapter(Context context) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return icons.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.main_item, null);
		} else {
			view = convertView;
		}
		ImageView images = (ImageView) view.findViewById(R.id.iv_main_item);
		images.setImageResource(icons[position]);
		
		return view;
	
	}

}
