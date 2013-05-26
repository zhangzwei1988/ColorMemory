package com.accedo.game.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accedo.game.R;
import com.accedo.game.model.Score;
import com.accedo.game.ui.MainActivity;

/**
 * The high socres adapter
 * 
 * @ClassName: ScoreAdapter
 * @Description: TODO()
 * @author zzw
 * @date 2013-5-6 下午10:57:09
 */
public class ScoreAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private ArrayList<Score> scoreList;

	public ScoreAdapter(Context mContext) {
		super();
		mInflater = LayoutInflater.from(mContext);
	}

	public void setScore(ArrayList<Score> list) {
		scoreList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return scoreList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return scoreList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		Holder holder;

		if (arg1 == null) {

			if (arg0 == 0) {
				arg1 = mInflater.inflate(R.layout.score_item1, null);

				holder = getHolder(arg1);
				holder.isFirstLine = true;
			} else {
				arg1 = mInflater.inflate(R.layout.score_item2, null);
				holder = getHolder(arg1);
				holder.isFirstLine = false;
			}

			arg1.setTag(holder);
		}

		else {

			/**
			 * Valide the item,sometimes disordered
			 */
			if (arg0 == 0) {
				if (((Holder) arg1.getTag()).isFirstLine == true) {
					holder = (Holder) arg1.getTag();
				} else {
					arg1 = mInflater.inflate(R.layout.score_item1, null);
					holder = getHolder(arg1);
					holder.isFirstLine = true;

					arg1.setTag(holder);
				}
			} else {
				if (((Holder) arg1.getTag()).isFirstLine == false) {
					holder = (Holder) arg1.getTag();
				} else {
					arg1 = mInflater.inflate(R.layout.score_item2, null);
					holder = getHolder(arg1);
					holder.isFirstLine = false;
					arg1.setTag(holder);
				}
			}
		}

		setHolder(arg0, holder);

		return arg1;
	}

	static class Holder {
		TextView txtRank;
		TextView txtName;
		TextView txtScore;
		boolean isFirstLine;
	}

	/**
	 * Bind the view to the holder
	 * 
	 * @Title: getHolder
	 * @Description: TODO()
	 * @param @param view
	 * @param @return
	 * @return Holder
	 * @throws
	 */
	public Holder getHolder(View view) {
		Holder holder = new Holder();

		holder.txtRank = (TextView) view.findViewById(R.id.txt_rank);
		holder.txtName = (TextView) view.findViewById(R.id.txt_name);
		holder.txtScore = (TextView) view.findViewById(R.id.txt_score);

		holder.txtRank.setTypeface(MainActivity.app.tf);
		holder.txtName.setTypeface(MainActivity.app.tf);
		holder.txtScore.setTypeface(MainActivity.app.tf);

		return holder;
	}

	/**
	 * assignment data to the view
	 * 
	 * @Title: setHolder
	 * @Description: TODO()
	 * @param @param position
	 * @param @param holder
	 * @return void
	 * @throws
	 */
	public void setHolder(int position, Holder holder) {
		holder.txtRank.setText(String.valueOf(position + 1));
		holder.txtName.setText(scoreList.get(position).getName());
		holder.txtScore.setText(String.valueOf(scoreList.get(position)
				.getScore()));
	}

}
