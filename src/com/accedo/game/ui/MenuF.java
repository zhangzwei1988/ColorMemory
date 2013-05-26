package com.accedo.game.ui;

import com.accedo.game.R;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.Logger;
import com.accedo.game.util.MemoryPreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuF extends BaseFragment implements OnClickListener {

	private ImageButton btn_play;
	private ImageButton btn_help;
	private ImageButton btn_about;

	private ImageButton btn_voice;
	private ImageButton btn_vibrate;

	private ImageView img_logo;

	private DimenUtils dimenUtils;
	private SharedPreferences setting;
	private SharedPreferences.Editor editor;
	private FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		main = inflater.inflate(R.layout.menu, null);

		findViews();
		initValues();
		bindEvents();

		return main;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		dimenUtils = new DimenUtils(getActivity());

		btn_play = (ImageButton) main.findViewById(R.id.btn_play);
		btn_help = (ImageButton) main.findViewById(R.id.btn_help);
		btn_about = (ImageButton) main.findViewById(R.id.btn_about);

		btn_voice = (ImageButton) main.findViewById(R.id.btn_voice);
		btn_vibrate = (ImageButton) main.findViewById(R.id.btn_vibrate);

		img_logo = (ImageView) main.findViewById(R.id.img_logo);

		dimenUtils.setSizeHorizontal(btn_play, 491, 255);
		dimenUtils.setSizeHorizontal(btn_help, 508, 237);
		dimenUtils.setSizeHorizontal(btn_about, 547, 233);
		dimenUtils.setSizeHorizontal(img_logo, 500, 200);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		logger = Logger.Jlog();
		setting = getActivity().getSharedPreferences("setting", 0);
		editor = setting.edit();

		if (setting.getInt("volumn", -1) == 1) {
			btn_voice.setBackgroundResource(R.drawable.volumn);
			MainActivity.app.isSoundOn = true;
		} else if (setting.getInt("volumn", -1) == 0) {
			btn_voice.setBackgroundResource(R.drawable.mute);
			MainActivity.app.isSoundOn = false;
		} else {
			MainActivity.app.isSoundOn = true;
			editor.putInt("volumn", 1);
			editor.commit();
		}

		if (setting.getInt("vibrate", -1) == 1) {

			btn_vibrate.setBackgroundResource(R.drawable.vibrate);
			MainActivity.app.isVibrateOn = true;
		} else if (setting.getInt("vibrate", -1) == 0) {
			btn_vibrate.setBackgroundResource(R.drawable.non_vibrate);
			MainActivity.app.isVibrateOn = false;
		} else {
			MainActivity.app.isVibrateOn = true;

			editor.putInt("vibrate", 1);
			editor.commit();

		}

	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		btn_play.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		btn_about.setOnClickListener(this);
		btn_voice.setOnClickListener(this);
		btn_vibrate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_play:

			ft = getActivity().getSupportFragmentManager().beginTransaction();
			GameF gameF = new GameF();
			MainActivity.gameF = gameF;
			ft.add(R.id.layout1, gameF);
			ft.addToBackStack(null);

			ft.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
			ft.commit();

			MainActivity.app.playAudio(MemoryPreference.CLICK);

			break;

		case R.id.btn_help:
			MainActivity.app.playAudio(MemoryPreference.CLICK);

			ft = getActivity().getSupportFragmentManager().beginTransaction();
			HelpF helpF = new HelpF();
			ft.add(R.id.layout1, helpF);
			ft.addToBackStack(null);

			ft.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
			ft.commit();

			break;

		case R.id.btn_about:
			MainActivity.app.playAudio(MemoryPreference.CLICK);

			ft = getActivity().getSupportFragmentManager().beginTransaction();
			AboutF aboutF = new AboutF();
			ft.add(R.id.layout1, aboutF);
			ft.addToBackStack(null);

			ft.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
			ft.commit();

			break;
		case R.id.btn_voice:

			if (setting.getInt("volumn", -1) == 1) {
				btn_voice.setBackgroundResource(R.drawable.mute);
				editor.putInt("volumn", 0);
				editor.commit();

				MainActivity.app.isSoundOn = false;
			} else if (setting.getInt("volumn", -1) == 0) {
				btn_voice.setBackgroundResource(R.drawable.volumn);
				editor.putInt("volumn", 1);
				editor.commit();

				MainActivity.app.isSoundOn = true;
				MainActivity.app.playAudio(MemoryPreference.CLICK);
			}

			break;

		case R.id.btn_vibrate:

			if (setting.getInt("vibrate", -1) == 1) {
				btn_vibrate.setBackgroundResource(R.drawable.non_vibrate);
				editor.putInt("vibrate", 0);
				editor.commit();

				MainActivity.app.isVibrateOn = false;
			} else if (setting.getInt("vibrate", -1) == 0) {
				btn_vibrate.setBackgroundResource(R.drawable.vibrate);
				editor.putInt("vibrate", 1);
				editor.commit();

				MainActivity.app.isVibrateOn = true;
				MainActivity.app.vibrate();
			}

			MainActivity.app.playAudio(MemoryPreference.CLICK);

			break;
		}

	}

}
