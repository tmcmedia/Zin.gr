package com.debatrrr;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ZingerAdd extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dbHelper = new DbZingerHelper(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zinger_add);
		
		commentText = (TextView) findViewById(R.id.zingerText);
		whoSaidIt= (TextView) findViewById(R.id.zingerWhoSaidIt);
//		source= (TextView) findViewById(R.id.zingerSource);
		
		Button saveCommentbtn = (Button) findViewById(R.id.zingerSaveBtn);
		saveCommentbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelper.insert(1, commentText.getText().toString(), new Date().toGMTString(),
						"mgibiec", whoSaidIt.getText().toString(), 
						"" ,//source.getText().toString(),
						0, 0, 0);
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		setResult(Activity.RESULT_OK);
	}
	private DbZingerHelper dbHelper;
	private TextView commentText;
	private TextView whoSaidIt;
	private TextView source;
}
