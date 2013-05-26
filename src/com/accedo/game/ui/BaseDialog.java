package com.accedo.game.ui;

import android.app.Dialog;
import android.content.Context;

public abstract class BaseDialog extends Dialog {

	public BaseDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BaseDialog(Context context, int theme) {
		// TODO Auto-generated constructor stub
		super(context, theme);
	}

	public abstract void findViews();

	public abstract void initValues();

	public abstract void bindEvents();

}
