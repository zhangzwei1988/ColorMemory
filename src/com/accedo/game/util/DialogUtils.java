package com.accedo.game.util;

import android.content.Context;

import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.ui.ChooseDialog;
import com.accedo.game.ui.InformDialog;
import com.accedo.game.ui.InputDialog;

/**
 * A utils to create dialog
 * 
 * @ClassName: DialogUtils
 * @Description: TODO()
 * @author zzw
 * @date 2013-5-10 下午10:59:35
 */
public class DialogUtils {

	/**
	 * Create and return a ChooseDialog
	 * 
	 * @Title: createChooseDialog
	 * @Description: TODO()
	 * @param @param context
	 * @param @param callBack
	 * @param @param msg
	 * @param @return
	 * @return ChooseDialog
	 * @throws
	 */
	public static ChooseDialog createChooseDialog(Context context,
			DialogCallBack callBack, String msg) {

		ChooseDialog dialog = new ChooseDialog(context, callBack, msg);
		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	/**
	 * Create and return a InputDialog
	 * 
	 * @Title: createInputDialog
	 * @Description: TODO()
	 * @param @param context
	 * @param @param callBack
	 * @param @param score
	 * @param @return
	 * @return InputDialog
	 * @throws
	 */
	public static InputDialog createInputDialog(Context context,
			DialogCallBack callBack, int score) {

		InputDialog dialog = new InputDialog(context, callBack, score);
		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	/**
	 * Create and return a InformDialog
	 * 
	 * @Title: createInformDialog
	 * @Description: TODO()
	 * @param @param context
	 * @param @param callBack
	 * @param @param state
	 * @param @param score
	 * @param @return
	 * @return InformDialog
	 * @throws
	 */
	public static InformDialog createInformDialog(Context context,
			DialogCallBack callBack, int state, int score) {

		InformDialog dialog = new InformDialog(context, callBack, state);
		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}
}
