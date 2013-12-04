
package ex.framwork;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * The main class of AQuery. All methods are actually inherited from AbstractAQuery.
 *
 */
public class Ex extends AbstractAQuery<Ex>{

	
	public Ex(Activity act) {
		super(act);
	}
	
	public Ex(View view) {
		super(view);
	}
	
	public Ex(Context context) {
		super(context);
	}
	
	public Ex(Activity act, View root){
		super(act, root);
	}
	

}


