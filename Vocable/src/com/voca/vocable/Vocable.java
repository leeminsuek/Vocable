package com.voca.vocable;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.voca.vocable.activity.VocaMainActivity;
import com.voca.vocable.db.VocaDatabaseAdapter;

public class Vocable
extends Activity
{
	private void toMainActivity()
	{
		startActivity(new Intent(this, VocaMainActivity.class));
		finish();
		overridePendingTransition(2130968576, 2130968577);
	}

	public void onBackPressed() {}

	protected void onCreate(Bundle paramBundle)
	{
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
					for (;;)
					{
						localSQLException.printStackTrace();
					}
				}
			}

			protected void onPostExecute(Void paramAnonymousVoid)
			{
				super.onPostExecute(paramAnonymousVoid);
				Vocable.this.runOnUiThread(new Runnable()
				{
					public void run()
					{
						if (VocaDatabaseAdapter.CREATE_YN) {
							Vocable.this.toMainActivity();
						}
						else
						{
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
