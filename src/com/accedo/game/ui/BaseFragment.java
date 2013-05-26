package com.accedo.game.ui;

import android.support.v4.app.Fragment;
import android.view.View;

import com.accedo.game.util.Logger;

public abstract class BaseFragment extends Fragment {

	protected View main;
	protected Logger logger;

	/**
	 * bind the control
	 * 
	 * @Title: findViews
	 * @param
	 * @return void
	 * @throws
	 */
	abstract public void findViews();

	/**
	 * Initialize the variable
	 * 
	 * @Title: initValues
	 * @param
	 * @return void
	 * @throws
	 */
	abstract public void initValues();

	/**
	 * Bind the page event
	 * 
	 * @Title: bindEvents
	 * @param
	 * @return void
	 * @throws
	 */
	abstract public void bindEvents();
}
