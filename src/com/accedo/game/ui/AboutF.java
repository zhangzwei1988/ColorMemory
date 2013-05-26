package com.accedo.game.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.MemoryPreference;

public class AboutF extends BaseFragment implements OnClickListener {

	TextView txt_design;
	TextView txt_program;
	TextView txt_art;
	TextView txt_right;

	ImageButton btn_back;
	TextView txt_title;
	RelativeLayout layout1;

	DimenUtils dimenUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		main = inflater.inflate(R.layout.about, null);

		initValues();
		findViews();
		bindEvents();

		return main;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		txt_design = (TextView) main.findViewById(R.id.txt_design);
		txt_program = (TextView) main.findViewById(R.id.txt_program);
		txt_art = (TextView) main.findViewById(R.id.txt_art);
		txt_title = (TextView) main.findViewById(R.id.txt_title);
		txt_right = (TextView) main.findViewById(R.id.txt_right);

		btn_back = (ImageButton) main.findViewById(R.id.btn_back);
		layout1 = (RelativeLayout) main.findViewById(R.id.layout1);

		txt_design.setTypeface(MainActivity.app.tf);
		txt_program.setTypeface(MainActivity.app.tf);
		txt_art.setTypeface(MainActivity.app.tf);
		txt_title.setTypeface(MainActivity.app.tf);
		txt_right.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(layout1, 0, 120);
		dimenUtils.setSizeHorizontal(btn_back, 90, 90);

		dimenUtils.setMarginVertical(txt_right, 0, 0, 0, 50);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		dimenUtils = new DimenUtils(getActivity());
	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		btn_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		MainActivity.app.playAudio(MemoryPreference.CLICK);
		getActivity().onBackPressed();

	}
}
