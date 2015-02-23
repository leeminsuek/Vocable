package com.voca.vocable;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.voca.vocable.activity.VocaMainActivity;
import com.voca.vocable.db.VocaDatabaseAdapter;

public class Vocable extends Activity {
	/**
	 * 메인액티비티 띄우기
	 */
	private void toMainActivity() {
		startActivity(new Intent(this, VocaMainActivity.class));
		finish();
		overridePendingTransition(R.anim.alpha, R.anim.alpha2);
	}

	public void onBackPressed() {}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_vocable);
		AsyncTask<Void, Void, Void> sql = new AsyncTask<Void, Void, Void>()
				{
			protected Void doInBackground(Void... paramAnonymousVarArgs)
			{
				VocaDatabaseAdapter localVocaDatabaseAdapter = new VocaDatabaseAdapter(Vocable.this);
				try
				{
					localVocaDatabaseAdapter.open();
					return null;
				}
				catch (SQLException localSQLException)
				{
						localSQLException.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Void paramAnonymousVoid)
			{
				super.onPostExecute(paramAnonymousVoid);
				Vocable.this.runOnUiThread(new Runnable()
				{
					public void run()
					{
						
						//디비정보를 받았으면 
						if (VocaDatabaseAdapter.CREATE_YN) {
							Vocable.this.toMainActivity();
						}
						//디비정보가 이미 한번 만들어졌으면 2.5초뒤에 메인화면으로
						else {
							new Handler().postDelayed(new Runnable()
							{
								public void run()
								{
									Vocable.this.toMainActivity();
								}
							}, 2500L);
						}
					}
				});
			}
				
		}.execute();
	}
}
