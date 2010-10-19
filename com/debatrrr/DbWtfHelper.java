package com.debatrrr;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbWtfHelper extends SQLiteOpenHelper {
	
	private final static String DB_NAME = "Zingr.db";
	private final static int SCHEMA_VERSION = 10;

	public DbWtfHelper(Context context){
		super(context, DB_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("mgibiec", "onCreate in DbWtfHelper");
		db.execSQL("CREATE TABLE Wtfs (_id INTEGER PRIMARY KEY AUTOINCREMENT, text VARCHAR(120), datePosted DATETIME, user VARCHAR(20), whoSaidIt VARCHAR(200));");
		
		db.execSQL("CREATE TABLE Zingers (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"sayWhatId INTEGER, " +
				"text VARCHAR(120), " +
				"datePosted DATETIME, " +
				"user VARCHAR(20), " +
				"whoSaidIt VARCHAR(200)," +
				"source VARCHAR(200)," +
				"likesCount INTEGER," +
				"sharesCount INTEGER," +
				"flaggedCount INTEGER);");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE Wtfs");
		db.execSQL("DROP TABLE Zingers");
		onCreate(db);
	}
	public void insert(String text, String datePosted, String user, String whoSaidIt){
		ContentValues values = new ContentValues(4);
		values.put("text", text);
		values.put("datePosted", datePosted);
		values.put("user", user);
		values.put("whoSaidIt", whoSaidIt);
		
		long sqlCode = getWritableDatabase().insert("Wtfs", "Test", values);
		if(sqlCode != -1)
			Log.i("mgibiec", "Comment saved. ID= " + sqlCode);
		else
			Log.i("mgibiec", "FAILED");
	}
	public void like(int id){
		
	}
	public Cursor getAll(){
		return getReadableDatabase().rawQuery("SELECT * FROM Wtfs ORDER BY datePosted", null);
	}
	public int getId(Cursor c){
		return c.getInt(0);
	}
	public String getText(Cursor c){
		return c.getString(1);
	}
	public String getDatePosted(Cursor c){
		return c.getString(2);
	}
	public String getUserId(Cursor c){
		return c.getString(3);
	}
	public String getWhoSaidIt(Cursor c){
		return c.getString(4);
	}
}




