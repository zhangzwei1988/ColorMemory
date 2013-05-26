package com.accedo.game.ui;

import com.accedo.game.R;
import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.util.DimenUtils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChooseDialog extends BaseDialog implements
		android.view.View.OnClickListener {

	Context mContext;
	DimenUtils dimenUtils;
	DialogCallBack callBack;

	RelativeLayout layout1;
	LinearLayout layout2;
	ImageButton btn_close, btn_yes, btn_no;
	TextView txt_message;

	String message;

	public ChooseDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ChooseDialog(Context context, DialogCallBack callBack, String msg) {
		super(context, R.style.ChooseDialog);

		this.mContext = context;
		this.callBack = callBack;
		this.message = msg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.choosedialog);

		initValues();
		findViews();
		bindEvents();
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		btn_close = (ImageButton) findViewById(R.id.btn_close);
		btn_yes = (ImageButton) findViewById(R.id.btn_yes);
		btn_no = (ImageButton) findViewById(R.id.btn_no);
		txt_message = (TextView) findViewById(R.id.txt_message);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);

		txt_message.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(layout1, 732, 518);
		dimenUtils.setSizeHorizontal(btn_yes, 212, 101);
		dimenUtils.setSizeHorizontal(btn_no, 212, 101);
		dimenUtils.setSizeHorizontal(btn_close, 107, 107);

		dimenUtils.setMargin(btn_close, 0, 20, 15, 0);
		dimenUtils.setMargin(layout2, 0, 0, 0, 80);
		dimenUtils.setMargin(txt_message, 0, 0, 0, 90);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		dimenUtils = new DimenUtils(mContext);

	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		txt_message.setText(message);

		btn_close.setOnClickListener(this);
		btn_yes.setOnClickListener(this);
		btn_no.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_close:

			this.dismiss();

			break;
		case R.id.btn_yes:

			callBack.onPositive();

			break;
		case R.id.btn_no:

			this.dismiss();

			break;
		}

	}

}
