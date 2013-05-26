package com.accedo.game.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.Logger;

public class SplashF extends BaseFragment {

	Timer timer;
	ImageView img_logo;
	TextView txt_right;

	FragmentTransaction ft;

	DimenUtils dimenUtils;
	Animation animation1, animation2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		main = inflater.inflate(R.layout.splash, null);

		initValues();
		findViews();
		bindEvents();

		return main;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		txt_right = (TextView) main.findViewById(R.id.txt_right);
		img_logo = (ImageView) main.findViewById(R.id.img_logo);
		timer = new Timer();

		txt_right.setTypeface(MainActivity.app.tf);
		dimenUtils.setMarginVertical(txt_right, 0, 0, 0, 50);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		dimenUtils = new DimenUtils(getActivity());
		logger = Logger.Jlog();

	}

	@SuppressLint("NewApi")
	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		// timer.schedule(task, 3000);

		animation1 = AnimationUtils
				.loadAnimation(getActivity(), R.anim.action1);
		animation1.setFillAfter(true);

		animation2 = AnimationUtils
				.loadAnimation(getActivity(), R.anim.action2);

		animation1.setAnimationListener(animationListener);
		animation2.setAnimationListener(animationListener);

		img_logo.startAnimation(animation1);

	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			MenuF menuF = new MenuF();
			ft.replace(R.id.layout1, menuF);

			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	};

	AnimationListener animationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

			if (animation.equals(animation1)) {
				img_logo.setVisibility(View.GONE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub

			if (animation.equals(animation1)) {

				main.startAnimation(animation2);

			} else if (animation.equals(animation2)) {
				ft = getActivity().getSupportFragmentManager()
						.beginTransaction();
				MenuF menuF = new MenuF();
				ft.replace(R.id.layout1, menuF);

				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}

		}
	};

}
