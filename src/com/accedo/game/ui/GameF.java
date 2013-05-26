package com.accedo.game.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.callback.GameCallBack;
import com.accedo.game.util.DialogUtils;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.Logger;
import com.accedo.game.util.MemoryPreference;

@SuppressLint("HandlerLeak")
public class GameF extends BaseFragment implements OnClickListener {

	GameSurface gameSurface;

	RelativeLayout layout1;
	TextView txt_MyScore;
	TextView txt_HighScore;
	DimenUtils dimenUtils;
	ImageView img_logo;

	ChooseDialog chooseDialog;
	InputDialog inputDialog;
	InformDialog informDialog;

	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		main = inflater.inflate(R.layout.gameview, null);

		initValues();
		findViews();
		bindEvents();

		return main;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		MainActivity.gameStatus = null;
		System.gc();

		logger.d("GameF onDestroy");
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		gameSurface = (GameSurface) main.findViewById(R.id.gamePanel);

		layout1 = (RelativeLayout) main.findViewById(R.id.layout1);
		img_logo = (ImageView) main.findViewById(R.id.img_logo);
		txt_MyScore = (TextView) main.findViewById(R.id.txt_myscore);
		txt_HighScore = (TextView) main.findViewById(R.id.txt_highscore);

		txt_MyScore.setTypeface(MainActivity.app.tf);
		txt_HighScore.setTypeface(MainActivity.app.tf);

		txt_MyScore.setText(R.string.txt_reset_score);
		txt_HighScore.setText(R.string.txt_highscore);

		dimenUtils.setSizeHorizontal(layout1, 0, 150);
		dimenUtils.setSizeHorizontal(img_logo, 200, 80);
		dimenUtils.setSizeHorizontal(txt_MyScore, 230, 95);
		dimenUtils.setSizeHorizontal(txt_HighScore, 229, 120);
	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		logger = Logger.Jlog();
		intent = new Intent();
		dimenUtils = new DimenUtils(getActivity());

		logger.d("onCreateView");

	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		GameSurface.setGameCallBack(gameCallBack);
		txt_HighScore.setOnClickListener(this);
		txt_MyScore.setOnClickListener(this);

	}

	GameCallBack gameCallBack = new GameCallBack() {

		@Override
		public void setTimeOut() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = MemoryPreference.TIMEOUT;
			handler.sendMessage(msg);
		}

		@Override
		public void setSuccess() {
			// TODO Auto-generated method stub

			Message msg = new Message();
			msg.what = MemoryPreference.GAME_SUCCESS;
			msg.arg1 = MainActivity.gameStatus.score;
			handler.sendMessage(msg);
		}

		@Override
		public void setScore(int state) {
			// TODO Auto-generated method stub

			Message msg = new Message();
			msg.what = MemoryPreference.UPDATE_SCORE;
			msg.arg1 = state;
			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MemoryPreference.UPDATE_SCORE:

				txt_MyScore.setText(getString(R.string.txt_present_score)
						.concat(String.valueOf(MainActivity.gameStatus.score)));

				if (msg.arg1 == MemoryPreference.RIGHT_ANSWER) {
					MainActivity.app.vibrate();
					MainActivity.app.playAudio(MemoryPreference.RIGHT_ANSWER);
				} else {
					MainActivity.app.playAudio(MemoryPreference.WRONG_ANSWER);
				}

				break;
			case MemoryPreference.GAME_SUCCESS:

				informDialog = DialogUtils.createInformDialog(getActivity(),
						dialogCallBack, MemoryPreference.SUCCESS,
						MainActivity.gameStatus.score);
				informDialog.show();

				MainActivity.app.vibrate();
				MainActivity.app.playAudio(MemoryPreference.GAME_WIN);
				break;

			case MemoryPreference.TIMEOUT:

				informDialog = DialogUtils.createInformDialog(getActivity(),
						dialogCallBack, MemoryPreference.TIMEOUT,
						MainActivity.gameStatus.score);
				informDialog.show();

				MainActivity.app.vibrate();
				MainActivity.app.playAudio(MemoryPreference.GAME_OVER);

				break;

			}

		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		MainActivity.app.playAudio(MemoryPreference.CLICK);

		switch (v.getId()) {
		case R.id.txt_highscore:

			intent.setClass(getActivity(), HighScoreActivity.class);

			startActivity(intent);
			break;
		case R.id.txt_myscore:

			// informDialog = DialogUtils.createInformDialog(getActivity(),
			// dialogCallBack, MemoryPreference.SUCCESS,
			// MainActivity.gameStatus.score);
			// informDialog.show();

			break;
		}
	}

	DialogCallBack dialogCallBack = new DialogCallBack() {

		@Override
		public void onPositive() {
			// TODO Auto-generated method stub

			chooseDialog.dismiss();
			txt_MyScore.setText(R.string.txt_reset_score);
			gameSurface.comeAagin();
		}

		@Override
		public void onNegative() {
			// TODO Auto-generated method stub

			chooseDialog.dismiss();

		}

		@Override
		public void onUpload() {
			// TODO Auto-generated method stub
			informDialog.dismiss();

			inputDialog = DialogUtils.createInputDialog(getActivity(),
					dialogCallBack, MainActivity.gameStatus.score);

			inputDialog.show();
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onConfirm() {
			// TODO Auto-generated method stub

			inputDialog.dismiss();

			if (chooseDialog == null) {
				chooseDialog = DialogUtils.createChooseDialog(getActivity(),
						dialogCallBack, getString(R.string.txt_come_again));
			}
			chooseDialog.show();

			logger.d("Confrim");
		}

	};

}
