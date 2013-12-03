package ex.test;

import java.text.SimpleDateFormat;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.SphereMapTexture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ex.launcherpad.R;

import ex.widget.gl.GlWorld;





@SuppressLint("NewApi")
public class DirectionalLightFragment extends GlWorld {

	private SphereMapTexture mTimeTexture;
	private Bitmap mTimeBitmap;
	private Canvas mTimeCanvas;
	private Paint mTextPaint;
	private SimpleDateFormat mDateFormat;
	private int mFrameCount;
	private boolean mShouldUpdateTexture;

	@SuppressLint("NewApi")
	@Override
	protected AExampleRenderer createRenderer() {
		return new DirectionalLightRenderer(getActivity());
	}
	private DirectionalLight mLight;
	private Object3D mSphere;
	private final class DirectionalLightRenderer extends AExampleRenderer {

		public DirectionalLightRenderer(Context context) {
			super(context);
		}

		protected void initScene() {
			mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
			mLight.setColor(1.0f, 1.0f, 1.0f);
			mLight.setPower(2);

			getCurrentScene().addLight(mLight);

			try {
				Material material = new Material();
				material.addTexture(new Texture("earthColors",
						R.drawable.book_chinese));
				material.setColorInfluence(0);
				mSphere = new Sphere(1, 24, 24);
				mSphere.setMaterial(material);
				getCurrentScene().addChild(mSphere);
			} catch (TextureException e) {
				e.printStackTrace();
			}

			getCurrentCamera().setZ(6);
		}
		public void onDrawFrame(GL10 glUnused) {
			super.onDrawFrame(glUnused);
			mSphere.setRotY(mSphere.getRotY() + 1);
		}

	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub

	}
}