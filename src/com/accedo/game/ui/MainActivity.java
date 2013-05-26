package com.accedo.game.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.accedo.game.ColourMemoryApp;
import com.accedo.game.R;
import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.model.GameStatus;
import com.accedo.game.util.DialogUtils;

public class MainActivity extends BaseActivity implements OnClickListener {

	public static ColourMemoryApp app;
	public static GameStatus gameStatus;

	public static GameF gameF;
	private FragmentTransaction ft;

	ChooseDialog chooseDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initValues();
		findViews();
		bindEvents();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Fragment fragment = getSupportFragmentManager().findFragmentById(
					R.id.layout1);
			if (fragment instanceof GameF) {
				chooseDialog = DialogUtils.createChooseDialog(
						MainActivity.this, dialogCallBack,
						getString(R.string.txt_quit_game));
				chooseDialog.show();
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		app = (ColourMemoryApp) getApplication();

		getWindowManager().getDefaultDisplay().getMetrics(app.dm);
	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		ft = getSupportFragmentManager().beginTransaction();
		SplashF splash = new SplashF();
		ft.replace(R.id.layout1, splash);

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	DialogCallBack dialogCallBack = new DialogCallBack() {

		@Override
		public void onUpload() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPositive() {
			// TODO Auto-generated method stub
			chooseDialog.dismiss();
			onBackPressed();
		}

		@Override
		public void onNegative() {
			// TODO Auto-generated method stub
		}

		@Override
		public void onConfirm() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}
	};

}
