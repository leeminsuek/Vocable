package com.voca.vocable.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.voca.vocable.R;
import com.voca.vocable.VocaCommon;

@SuppressLint("NewApi")
public class VocaLevelActivity extends VocaActivity {


	private ImageButton mLevelBtn;

	private ImageButton mLevelBtn1;
	private ImageButton mLevelBtn2;
	private ImageButton mLevelBtn3;
	
	private int mLevel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voca_level);
		try {
			init();
			initHeader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 컴포넌트 초기화 및 리스너 연결
	 */
	private void init() {

		try {
			//레벨 가져오기
			Intent intent = getIntent();
			mLevel = intent.getExtras().getInt("level");

			mLevelBtn = (ImageButton) findViewById(R.id.level_btn);
			mLevelBtn1 = (ImageButton) findViewById(R.id.level_btn1);
			mLevelBtn2 = (ImageButton) findViewById(R.id.level_btn2);
			mLevelBtn3 = (ImageButton) findViewById(R.id.level_btn3);
			
			mLevelBtn1.setTag(1);
			mLevelBtn2.setTag(2);
			mLevelBtn3.setTag(3);
			
			mLevelBtn1.setOnClickListener(mGubunBtnListener);
			mLevelBtn2.setOnClickListener(mGubunBtnListener);
			mLevelBtn3.setOnClickListener(mGubunBtnListener);
			

			mLevelBtn.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), VocaCommon.getInstance().mResourceArray.get(mLevel-1).getRecousrceImage())));
			mLevelBtn1.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), VocaCommon.getInstance().mResourceArray.get(mLevel-1).getBtnImg1())));
			mLevelBtn2.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), VocaCommon.getInstance().mResourceArray.get(mLevel-1).getBtnImg2())));
			mLevelBtn3.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), VocaCommon.getInstance().mResourceArray.get(mLevel-1).getBtnImg3())));
//			mLevelBtn.setText(mResourceArray.get(mLevel-1).getRecourceString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 구분 버튼 리스너
	 * tag value 1 = vocabulary, 2 = self test, 3 = my vocab
	 */
	OnClickListener mGubunBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			
			Intent i = new Intent(VocaLevelActivity.this, VocaDayActivity.class);
			i.putExtra("level", mLevel);
			i.putExtra("gubun", tag);
			toActivity(i);
		}
	};

}
