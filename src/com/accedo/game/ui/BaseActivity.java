package com.accedo.game.ui;

import android.support.v4.app.FragmentActivity;

import com.accedo.game.util.Logger;

/**
 * Base Activity of the App, all the activity should extends it
 * 
 * @ClassName: BaseActivity
 * @author Jerry
 * @date 2013-1-22 上午10:00:40
 */
public abstract class BaseActivity extends FragmentActivity {

	protected Logger logger;

	public abstract void findViews();

	public abstract void initValues();

	public abstract void bindEvents();

}
