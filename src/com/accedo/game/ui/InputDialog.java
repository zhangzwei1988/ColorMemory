package com.accedo.game.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accedo.game.R;
import com.accedo.game.callback.DialogCallBack;
import com.accedo.game.db.ScoreDao;
import com.accedo.game.model.Score;
import com.accedo.game.util.DimenUtils;

public class InputDialog extends BaseDialog implements
		android.view.View.OnClickListener {

	private static final String NAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";

	Context mContext;
	DimenUtils dimenUtils;
	DialogCallBack callBack;

	RelativeLayout layout1;
	TextView txt_message;
	EditText edt_username;
	String message;
	ImageButton btn_close, btn_confirm;

	ScoreDao dao;

	Pattern pattern;
	Matcher matcher;
	int score;

	public InputDialog(Context context, DialogCallBack callBack, int score) {
		super(context, R.style.ChooseDialog);

		this.mContext = context;
		this.callBack = callBack;
		this.score = score;
		dao = new ScoreDao(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.inputdialog);

		initValues();
		findViews();
		bindEvents();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (dao != null) {
			dao.closeDatabase();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_close:

			this.dismiss();

			break;
		case R.id.btn_confirm:

			if (valideName()) {

				Score recored = new Score(edt_username.getText().toString()
						.trim(), score);

				dao.insertUser(recored);

				callBack.onConfirm();
			}

			break;
		}
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

		btn_close = (ImageButton) findViewById(R.id.btn_close);
		btn_confirm = (ImageButton) findViewById(R.id.btn_confirm);
		txt_message = (TextView) findViewById(R.id.txt_message);
		edt_username = (EditText) findViewById(R.id.edt_name);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);

		txt_message.setTypeface(MainActivity.app.tf);
		edt_username.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(layout1, 732, 518);
		dimenUtils.setSizeHorizontal(btn_confirm, 212, 101);
		dimenUtils.setSizeHorizontal(btn_close, 107, 107);
		dimenUtils.setSizeHorizontal(edt_username, 535, 106);

		dimenUtils.setMargin(btn_close, 0, 20, 15, 0);

		dimenUtils.setMargin(txt_message, 0, 0, 0, 30);
		dimenUtils.setMargin(btn_confirm, 0, 0, 0, 65);
		dimenUtils.setMargin(edt_username, 0, 0, 0, 35);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		dimenUtils = new DimenUtils(mContext);
	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		btn_close.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);

	}

	public boolean valideName() {
		String name = edt_username.getText().toString();

		if (!validUserName(name)) {
			Toast.makeText(mContext, "Please input valide name",
					Toast.LENGTH_LONG).show();
			return false;
		} else {
			return true;
		}
	}

	public boolean validUserName(final String username) {
		pattern = Pattern.compile(NAME_PATTERN);
		matcher = pattern.matcher(username);
		return matcher.matches();
	}

}
