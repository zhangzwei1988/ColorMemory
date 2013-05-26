package com.accedo.game.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.accedo.game.util.Logger;

/**
 * Helper class for Score.db
 * 
 * @ClassName: DatabaseHelper
 * @Description: TODO()
 * @author zzw
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 1;// DataBase Version
	private final static String DATABASE_NAME = "Score.db";// Database Name
	Logger logger;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DatabaseHelper(Context context, String name) {
		this(context, name, null, DATABASE_VERSION);
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		logger = Logger.Jlog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createDataBase(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Delete the table
		String sql = "drop table if exists [Score]";
		db.execSQL(sql);

		// Create the table
		onCreate(db);
	}

	public Cursor execSQL(String sql) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	private void createDataBase(SQLiteDatabase db) {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE [Score](");
		builder.append("[id] INTEGER PRIMARY KEY AUTOINCREMENT,");
		builder.append("[username] VARCHAR(32) NOT NULL,");
		builder.append("[date] datetime,");
		builder.append("[score] INTEGER NOT NULL)");
		db.execSQL(builder.toString());

		builder = null;
		System.gc();
	}

}
