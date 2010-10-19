package com.debatrrr;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbZingerHelper extends SQLiteOpenHelper {
	
	private final static String DB_NAME = "Zingr.db";
	private final static int SCHEMA_VERSION = 10;

	public DbZingerHelper(Context context){
		super(context, DB_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("mgibiec", "onCreate in DbZingerHelper");
		
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
		db.execSQL("DROP TABLE Zingers");
		onCreate(db);
	}
	public void insert(int sayWhatId, String text, String datePosted, String user, String whoSaidIt, 
			String source, int likesCount, int sharesCount, int flaggedCount){
		ContentValues values = new ContentValues(4);
		values.put("sayWhatId", sayWhatId);
		values.put("text", text);
		values.put("datePosted", datePosted);
		values.put("user", user);
		values.put("whoSaidIt", whoSaidIt);
		values.put("source", source);
		values.put("likesCount", likesCount);
		values.put("sharesCount", sharesCount);
		values.put("flaggedCount", flaggedCount);
		
		
		long sqlCode = getWritableDatabase().insert("Zingers", "Test", values);
		if(sqlCode != -1)
			Log.i("mgibiec", "Zinger saved. ID= " + sqlCode);
		else
			Log.i("mgibiec", "FAILED");
	}
	public Cursor getAll(){
		return getReadableDatabase().rawQuery("SELECT * FROM Zingers ORDER BY datePosted", null);
	}
	public Cursor getAllForSayWhat(int sayWhatId){
		return getReadableDatabase().rawQuery("SELECT * FROM Zingers where sayWhatId like " + sayWhatId + " ORDER BY datePosted", null);
	}
	public int getId(Cursor c){
		return c.getInt(0);
	}
	public int getSayWhayId(Cursor c){
		return c.getInt(1);
	}
	public String getText(Cursor c){
		return c.getString(2);
	}
	public String getDatePosted(Cursor c){
		return c.getString(3);
	}
	public String getUserId(Cursor c){
		return c.getString(4);
	}
	public String getWhoSaidIt(Cursor c){
		return c.getString(5);
	}
	public String getSource(Cursor c){
		return c.getString(6);
	}
	public int getLikesCount(Cursor c){
		return c.getInt(7);
	}
	public int getSharesCount(Cursor c){
		return c.getInt(8);
	}
	public int getFlaggedCount(Cursor c){
		return c.getInt(9);
	}
}




