package com.voca.vocable.activity;

import com.voca.vocable.R;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class VocaMainActivity
  extends VocaActivity
{
  private Button mCompletionBtn;
  private SQLiteDatabase mDatabase;
  private Button mFoundationBtn;
  private ImageButton mHeaderBtn;
  
  View.OnClickListener mHeaderBtnListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView) {}
  };
  
  private ImageButton mHomePageBtn;
  private Button mIntroductionBtn;
  
  View.OnClickListener mMainBtnListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      int i = ((Integer)paramAnonymousView.getTag()).intValue();
      Intent localIntent = new Intent(VocaMainActivity.this, VocaLevelActivity.class);
      localIntent.putExtra("level", i);
      VocaMainActivity.this.toActivity(localIntent);
    }
  };
  
  private ImageButton mSearchBtn;
  
  View.OnClickListener mSearchBtnListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Intent localIntent = new Intent(VocaMainActivity.this, VocaSearchActivity.class);
      VocaMainActivity.this.toActivity(localIntent);
    }
  };
  
  private void init()
  {
    try
    {
      this.mHeaderBtn = ((ImageButton)findViewById(R.id.header_btn));
      this.mCompletionBtn = ((Button)findViewById(R.id.c_btn));
      this.mIntroductionBtn = ((Button)findViewById(R.id.i_btn));
      this.mFoundationBtn = ((Button)findViewById(R.id.f_btn));
      this.mHomePageBtn = ((ImageButton)findViewById(R.id.home_btn));
      this.mSearchBtn = ((ImageButton)findViewById(R.id.search_btn));
      
      this.mFoundationBtn.setTag(Integer.valueOf(1));
      this.mIntroductionBtn.setTag(Integer.valueOf(2));
      this.mCompletionBtn.setTag(Integer.valueOf(3));
      
      this.mHomePageBtn.setTag(getResources().getString(R.string.homepage_url));
      
      this.mFoundationBtn.setOnClickListener(this.mMainBtnListener);
      this.mIntroductionBtn.setOnClickListener(this.mMainBtnListener);
      this.mCompletionBtn.setOnClickListener(this.mMainBtnListener);
      this.mHomePageBtn.setOnClickListener(this.mWebStieListener);
      this.mSearchBtn.setOnClickListener(this.mSearchBtnListener);
    }
    catch (Exception localException)
    {
        localException.printStackTrace();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.voca_main);
    init();
    initHeader();
  }
  
}
