package com.accedo.game.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.accedo.game.model.Score;
import com.accedo.game.util.Logger;

/**
 * The opeartion class for Score.db
 * 
 * @ClassName: ScoreDao
 * @Description: TODO()
 * @author zzw
 */
public class ScoreDao {

	static final String TABLE_NAME = "Score";
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;

	SimpleDateFormat format;
	Logger logger;

	public ScoreDao(Context context) {
		super();
		// TODO Auto-generated constructor stub

		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(context);
		}

		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger = Logger.Jlog();
	}

	public Score queryScore(Score score) throws ParseException {
		Score scoreLast = null;
		database = dbHelper.getReadableDatabase();

		Cursor c = database.rawQuery(
				"select * from Score where username = ? COLLATE NOCASE;",
				new String[] { score.getName() });

		if (c != null && c.getCount() > 0) {

			scoreLast = new Score();
			c.moveToFirst();

			scoreLast.setId(getColumnInt(c, "id"));
			scoreLast.setName(getColumn(c, "username"));
			scoreLast.setScore(getColumnInt(c, "score"));

			c.close();

			return scoreLast;

		} else {
			return null;
		}
	}

	public ArrayList<Score> queryScore() throws ParseException {
		ArrayList<Score> scoreList = new ArrayList<Score>();
		database = dbHelper.getReadableDatabase();

		Cursor c = database.rawQuery(
				"select * from Score order by score desc;", null);

		if (c != null && c.getCount() > 0) {

			for (int i = 0; i < c.getCount(); i++) {

				Score score = new Score();
				c.moveToPosition(i);

				score.setId(getColumnInt(c, "id"));
				score.setName(getColumn(c, "username"));
				score.setScore(getColumnInt(c, "score"));

				scoreList.add(score);
			}

			c.close();

			return scoreList;

		} else {
			return null;
		}
	}

	public void insertUser(Score score) {
		try {
			database = dbHelper.getWritableDatabase();

			Score lastScore = queryScore(score);
			if (lastScore != null && lastScore.getScore() < score.getScore()) {
				updateScore(score, lastScore.getId());
			} else {
				StringBuilder builder = new StringBuilder();
				builder.append("insert into Score(username,date,score) values('");
				builder.append(score.getName()).append("','");
				builder.append(format.format(new Date())).append("','");
				builder.append(score.getScore()).append("')");

				database.execSQL(builder.toString());
			}

		} catch (Exception e) {
			Log.v("error", "Exception" + e.getMessage());
		}

	}

	public boolean updateScore(Score score, int id) {
		try {
			database = dbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("username", score.getName());
			cv.put("score", score.getScore());

			String selection = " id=? ";
			String[] args = { String.valueOf(id) };

			return database.update("Score", cv, selection, args) > 0;

		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", "Exception" + e.getMessage());
			return false;
		}
	}

	public String getColumn(Cursor c, String column) {
		if (c.getColumnIndex(column) != -1) {
			return c.getString(c.getColumnIndex(column));
		} else {
			return null;
		}
	}

	public int getColumnInt(Cursor c, String column) {
		if (c.getColumnIndex(column) != -1) {
			return c.getInt(c.getColumnIndex(column));
		} else {
			return -1;
		}
	}

	public Date getColumnDate(Cursor c, String column) throws ParseException {

		Date date = null;
		if (c.getColumnIndex(column) != -1) {

			if (c.getString(c.getColumnIndex(column)) == null
					|| c.getString(c.getColumnIndex(column)).equals("")) {
				return null;
			}

			else {
				date = format.parse(c.getString(c.getColumnIndex(column)));
			}

			return date;
		} else {
			return null;
		}
	}

	public void closeDatabase() {
		if (database != null) {
			database.close();
		}
	}

}
