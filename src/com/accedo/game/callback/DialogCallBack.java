package com.accedo.game.callback;

/**
 * DialogCallBack,handle the event invoked by user
 * 
 * @ClassName: DialogCallBack
 * @Description: TODO()
 * @author zzw
 */
public interface DialogCallBack {

	public void onPositive();

	public void onNegative();

	public void onCancel();

	public void onUpload();

	public void onConfirm();
}
