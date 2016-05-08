package com.project.Vishalaxi.MP3Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.project.Vishalaxi.KeyCommon.MusicPlayer;

public class ServerImpl extends Service {

	private DatabaseOpenHelper mDbHelper = new DatabaseOpenHelper(this);
	private int mCurrentSongNumber;
	static private MediaPlayer  mp=new MediaPlayer();
    final static int[] songNumberList={R.raw.song1,R.raw.song2,R.raw.song3,R.raw.song4,R.raw.song5,
									   R.raw.song6,R.raw.song7,R.raw.song8,R.raw.song9,R.raw.song10};

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar calendar;
	private boolean isStarted = false;

	// Implement the Stub for this Object
	private final MusicPlayer.Stub mBinder = new MusicPlayer.Stub() {

		/* Implement the remote methods*/

		/*Implementation of playSong() defined in AIDL file
		  It plays the specified song using the music player
		  and stores this request in the database*/
		public void  playSong(int songNumber){
			try {
					//Get the file descriptor for the currently requested songNumber and set the data source
					AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(songNumberList[songNumber - 1]);
					mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
					mp.prepare();
					mp.start();
					afd.close();

					ContentValues values = new ContentValues();
					calendar= Calendar.getInstance();
					String timeStamp=dateFormat.format(calendar.getTime());
					values.put(DatabaseOpenHelper.TIME_STAMP, timeStamp);
					values.put(DatabaseOpenHelper.REQUEST_TYPE, "Play Song");
					values.put(DatabaseOpenHelper.SONG_NUMBER, "#" + songNumber + ", ");
				    // get the REQUEST_TYPE and SONG_NUMBER of the last entry and insert it into
				    ///current state column of database after minor modifications
					String currentState=getCurrentState();
					String currentStateString=getAppropriateCurrentState(currentState);
					if(currentStateString!=null){
						values.put(DatabaseOpenHelper.CURRENT_STATE, currentStateString);
					}
					else {
						values.put(DatabaseOpenHelper.CURRENT_STATE, "#first request#");
					}

					mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
					values.clear();
			}
			catch (IOException e){
				Log.i("vish", "Exception caused in playSong setDataSource ");
			}
			isStarted = true;
			mCurrentSongNumber=songNumber;
		}

		/*Implementation of resume_or_Play_Song() defined in AIDL file
  		It resumes the old song or plays new song using the music player
  		and stores this request in the database*/
		public void resume_or_Play_Song(int songNumber) {
			// Resume a previously started song
			if (isStarted) {
					if (mCurrentSongNumber == songNumber) {
						mp.start();
						ContentValues values = new ContentValues();
						calendar= Calendar.getInstance();
						String timeStamp=dateFormat.format(calendar.getTime());
						values.put(DatabaseOpenHelper.TIME_STAMP, timeStamp);
						values.put(DatabaseOpenHelper.REQUEST_TYPE, "Resume Song" );
						values.put(DatabaseOpenHelper.SONG_NUMBER, "#" + songNumber);
						// get the REQUEST_TYPE and SONG_NUMBER of the last entry and insert it into
						///current state column of database after minor modifications
						String currentState=getCurrentState();
						String currentStateString=getAppropriateCurrentState(currentState);
						if(currentStateString!=null){
							values.put(DatabaseOpenHelper.CURRENT_STATE, currentStateString);
						}
						mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
						values.clear();
						getTransactions();

					} else {
							mp.stop();
							mp.reset();
							playSong(songNumber);
					}
			}
			// play the newly requested song
			else {
				playSong(songNumber);
			}
			mCurrentSongNumber=songNumber;
		}

		/*Implementation of playSong() defined in AIDL file
		It plays the specified song using the music player
		and stores this request in the database*/

		public void pauseSong(int songNumber) {
			//If player is playing a song then pause it
			if(mp.isPlaying()) {
					mp.pause();
					ContentValues values = new ContentValues();
					calendar= Calendar.getInstance();
					String timeStamp=dateFormat.format(calendar.getTime());
					values.put(DatabaseOpenHelper.TIME_STAMP, timeStamp);
					values.put(DatabaseOpenHelper.REQUEST_TYPE, "Pause Song" );
					values.put(DatabaseOpenHelper.SONG_NUMBER, "#" + songNumber);
					// get the REQUEST_TYPE and SONG_NUMBER of the last entry and insert it into
					//current state column of database after minor modifications
					String currentState=getCurrentState();
				    String currentStateString=getAppropriateCurrentState(currentState);
					if(currentStateString!=null){
						values.put(DatabaseOpenHelper.CURRENT_STATE, currentStateString);
					}
					mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
					values.clear();
			}
		}

		/*Implementation of stopSong() defined in AIDL file
		It stops the specified song using the music player and
		 stores this request in the database*/

		public void stopSong(int songNumber) {
			mp.stop();
			mp.reset();
			ContentValues values = new ContentValues();
			calendar= Calendar.getInstance();
			String timeStamp=dateFormat.format(calendar.getTime());
			values.put(DatabaseOpenHelper.TIME_STAMP, timeStamp);
			values.put(DatabaseOpenHelper.REQUEST_TYPE, "Stop Song" );
			values.put(DatabaseOpenHelper.SONG_NUMBER, "#" + songNumber);
			// get the REQUEST_TYPE and SONG_NUMBER of the last entry and insert it into
			//current state column of database after minor modifications
			String currentState=getCurrentState();
			String currentStateString=getAppropriateCurrentState(currentState);
			if(currentStateString!=null){
				values.put(DatabaseOpenHelper.CURRENT_STATE, currentStateString);
			}
			mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
			values.clear();
			getTransactions();
			isStarted = false;
		}

		/*Implementation of getTransactions() defined in AIDL file
          It reads the columns of database and returns all the entries presently
          stored to the client which displays all requests on clicking get transactions*/

		public List getTransactions(){
			List<String> transactionsList = new ArrayList<String>();
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM " + DatabaseOpenHelper.TABLE_NAME, null);
			if(cur.getCount() ==0){
				transactionsList.add("No Requests recorded in the database!");
				return transactionsList;
			}
			if(cur.getCount() != 0){
				cur.moveToFirst();
				do{
					String row_values = "";
					String[] tuple=new String[10];
					for(int i = 1 ; i < cur.getColumnCount(); i++){
						row_values = row_values + "  " + cur.getString(i);
						Log.d("LOG_TAG_HERE", row_values);
					}
					transactionsList.add(row_values);
				}while (cur.moveToNext());
			}
			return transactionsList;
		}
	};

	/*Implementation of Server side Utility functions*/

	/*Returns the meaningful current state information for play, pause,resume and stop requests*/
	public String getAppropriateCurrentState(String currentState){
		if(currentState==null){
			return null;
		}
		String[] parts = currentState.split(" ");
		String str=null;
		Log.i("check parts values",parts[0]+"$"+parts[1]+"$"+parts[2]);
		if(parts[0].equals("Play")){
			str=" Song " + parts[2].replace(",", "")+" was playing";

		}else if(parts[0].equals("Pause")){
			str=" Song " +parts[2].replace(",", "")+" is paused";

		}else if(parts[0].equals("Resume")){
			str=" Song "+parts[2].replace(",", "")+" had Resumed playing";
		}
		else if(parts[0].equals("Stop")){
			str=" Song " +parts[2].replace(",", "")+" is stopped";
		}
		return(" while"+str);
	}

	/*Returns the current state by getting the request type of lastly entered record in the database*/
	public String getCurrentState(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		int last_id=getHighestID();
		if(last_id==-1){
			return "Blank";
		}
		Cursor cur = db.rawQuery("SELECT * FROM " + DatabaseOpenHelper.TABLE_NAME + " WHERE _id = " + last_id, null);
		String current_state=null;
		if(cur.getCount() != 0) {
			cur.moveToFirst();
			current_state=cur.getString(2)+ " " +cur.getString(3);
		}
		return current_state;
	}

	/*Queries the database to return the ID of the lastly inserted row*/
	public int getHighestID() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		final String MY_QUERY = "SELECT last_insert_rowid()";
		Cursor cur = db.rawQuery(MY_QUERY, null);
		if(cur==null){
			return -1;
		}
		cur.moveToFirst();
		int ID = cur.getInt(0);
		cur.close();
		return ID;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		//unbindService(mServiceConnection);
		Log.i("Vishal", "ondestroy called in Audioserver");
	}

	// Return the Stub defined above
	@Override
	public IBinder onBind(Intent intent) {
		mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);
		return mBinder;
	}
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Log.i("onTaskRemoved", "called");
		Toast.makeText(getApplicationContext(), "APP KILLED", Toast.LENGTH_LONG).show(); // here your app is killed by user
		try {
			stopService(new Intent(this, this.getClass()));
			stopSelf();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			super.onTaskRemoved(rootIntent);
		}
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		Log.i("onStartCommand", "called");
		return START_NOT_STICKY;
	}

}


