package com.voca.vocable.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.voca.vocable.R;
import com.voca.vocable.VocaCommon;
import com.voca.vocable.VocaCommon.onDialogClick;
import com.voca.vocable.VocaDayButton;
import com.voca.vocable.db.VocaDatabaseAdapter;
import com.voca.vocable.vo.VocaNote;

@SuppressLint("NewApi")
public class VocaDayActivity extends VocaActivity {

	private ImageButton mLevelBtn;
	private ImageButton mChooseBtn;
	private VocaDayButton mBtn1,mBtn2,mBtn3,mBtn4,mBtn5,mBtn6,mBtn7,mBtn8,mBtn9,mBtn10,mBtn11,mBtn12,mBtn13,mBtn14,mBtn15,mBtn16,mBtn17,mBtn18,mBtn19,mBtn20;
	private ArrayList<VocaDayButton> mBtnArray;
	private ArrayList<VocaNote> mVocaNoteArray;
	private ArrayList<Integer> mNoteCntArray;

	private int mLevel;
	private int mGubun;

	private StateListDrawable mStates1 = new StateListDrawable();
	private StateListDrawable mStates2 = new StateListDrawable();
	private StateListDrawable mStates3 = new StateListDrawable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_voca_day);

			init();	
			initHeader();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*My Vocab일때*/
		if(mGubun == 3) {
			initDatabase();
			checkDays();
			changeResource();	
		}
		else {
			initDatabase();
		}
	}

	/**
	 * 저장한 단어가 있을경우 해당 날짜 버튼 이미지 변경
	 */
	private void changeResource() {
		try {
			if(mNoteCntArray != null) {
				for(int i = 0 ; i < mNoteCntArray.size(); i ++) {
					int cnt = mNoteCntArray.get(i);
					/*갯수가 한개 이상일때*/
					if(cnt > 0) {
						if(mLevel == 1) {
							mBtnArray.get(i).setDefaultImage(R.drawable.d_blue2_btn);
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_blue_btn2);
						}
						else if(mLevel == 2) {
							mBtnArray.get(i).setDefaultImage(R.drawable.d_yel2_btn);
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_yel_btn2);
						}
						else {
							mBtnArray.get(i).setDefaultImage(R.drawable.d_red2_btn);
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_red_btn2);
						}
						mBtnArray.get(i).setCheckYn(true);
					}
					else {
						mBtnArray.get(i).setDefaultImage(R.drawable.d_btn);
						mBtnArray.get(i).setCheckYn(false);
						
						if(mLevel == 1) {
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_blue_btn);
						}
						else if(mLevel == 2) {
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_yel_btn);
						}
						else {
							mBtnArray.get(i).setBackgroundResource(R.drawable.selector_red_btn);
						}
					}

//					//테스트일때만
//					if(mGubun == 2) {
//						//터치 이미지 지정
//						if(mLevel == 1) {
//							mBtnArray.get(i).setSelectorImage(R.drawable.d_blue_btn);
//						}
//						else if(mLevel == 2) {
//							mBtnArray.get(i).setSelectorImage(R.drawable.d_yel_btn);
//						}
//						else {
//							mBtnArray.get(i).setSelectorImage(R.drawable.d_red_btn);
//						}
//						mBtnArray.get(i).setTouchYn(true);
//					}
//					else {
//						mBtnArray.get(i).setTouchYn(false);
//					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 날짜별로 저장한 단어 갯수 가져오기
	 */
	private void checkDays() {
		try {
			mNoteCntArray = new ArrayList<Integer>();

			for(int i = 1 ; i <= 20 ; i ++) {
				int cnt = 0;
				for(int j = 0 ; j < mVocaNoteArray.size() ; j++) {
					//같은날짜 단어장에ㅐ서 save = y인 갯수 카운터
					if(mVocaNoteArray.get(j).getDays().equals(String.valueOf(i))) {
						if(mVocaNoteArray.get(j).getSaveYn().equals("y")) {
							cnt++;
						}
					}
				}
				mNoteCntArray.add(cnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * db정보 가져오기
	 */
	private void initDatabase() {
		VocaDatabaseAdapter adapter = new VocaDatabaseAdapter(this);
		try {
			adapter.open();
			Cursor cursor = adapter.fetchNote(String.valueOf(mLevel));
			cursor.moveToFirst();

			mVocaNoteArray = new ArrayList<VocaNote>();

			while(!cursor.isAfterLast()) {
				VocaNote note = new VocaNote();
				note.setIndex(cursor.getInt(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME1)));
				note.setDays(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME7)));
				note.setGubun(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME5)));
				note.setMeaning(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME3)));
				note.setSaveYn(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME4)));
				note.setSynonym(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME6)));
				note.setWord(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME2)));

				mVocaNoteArray.add(note);

				cursor.moveToNext();
			}
			cursor.close();
			adapter.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 컴포넌트 초기화 및 리스너 연결
	 */
	private void init() {
		try {
			mBtnArray = new ArrayList<VocaDayButton>();

			Intent intent = getIntent();
			mGubun = intent.getExtras().getInt("gubun");
			mLevel = intent.getExtras().getInt("level");

			mLevelBtn = (ImageButton) findViewById(R.id.level_btn);
			mChooseBtn = (ImageButton) findViewById(R.id.choose_btn);
			mBtn1 = (VocaDayButton) findViewById(R.id.btn1);
			mBtn2 = (VocaDayButton) findViewById(R.id.btn2);
			mBtn3 = (VocaDayButton) findViewById(R.id.btn3);
			mBtn4 = (VocaDayButton) findViewById(R.id.btn4);
			mBtn5 = (VocaDayButton) findViewById(R.id.btn5);
			mBtn6 = (VocaDayButton) findViewById(R.id.btn6);
			mBtn7 = (VocaDayButton) findViewById(R.id.btn7);
			mBtn8 = (VocaDayButton) findViewById(R.id.btn8);
			mBtn9 = (VocaDayButton) findViewById(R.id.btn9);
			mBtn10 = (VocaDayButton) findViewById(R.id.btn10);
			mBtn11 = (VocaDayButton) findViewById(R.id.btn11);
			mBtn12 = (VocaDayButton) findViewById(R.id.btn12);
			mBtn13 = (VocaDayButton) findViewById(R.id.btn13);
			mBtn14 = (VocaDayButton) findViewById(R.id.btn14);
			mBtn15 = (VocaDayButton) findViewById(R.id.btn15);
			mBtn16 = (VocaDayButton) findViewById(R.id.btn16);
			mBtn17 = (VocaDayButton) findViewById(R.id.btn17);
			mBtn18 = (VocaDayButton) findViewById(R.id.btn18);
			mBtn19 = (VocaDayButton) findViewById(R.id.btn19);
			mBtn20 = (VocaDayButton) findViewById(R.id.btn20);

			mBtnArray.add(mBtn1);
			mBtnArray.add(mBtn2);
			mBtnArray.add(mBtn3);
			mBtnArray.add(mBtn4);
			mBtnArray.add(mBtn5);
			mBtnArray.add(mBtn6);
			mBtnArray.add(mBtn7);
			mBtnArray.add(mBtn8);
			mBtnArray.add(mBtn9);
			mBtnArray.add(mBtn10);
			mBtnArray.add(mBtn11);
			mBtnArray.add(mBtn12);
			mBtnArray.add(mBtn13);
			mBtnArray.add(mBtn14);
			mBtnArray.add(mBtn15);
			mBtnArray.add(mBtn16);
			mBtnArray.add(mBtn17);
			mBtnArray.add(mBtn18);
			mBtnArray.add(mBtn19);
			mBtnArray.add(mBtn20);

			for(int i = 0 ; i < mBtnArray.size(); i ++) {
				int tag = i+1;
				mBtnArray.get(i).setTag(tag);
				mBtnArray.get(i).setText(String.valueOf(tag));
				mBtnArray.get(i).setOnClickListener(mDayListener);

				//테스트일때만
				if(mGubun == 2) {
					//터치 이미지 지정
					if(mLevel == 1) {
						mBtnArray.get(i).setSelectorImage(R.drawable.d_blue_btn);
					}
					else if(mLevel == 2) {
						mBtnArray.get(i).setSelectorImage(R.drawable.d_yel_btn);
					}
					else {
						mBtnArray.get(i).setSelectorImage(R.drawable.d_red_btn);
					}
					mBtnArray.get(i).setTouchYn(true);
				}
				else {
					mBtnArray.get(i).setTouchYn(false);
					if(mLevel == 1) {
						mBtnArray.get(i).setBackgroundResource(R.drawable.selector_blue_btn);
					}
					else if(mLevel == 2) {
						mBtnArray.get(i).setBackgroundResource(R.drawable.selector_yel_btn);
					}
					else {
						mBtnArray.get(i).setBackgroundResource(R.drawable.selector_red_btn);
					}
					
				}
				mBtnArray.get(i).setDefaultImage(R.drawable.d_btn);
			}

			//테스트일떄
			if(mGubun == 2) {
				mChooseBtn.setOnClickListener(mChooseListener);
			}

			mLevelBtn.setBackgroundResource(VocaCommon.getInstance().mResourceArray.get(mLevel-1).getRecousrceImage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	/**
	 * choose btn 리스너
	 */
	OnClickListener mChooseListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String message = "";
			final ArrayList<VocaNote> array = new ArrayList<VocaNote>();
			for(int i = 0 ; i < mBtnArray.size(); i ++) {
				if(mBtnArray.get(i).isCheckYn() == true) { 
					int tag = (Integer) mBtnArray.get(i).getTag();
					message += tag +", ";
					for(int j = 0; j < mVocaNoteArray.size(); j++) {
						if(mVocaNoteArray.get(j).getDays().equals(String.valueOf(tag))) {
							array.add(mVocaNoteArray.get(j));	
						}
					}
				}
			}
			
			if(message.equals("")) {
				showToast("날짜를 선택해주세요.");
				return;
			}
			//마지막 콤마 제외
			message = message.substring(0, message.lastIndexOf(","));
			message += " 일자를 선택하셨습니다.";
			
			//arrayList 랜덤으로 석기
			Collections.shuffle(array);
			
			VocaCommon.getInstance().showDialog(VocaDayActivity.this, getResources().getString(R.string.app_name), message, new onDialogClick() {
				@Override
				public void setOnDialogoskclick() {
					Intent intent = new Intent(VocaDayActivity.this, VocaNoteActivity.class);
					intent.putExtra("gubun", mGubun);
					intent.putExtra("level", mLevel);
					intent.putExtra("days", -1);
					intent.putExtra("items", array);
					toActivity(intent);
				}

				@Override
				public void setOnDialogoskCancleclick() {
					
				}
			});
		
		}
	};

	/**
	 * 각 날짜별 버튼 리스너
	 */
	OnClickListener mDayListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int tag = (Integer) v.getTag();


			if(mGubun == 3) {
				if(mNoteCntArray.get(tag-1) == 0) {
					showToast("해당일에 저장된 단어가 없습니다.");
				}
				else {
					Intent intent = new Intent(VocaDayActivity.this, VocaNoteActivity.class);
					intent.putExtra("gubun", mGubun);
					intent.putExtra("level", mLevel);
					intent.putExtra("days", tag);
					toActivity(intent);
				}
			}
			else if(mGubun == 2) {
				VocaDayButton button = (VocaDayButton) v;
				button.toggleCheckYn();
			}
			else {
				Intent intent = new Intent(VocaDayActivity.this, VocaNoteActivity.class);
				intent.putExtra("gubun", mGubun);
				intent.putExtra("level", mLevel);
				intent.putExtra("days", tag);
				toActivity(intent);
			}
		}
	};


}
