package com.project.Vishalaxi.MP3Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	
	final static String TABLE_NAME = "player_requests";
	final static String TIME_STAMP="time";
	final static String REQUEST_TYPE = "request_type";
	final static String SONG_NUMBER = "song_no";
	final static String CURRENT_STATE="current_state";

	final static String _ID = "_id";
	final static String[] columns = { _ID, TIME_STAMP,REQUEST_TYPE,SONG_NUMBER,CURRENT_STATE };

	final private static String CREATE_CMD =

	"CREATE TABLE player_requests (" + _ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TIME_STAMP + " TEXT NOT NULL,"
			+ REQUEST_TYPE + " TEXT NOT NULL,"
			+ SONG_NUMBER + " TEXT NOT NULL,"
			+ CURRENT_STATE + " TEXT NOT NULL)";

	final private static String NAME = "player_db";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseOpenHelper(Context context) {
		super(context, NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CMD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	void deleteDatabase() {
		mContext.deleteDatabase(NAME);
	}


}
