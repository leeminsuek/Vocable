package com.voca.vocable.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.voca.vocable.R;
import com.voca.vocable.VocaCommon;
import com.voca.vocable.VocaCommon.onDialogClick;
import com.voca.vocable.db.VocaDatabaseAdapter;
import com.voca.vocable.vo.VocaNote;

public class VocaNoteActivity extends VocaActivity implements OnInitListener {


	private Button mLevelBtn;
	private ImageButton mSaveBtn;
	private ImageButton mDeleteBtn;

	private ImageButton mTtsBtn;
	private ImageButton mNextBtn;
	private ImageButton mPrevBtn;

	private TextView mWordTxt;
	private TextView mMeaningTxt;
	private TextView mSynonymTxt;

//	private RelativeLayout mSynonymLayout;
//	private RelativeLayout mMeaningLayout;Z

	/**
	 * 단어 레벨 구분
	 */
	private int mLevel;
	/**
	 * vocableation, test, my vocab 구분
	 */
	private int mGubun;
	private int mDays;
	private int mPageCnt;


	private TextToSpeech mTextToSpeech;
	private boolean mSpeechActive = false;

	private ArrayList<VocaNote> mVocaNoteArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voca_note);

		init();
		initHeader();
		
		//날짜를 여러개 선택해서 들어온경우
		if(mDays == -1) {
			mVocaNoteArray = (ArrayList<VocaNote>) getIntent().getSerializableExtra("items");
		}
		else {
			initDatabase();	
		}
		initWord();
	}

	/**
	 * 단어장 표시
	 */
	private void initWord() {
		try {
			if(mVocaNoteArray != null && mVocaNoteArray.size() > 0) {
				VocaNote note = mVocaNoteArray.get(mPageCnt);

				
				
				mLevelBtn.setText("DAY "+note.getDays());
				
				mWordTxt.setText(note.getWord());
				mMeaningTxt.setText(note.getMeaning());

				/*foundation가 아닐때동의어 표시*/
				if(mLevel != 1) {
					mSynonymTxt.setText(note.getSynonym());
				}
				
				/*셀프 테스트, my vocab 일떄*/
				if(mGubun == 2 || mGubun == 3) {
					mSynonymTxt.setText("Click");
					mMeaningTxt.setText("Click");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 컴포넌트 초기화 및 리스너 연결 
	 */
	private void init() {
		try {

			mTextToSpeech = new TextToSpeech(this,this);
			//초기 페이지 설정
			mPageCnt = 0;

			Intent intent = getIntent();
			mLevel = intent.getExtras().getInt("level");
			mGubun = intent.getExtras().getInt("gubun");
			mDays = intent.getExtras().getInt("days");

			mLevelBtn = (Button) findViewById(R.id.level_btn);
			mSaveBtn = (ImageButton) findViewById(R.id.note_save_btn);
			mDeleteBtn = (ImageButton) findViewById(R.id.note_delete_btn);
			mNextBtn = (ImageButton) findViewById(R.id.note_next_btn);
			mPrevBtn = (ImageButton) findViewById(R.id.note_prev_btn);
			mTtsBtn = (ImageButton) findViewById(R.id.note_tts_btn);
			mWordTxt = (TextView) findViewById(R.id.note_word_txt);
			mMeaningTxt = (TextView) findViewById(R.id.note_meaning_txt);
			mSynonymTxt = (TextView) findViewById(R.id.note_synonym_txt);
//			mSynonymLayout = (RelativeLayout) findViewById(R.id.note_synonym_layout);
//			mMeaningLayout = (RelativeLayout) findViewById(R.id.note_meaning_layout);

			mMeaningTxt.setTag(1);
			mSynonymTxt.setTag(2);

//			mSynonymLayout.setTag(mSynonymTxt);
//			mMeaningLayout.setTag(mMeaningTxt);

			mNextBtn.setOnClickListener(mNextBtnListener);
			mPrevBtn.setOnClickListener(mPrevBtnListener);
			mSaveBtn.setOnClickListener(mSaveBtnListener);
			mDeleteBtn.setOnClickListener(mDelBtnListener);
			mTtsBtn.setOnClickListener(mSpeechListener);
			

			/*
			 * Foiundation일때
			 * 동의어 텍스트 부분을 숨겨준다.
			 * */
			if(mLevel == 1) {
				mSynonymTxt.setVisibility(View.GONE);
			}
			else {
				mSynonymTxt.setVisibility(View.VISIBLE);
			}

			//vocabulary
			if(mGubun == 1) {
				mTtsBtn.setVisibility(View.VISIBLE);
				mSaveBtn.setVisibility(View.GONE);
				mDeleteBtn.setVisibility(View.GONE);
			}
			//selft test
			else if(mGubun == 2) {
				mTtsBtn.setVisibility(View.GONE);
				mSaveBtn.setVisibility(View.VISIBLE);
				mDeleteBtn.setVisibility(View.GONE);
				
				mMeaningTxt.setOnTouchListener(mLayoutTouchListener);
				mSynonymTxt.setOnTouchListener(mLayoutTouchListener);
			}
			//my vocab
			else if(mGubun == 3) {
				mTtsBtn.setVisibility(View.GONE);
				mSaveBtn.setVisibility(View.GONE);
				mDeleteBtn.setVisibility(View.VISIBLE);
				
				mMeaningTxt.setOnTouchListener(mLayoutTouchListener);
				mSynonymTxt.setOnTouchListener(mLayoutTouchListener);
			}
			

			if(mLevel == 1) {
				mLevelBtn.setBackgroundResource(R.drawable.d_blue_btn);				
			}
			else if(mLevel == 2) {
				mLevelBtn.setBackgroundResource(R.drawable.d_yel_btn);				
			}
			else {
				mLevelBtn.setBackgroundResource(R.drawable.d_red_btn);				
			}

//			String levelText =mResourceArray.get(mLevel-1).getRecourceString() + " DAY - " + mDays; 
//			mLevelBtn.setText(levelText);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 데이터베이스에서 정보 가져오기(날짜, 구분)
	 */
	private void initDatabase() {
		VocaDatabaseAdapter adapter = new VocaDatabaseAdapter(this);
		try {
			adapter.open();
			Cursor cursor = adapter.fetchNote(String.valueOf(mLevel), String.valueOf(mDays));;
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

				if(mGubun == 3) {
					if(note.getSaveYn().equals("y")) {
						mVocaNoteArray.add(note);
					}
				}
				else {
					mVocaNoteArray.add(note);	
				}
				cursor.moveToNext();
			}
			adapter.close();
			cursor.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 단어장 이동시 음성 출력 멈춤
	 */
	private void stopSpeech() {
		try {
			if(mTextToSpeech != null) {
				mTextToSpeech.stop();
				mSpeechActive = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 레벨별 스트링(토스트 출력용)
	 *
	 *		mFoundationBtn.setTag(1);
	 *		mIntroductionBtn.setTag(2);
	 *		mCompletionBtn.setTag(3);
	 */
	private String getLevelString() {
		if(mVocaNoteArray.get(mPageCnt).getGubun().equals("1")) {
			return "Foundation";
		}
		else if(mVocaNoteArray.get(mPageCnt).getGubun().equals("2")) {
			return "Introduction";
		}
		else if(mVocaNoteArray.get(mPageCnt).getGubun().equals("3")) {
			return "Completion";
		}
		return "";
	}

	/**
	 * 음성 출력 버튼 리스너
	 */
	OnClickListener mSpeechListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(mTextToSpeech != null) {
				mTextToSpeech.setLanguage(Locale.US);
				mSpeechActive = true;
				mTextToSpeech.speak(mWordTxt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	};

	/**
	 * 단어 삭제 버튼 리스너
	 */
	OnClickListener mDelBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			try {
				VocaNote note = null;
				note = mVocaNoteArray.get(mPageCnt);
				final String toastMesssage = getLevelString() +" DAY"+mDays + " " + note.getWord();
				
				VocaCommon.getInstance().showDialog(VocaNoteActivity.this, getResources().getString(R.string.app_name),toastMesssage+" 단어를 삭제하시겠습니까?" , new onDialogClick() {
					@Override
					public void setOnDialogoskclick() {
						VocaDatabaseAdapter adapter = new VocaDatabaseAdapter(VocaNoteActivity.this);
						try {
							adapter.open();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						boolean result = adapter.updateNote("n", mVocaNoteArray.get(mPageCnt).getIndex());
					
						if(result) {
							mVocaNoteArray.remove(mPageCnt);
							if(mPageCnt - 1 <= 0) {
								mPageCnt = mVocaNoteArray.size()-1;
							}
							else {
								mPageCnt --;
							}
							if(mVocaNoteArray.size() == 0) {
								finish();
							}
							else {
								initWord();	
							}
							
							showToast(toastMesssage + " 단어가 삭제되었습니다.");
						}
					}
					
					@Override
					public void setOnDialogoskCancleclick() {
					}
				}); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	};

	/**
	 * 단어 저장 버튼 리스너
	 */
	OnClickListener mSaveBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			VocaDatabaseAdapter adapter = new VocaDatabaseAdapter(VocaNoteActivity.this);
			try {
				adapter.open();
				boolean saveYn = adapter.checkNote(mVocaNoteArray.get(mPageCnt).getIndex());
				if(saveYn) {
					showToast("이미 저장되어 있는 단어입니다.");
				}
				else {
					boolean result = adapter.updateNote("y", mVocaNoteArray.get(mPageCnt).getIndex());
					
					String toastMesssage = getLevelString() +" DAY "+ mVocaNoteArray.get(mPageCnt).getDays() + " " + mVocaNoteArray.get(mPageCnt).getWord();
					showToast(toastMesssage+" 단어가 저장되었습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	};

	/**
	 * 다음단어 버튼 리스너
	 */
	OnClickListener mNextBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			stopSpeech();
			//마지막 단어이면
			if(mPageCnt + 1 >= mVocaNoteArray.size()) {
				mPageCnt = 0;
			}
			else {
				mPageCnt ++;
			}

//			showToast(mVocaNoteArray.get(mPageCnt).getSynonym());
			initWord();
		}
	};

	/**
	 * 이전단어 버튼 리스너
	 */
	OnClickListener mPrevBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			stopSpeech();
			//맨 처음 단어이면
			if(mPageCnt - 1 < 0) {
				mPageCnt = mVocaNoteArray.size()-1;
			}
			else {
				mPageCnt --;
			}

//			showToast(mVocaNoteArray.get(mPageCnt).getSynonym());
			initWord();
		}
	};

	/**
	 * 레이아웃 터치 리스너
	 */
	OnTouchListener mLayoutTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			TextView txtv = (TextView) v;
			int tag = (Integer) txtv.getTag();
			
			 switch (event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	if(tag == 1) {
						txtv.setText(mVocaNoteArray.get(mPageCnt).getMeaning());
					}
					else {
						txtv.setText(mVocaNoteArray.get(mPageCnt).getSynonym());
					}
		            break;
		        case MotionEvent.ACTION_UP:
		        	txtv.setText("Click");
		            break;
		        default:
		            break;
		    }
		    return true;
		}
	};


	@Override
	public void onInit(int status) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if(mTextToSpeech != null) {
				mTextToSpeech.shutdown();
				mTextToSpeech = null;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
