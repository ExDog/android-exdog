package ex.widget.gl;

import javax.microedition.khronos.opengles.GL10;

import com.ex.launcherpad.R;
import com.ex.launcherpad.R.id;
import com.ex.launcherpad.R.layout;

import rajawali.RajawaliFragment;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.opengl.EGLConfig;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public abstract class GlWorld extends RajawaliFragment implements
OnClickListener {
	public static final String BUNDLE_EXAMPLE_URL = "BUNDLE_EXAMPLE_URL";
	protected RajawaliRenderer mRenderer;
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
		mLayout.addView(mSurfaceView);
		return mLayout;
	}
	
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
	protected abstract ExRenderer createRenderer();
	protected void hideLoader() {
		//mProgressBarLoader.post(new Runnable() {
		
	}
	protected boolean isTransparentSurfaceView() {
		return false;
	}
	protected void showLoader() {
	
	}
	protected abstract class ExRenderer extends RajawaliRenderer {
		public ExRenderer(Context context) {
			super(context);
			setFrameRate(60);
		}
		public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
			showLoader();
			super.onSurfaceCreated(gl, config);
			hideLoader();
		}
	}
	final class NullRenderer extends RajawaliRenderer {
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