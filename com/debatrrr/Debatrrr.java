package com.debatrrr;

import java.util.Properties;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.signature.HmacSha1MessageSigner;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Debatrrr extends Activity {
	
	private static final String APP = 	"OAUTH";

	private OAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;
	
	protected static final int CONTEXTMENU_ILIKEIT = 0;
	protected static final int CONTEXTMENU_VIEWZINGERS = 1;

	private String CONSUMER_KEY = 		"put it here";
	private String CONSUMER_SECRET = 	"put it here";
	private String CALLBACK_URL = 		"Debatrrr://oauth";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbHelper = new DbWtfHelper(this);
        new DbZingerHelper(this);
        cursor = dbHelper.getAll();
        
        startManagingCursor(cursor);
		adapter = new SayWhatAdapter(this, cursor);
		lvAllEntires = (ListView) findViewById(R.id.listAllEntries);
        lvAllEntires.setAdapter(adapter);
        
        initListWithContextMenu();
    }
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	cursor.close();
    	
    }
    
    ListView lvAllEntires;
    private void initListWithContextMenu() {
    	lvAllEntires.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("More actions");
				menu.add(0, CONTEXTMENU_ILIKEIT, 0, "I like it!");
				menu.add(0, CONTEXTMENU_VIEWZINGERS, 1, "View Zingers");
				
			}
		});
		
	}
    @Override
    public boolean onContextItemSelected(MenuItem item){
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
    	
        switch (item.getItemId()) {
        	
             case CONTEXTMENU_ILIKEIT:
            	 Log.e("mgibiec", "I LIKE IT!");
            	 Toast.makeText(getApplicationContext(), "Yeah! We like it too!", Toast.LENGTH_LONG);
                 return true;
             case CONTEXTMENU_VIEWZINGERS:
            	 Log.e("mgibiec", "VIEW ZINGERS");
            	 String s = ((TextView) menuInfo.targetView.findViewById(R.id.idInRow)).getText().toString();
            	 Log.e("mgibiec", s);
            	 startActivity(new Intent(Debatrrr.this, ZingersView.class));
            	 
            	 
        }
        return false;
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comments_menu, menu);
        	
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.postNewSayWhat:
            startActivityForResult(new Intent(Debatrrr.this, WtfAdd.class), ADD_CONTACT_REQ);
            return true;
        case R.id.enableTwitter:
        	enableTwitter();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == ADD_CONTACT_REQ) {
            if (resultCode == RESULT_OK) {
                cursor.requery();
            }
        }
    }
    private void enableTwitter(){
    	
    	Properties systemSettings = System.getProperties();
		systemSettings.put("https.proxyHost", "proxy");
		systemSettings.put("https.proxyPort", "8080");
		System.setProperties(systemSettings);
    	
    	CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(  
    	        CONSUMER_KEY, CONSUMER_SECRET);  
    	  
    	OAuthProvider provider = new DefaultOAuthProvider( "http://twitter.com/oauth/request_token", 
    			"http://twitter.com/oauth/access_token", "http://twitter.com/oauth/authorize");  
    	
    	consumer.setMessageSigner(new HmacSha1MessageSigner());
    	HttpClient client = new DefaultHttpClient();  
    	
    	Context ctx = this;
    	// Context ctx whichever way you passing it in  
    	String authUrl;
		try {
			authUrl = provider.retrieveRequestToken(consumer, CALLBACK_URL);
			ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
	}
    /**
	 * Open the browser and asks the user to authorize the app.
	 * Afterwards, we redirect the user back here!
	 */
	private void askOAuth() {
		try {
			consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			provider = new DefaultOAuthProvider("http://twitter.com/oauth/request_token",
												"http://twitter.com/oauth/access_token",
												"http://twitter.com/oauth/authorize");
			String authUrl = provider.retrieveRequestToken(consumer, CALLBACK_URL);
			Toast.makeText(this, "Please authorize this app!", Toast.LENGTH_LONG).show();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (Exception e) {
			Log.e(APP, e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
    
    private DbWtfHelper dbHelper;
    static final int ADD_CONTACT_REQ = 100;
    Cursor cursor;
    ListAdapter adapter;
    class SayWhatAdapter extends CursorAdapter{
    	
		public SayWhatAdapter(Context context, Cursor c) {
			super(context, c);
		}
	
		@Override
		public void bindView(View row, Context context, Cursor cursor) {
			SayWhayHolder holder = (SayWhayHolder) row.getTag();
			holder.populateForm(cursor, dbHelper);
		}
	
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			SayWhayHolder holder = new SayWhayHolder(row);
			
			row.setTag(holder);
			return row;
		}
	}
	class SayWhayHolder {
		
		private TextView text = null;
		private TextView datePosted = null;
		private TextView whoSaidIt = null;
		private TextView user = null;
		private View row = null;
		private TextView id = null;
		
		public SayWhayHolder(View row){
			this.row = row;
			text= (TextView) this.row.findViewById(R.id.commentTextInRow);
			datePosted = (TextView) this.row.findViewById(R.id.datePostedInRow);
			whoSaidIt = (TextView) this.row.findViewById(R.id.whoSaidItInRow);
			user = (TextView) this.row.findViewById(R.id.userInRow);
			id = (TextView) this.row.findViewById(R.id.idInRow);
		}
		void populateForm(Cursor c, DbWtfHelper helper){
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