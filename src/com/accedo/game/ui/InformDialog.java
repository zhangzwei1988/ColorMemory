package com.accedo.game.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.MemoryPreference;

public class InformDialog extends BaseDialog implements
		android.view.View.OnClickListener {

	Context mContext;
	DimenUtils dimenUtils;
	DialogCallBack callBack;
	int state;

	RelativeLayout layout1, layout2;
	ImageView img_indicator;
	TextView txt_message;
	ImageButton btn_close;

	Timer timer;

	public InformDialog(Context context, DialogCallBack callBack, int state) {

		super(context, R.style.ChooseDialog);

		this.mContext = context;
		this.callBack = callBack;
		this.state = state;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.informdialog);

		initValues();
		findViews();
		bindEvents();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_close:

			this.dismiss();

			break;
		}
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		btn_close = (ImageButton) findViewById(R.id.btn_close);
		img_indicator = (ImageView) findViewById(R.id.img_indicator);
		txt_message = (TextView) findViewById(R.id.txt_message);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		layout2 = (RelativeLayout) findViewById(R.id.layout2);

		txt_message.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(layout1, 732, 518);
		dimenUtils.setSizeHorizontal(img_indicator, 180, 172);
		dimenUtils.setSizeHorizontal(btn_close, 107, 107);

		dimenUtils.setMargin(btn_close, 0, 20, 15, 0);
		dimenUtils.setMargin(img_indicator, 70, 0, 0, 0);
	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		dimenUtils = new DimenUtils(mContext);
		timer = new Timer();
	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub
		btn_close.setOnClickListener(this);

		if (state == MemoryPreference.SUCCESS) {

			img_indicator.setBackgroundResource(R.drawable.success);
			txt_message.setText("Successful");
			txt_message.setTextColor(getContext().getResources().getColor(
					R.color.highlight_title));
			timer.schedule(task, 1500);

		} else if (state == MemoryPreference.TIMEOUT) {
			img_indicator.setBackgroundResource(R.drawable.fail);
			txt_message.setText("TimeOut");
			txt_message.setTextColor(getContext().getResources().getColor(
					R.color.red));
		}

	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			handler.sendEmptyMessage(MemoryPreference.SUCCESS);
		}
	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case MemoryPreference.SUCCESS:
				callBack.onUpload();
				break;
			}
		}

	};
}
