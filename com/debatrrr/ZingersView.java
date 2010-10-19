package com.debatrrr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ZingersView extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	private final int ADD_ZINGER = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sayWhat = (TextView) findViewById(R.id.zingersViewSayWhat);
		zingers = (ListView) findViewById(R.id.zingersViewAllEntries);
		dbHelperZingers = new DbZingerHelper(this);
		dbHelperSayWhat = new DbWtfHelper(this);
		
		
		setContentView(R.layout.zingers_view);
        
        sayWhatId = 1;
        cursor = dbHelperZingers.getAllForSayWhat(sayWhatId);
        
        startManagingCursor(cursor);
		adapter = new ZingerAdapter(this, cursor);
		zingers = (ListView) findViewById(R.id.zingersViewAllEntries);
        zingers.setAdapter(adapter);
        
        
		
	}
	 @Override
	    public void onDestroy(){
		 	super.onDestroy();
	    	cursor.close();
	    }
	 
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.zingers_menu, menu);
	        return true;
	    }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.postNewZinger:
	        	Intent i = new Intent(ZingersView.this, ZingerAdd.class);
	            startActivityForResult(i, ADD_ZINGER);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	private int sayWhatId;
	private TextView sayWhat;
	private ListView zingers;
	private DbZingerHelper dbHelperZingers;
	private DbWtfHelper dbHelperSayWhat;
	private Cursor cursor;
	private ListAdapter adapter;
	
	
	
class ZingerAdapter extends CursorAdapter{
    	
		public ZingerAdapter(Context context, Cursor c) {
			super(context, c);
		}
	
		@Override
		public void bindView(View row, Context context, Cursor cursor) {
			ZingerHolder holder = (ZingerHolder) row.getTag();
			holder.populateForm(cursor, dbHelperZingers);
		}
	
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			ZingerHolder holder = new ZingerHolder(row);
			
			row.setTag(holder);
			return row;
		}
	}
	class ZingerHolder {
		
		private TextView text = null;
		private TextView datePosted = null;
		private TextView whoSaidIt = null;
		private TextView user = null;
		private View row = null;
		private TextView id = null;
		
		public ZingerHolder(View row){
			this.row = row;
			text= (TextView) this.row.findViewById(R.id.commentTextInRow);
			datePosted = (TextView) this.row.findViewById(R.id.datePostedInRow);
			whoSaidIt = (TextView) this.row.findViewById(R.id.whoSaidItInRow);
			user = (TextView) this.row.findViewById(R.id.userInRow);
			id = (TextView) this.row.findViewById(R.id.idInRow);
		}
		void populateForm(Cursor c, DbZingerHelper helper){
			TextView idInRow = (TextView) this.row.findViewById(R.id.idInRow);
			idInRow.setBackgroundResource(R.drawable.zinger32x480);
			text.setText(helper.getText(c));
			datePosted.setText(helper.getDatePosted(c));
			whoSaidIt.setText(helper.getWhoSaidIt(c));
			user.setText(helper.getUserId(c));
			id.setText(helper.getId(c) + "");
//			ImageView image = (ImageView) findViewById(R.id.iconInRow);
//			image.setScaleType(ScaleType.FIT_START);
			
		}
	}
}
