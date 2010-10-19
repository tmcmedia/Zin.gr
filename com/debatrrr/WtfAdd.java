package com.debatrrr;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WtfAdd extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dbHelper = new DbWtfHelper(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wtf_add);
		
		commentText = (TextView) findViewById(R.id.wtfText);
		whoSaidIt= (TextView) findViewById(R.id.wtfWhoSaidIt);
		Button saveCommentbtn = (Button) findViewById(R.id.wtfSaveBtn);
		saveCommentbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelper.insert(commentText.getText().toString(), new Date().toGMTString(),
						"mgibiec", whoSaidIt.getText().toString());
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		setResult(Activity.RESULT_OK);
	}
	private DbWtfHelper dbHelper;
	private TextView commentText;
	private TextView whoSaidIt;
}
