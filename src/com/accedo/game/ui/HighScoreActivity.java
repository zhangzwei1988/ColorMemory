package com.accedo.game.ui;

import java.text.ParseException;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.db.ScoreDao;
import com.accedo.game.model.Score;
import com.accedo.game.util.DimenUtils;
import com.accedo.game.util.MemoryPreference;
import com.accedo.game.util.ScoreAdapter;

public class HighScoreActivity extends BaseActivity implements OnClickListener {

	ImageButton btn_back;
	TextView txt_highscore;
	RelativeLayout layout1;

	TextView txt_rank;
	TextView txt_name;
	TextView txt_score;

	ListView scoreListView;

	ScoreAdapter adapter;
	ArrayList<Score> scoreList;

	DimenUtils dimenUtils;
	ScoreDao dao;

	DisplayMetrics dm;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.highscore);

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
	public void findViews() {
		// TODO Auto-generated method stub

		dimenUtils = new DimenUtils(this);

		btn_back = (ImageButton) findViewById(R.id.btn_back);
		txt_highscore = (TextView) findViewById(R.id.txt_highscore);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		scoreListView = (ListView) findViewById(R.id.scoreListView);

		txt_rank = (TextView) findViewById(R.id.txt_rank);
		txt_name = (TextView) findViewById(R.id.txt_name);
		txt_score = (TextView) findViewById(R.id.txt_score);

		txt_highscore.setTypeface(MainActivity.app.tf);

		dimenUtils.setSizeHorizontal(txt_rank, 220, 72);
		dimenUtils.setSizeHorizontal(txt_name, 220, 72);
		dimenUtils.setSizeHorizontal(txt_score, 220, 72);

		if (dm.widthPixels < dm.heightPixels) {
			dimenUtils.setSizeHorizontal(layout1, 0, 120);
			dimenUtils.setSizeHorizontal(btn_back, 90, 90);
		} else {
			dimenUtils.setSizeVertical(layout1, 0, 180);
			dimenUtils.setSizeVertical(btn_back, 140, 140);
		}

		txt_rank.setTypeface(MainActivity.app.tf);
		txt_name.setTypeface(MainActivity.app.tf);
		txt_score.setTypeface(MainActivity.app.tf);

	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub

		dm = new DisplayMetrics();

		adapter = new ScoreAdapter(this);
		dao = new ScoreDao(this);
		getWindowManager().getDefaultDisplay().getMetrics(dm);

	}

	@Override
	public void bindEvents() {
		// TODO Auto-generated method stub

		btn_back.setOnClickListener(this);
		txt_highscore.setText(R.string.txt_title_highscore);

		txt_name.setText(R.string.txt_user_name);
		txt_rank.setText(R.string.txt_rank);
		txt_score.setText(R.string.txt_user_score);

		try {
			scoreList = dao.queryScore();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if database is empty,then display these init data;
		if (scoreList == null) {

			scoreList = new ArrayList<Score>();

			scoreList.add(new Score("Jerry", 12));
			scoreList.add(new Score("Tom", 10));
			scoreList.add(new Score("Tman", 8));
			scoreList.add(new Score("Yancy", 7));
			scoreList.add(new Score("Ken", 5));
		}

		if (scoreList != null && scoreList.size() > 0) {
			adapter.setScore(scoreList);
			scoreListView.setAdapter(adapter);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		MainActivity.app.playAudio(MemoryPreference.CLICK);

		switch (v.getId()) {
		case R.id.btn_back:

			onBackPressed();

			break;

		default:
			break;
		}

	}

}
