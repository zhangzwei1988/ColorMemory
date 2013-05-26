package com.accedo.game.ui;

import com.accedo.game.R;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.MemoryPreference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpF extends BaseFragment implements OnClickListener {

	TextView txt_help_item1;
	TextView txt_help_item2;
	TextView txt_help_item3;

	ImageButton btn_back;
	TextView txt_title;
	RelativeLayout layout1;

	DimenUtils dimenUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		main = inflater.inflate(R.layout.help, null);

		initValues();
		findViews();
		bindEvents();

		return main;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		txt_help_item1 = (TextView) main.findViewById(R.id.txt_item1);
		txt_help_item2 = (TextView) main.findViewById(R.id.txt_item2);
		txt_help_item3 = (TextView) main.findViewById(R.id.txt_item3);
		txt_title = (TextView) main.findViewById(R.id.txt_title);

		btn_back = (ImageButton) main.findViewById(R.id.btn_back);
		layout1 = (RelativeLayout) main.findViewById(R.id.layout1);

		txt_help_item1.setTypeface(MainActivity.app.tf);
		txt_help_item2.setTypeface(MainActivity.app.tf);
		txt_help_item3.setTypeface(MainActivity.app.tf);
		txt_title.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(layout1, 0, 120);
		dimenUtils.setSizeHorizontal(btn_back, 90, 90);

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
