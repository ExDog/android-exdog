package ex.widget.gl.photowall;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.TranslateAnimation3D;
import rajawali.curves.CatmullRomCurve3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ex.launcherpad.R;

import ex.framwork.util.ExUtility;
import ex.widget.gl.GlWorld;
import ex.widget.gl.planes.PlanesGalore;
import ex.widget.gl.planes.PlanesGaloreMaterialPlugin;

public class Optimized2000PlanesFragment extends GlWorld {

	@SuppressLint("NewApi")
	@Override
	protected ExRenderer createRenderer() {
		return new Optimized2000PlanesRenderer(getActivity());
	}

	public class Optimized2000PlanesRenderer extends ExRenderer {

		private PlanesGalore mPlanes;
		private long mStartTime;
		private TranslateAnimation3D mCamAnim;
		private Material mMaterial;
		private PlanesGaloreMaterialPlugin mMaterialPlugin;

		public Optimized2000PlanesRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			DirectionalLight light = new DirectionalLight(0, 0, 1);
			
			getCurrentScene().addLight(light);
			getCurrentCamera().setPosition(0, 0, -16);

			mPlanes = new PlanesGalore();
			mMaterial = mPlanes.getMaterial();
			mMaterial.setColorInfluence(0);
			try {
				mMaterial.addTexture(new Texture("flickrPics", R.drawable.flickrpics));
			} catch (TextureException e) {
				e.printStackTrace();
			}
			
			mMaterialPlugin = mPlanes.getMaterialPlugin();
			
			mPlanes.setDoubleSided(true);
			mPlanes.setZ(4);
			addChild(mPlanes);

			Object3D empty = new Object3D();
			addChild(empty);

			CatmullRomCurve3D path = new CatmullRomCurve3D();
			path.addPoint(new Vector3(-4, 0, -20));
			path.addPoint(new Vector3(2, 1, -10));
			path.addPoint(new Vector3(-2, 0, 10));
			path.addPoint(new Vector3(0, -4, 20));
			path.addPoint(new Vector3(5, 10, 30));
			path.addPoint(new Vector3(-2, 5, 40));
			path.addPoint(new Vector3(3, -1, 60));
			path.addPoint(new Vector3(5, -1, 70));

			mCamAnim = new TranslateAnimation3D(path);
			mCamAnim.setDuration(20000);
			mCamAnim.setRepeatMode(RepeatMode.REVERSE_INFINITE);
			mCamAnim.setTransformable3D(getCurrentCamera());
			mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
			registerAnimation(mCamAnim);
			mCamAnim.play();

			getCurrentCamera().setLookAt(new Vector3(0, 0, 30));
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			super.onSurfaceCreated(gl, config);
			mStartTime = System.currentTimeMillis();		
		}

		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mMaterial.setTime((System.currentTimeMillis() - mStartTime) / 1000f);
			mMaterialPlugin.setCameraPosition(getCurrentCamera().getPosition());
		}
	}

	@Override
	protected boolean isTransparentSurfaceView() {
		return true;
	}
	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		ExUtility.debug(arg1);
	}

	
}
