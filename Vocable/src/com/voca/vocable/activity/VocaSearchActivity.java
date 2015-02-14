package com.voca.vocable.activity;

import java.sql.SQLException;
import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voca.vocable.R;
import com.voca.vocable.VocaCommon;
import com.voca.vocable.VocaCommon.onDialogChoice;
import com.voca.vocable.db.VocaDatabaseAdapter;
import com.voca.vocable.vo.VocaNote;

public class VocaSearchActivity extends VocaActivity {
	
	
	private ImageButton mSearchBtn;
	private ImageView mLogoView1;
	private ImageView mLogoView2;
	private RelativeLayout mSearchLayout;
	private EditText mSearchEdit;
	private ArrayList<VocaNote> mVocaNoteArr;
	private VocaNote mVocaNote;
	private LinearLayout mNoteLayout;
	private TextView mWordTxt;
	private TextView mMeaningTxt;
	private TextView mSynonymTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_voca_search);
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
			mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
			mSearchLayout = (RelativeLayout) findViewById(R.id.searhc_layout);
			mLogoView1 = (ImageView) findViewById(R.id.logo_view1);
			mLogoView2 = (ImageView) findViewById(R.id.logo_view2);		
			mSearchEdit = (EditText) findViewById(R.id.search_edit);
			mNoteLayout = (LinearLayout) findViewById(R.id.note_layout);
			mWordTxt = (TextView) findViewById(R.id.note_word_txt);
			mMeaningTxt = (TextView) findViewById(R.id.note_meaning_txt);
			mSynonymTxt = (TextView) findViewById(R.id.note_synonym_txt);
			
			mSearchBtn.setOnClickListener(mSearchBtnListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 데이터베이스에서 단어 검색( searchWord like % )
	 */
	private boolean searchDatabase() {
		VocaDatabaseAdapter adapter = new VocaDatabaseAdapter(this);
		try {
			adapter.open();
			Cursor cursor = adapter.searchNote(mSearchEdit.getText().toString());
			cursor.moveToFirst();
			mVocaNoteArr = new ArrayList<VocaNote>();
			mVocaNoteArr.clear();
			VocaNote vocaNote;
			while(!cursor.isAfterLast()) {
				vocaNote = new VocaNote();
				vocaNote.setIndex(cursor.getInt(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME1)));
				vocaNote.setDays(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME7)));
				vocaNote.setGubun(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME5)));
				vocaNote.setMeaning(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME3)));
				vocaNote.setSaveYn(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME4)));
				vocaNote.setSynonym(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME6)));
				vocaNote.setWord(cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME2)));
				mVocaNoteArr.add(vocaNote);
				cursor.moveToNext();
			}
			adapter.close();
			cursor.close();
			
			if(mVocaNoteArr.size() == 0) return false;
			else return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	/**
	 * 검색버튼 리스너
	 */
	OnClickListener mSearchBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(mSearchEdit.getText().toString().trim().equals("")) {
				showToast("검색 단어를 입력해 주세요.");
				return;
			}
			
			boolean searchYn = searchDatabase();
			if(searchYn == true) {
				String[] array = new String[mVocaNoteArr.size()];
				for(int i = 0 ; i < mVocaNoteArr.size() ; i ++) {
					array[i] = mVocaNoteArr.get(i).getWord();
				}
				
				VocaCommon.getInstance().InputKeybordHidden(VocaSearchActivity.this, mSearchEdit);
				
				VocaCommon.getInstance().ShowArrayDialog(VocaSearchActivity.this, getResources().getString(R.string.app_name), array, 0, new onDialogChoice() {

					@Override
					public void setOnDialogSingleChoice(int which) {
						//선택된 노트 정보 가져오기
						mVocaNote = mVocaNoteArr.get(which);
						
						//단어장 화면 갱신
						mNoteLayout.setVisibility(View.VISIBLE);
						mLogoView1.setVisibility(View.GONE);
						mLogoView2.setVisibility(View.GONE);
						
						//단어정보 출력
						mWordTxt.setText(mVocaNote.getWord());
						mMeaningTxt.setText(mVocaNote.getMeaning());
						
						
						//foundation 구분
						if(mVocaNote.getGubun().equals("1")) {
							mSynonymTxt.setVisibility(View.GONE);
							mSynonymTxt.setText("");
						}
						else {
							mSynonymTxt.setVisibility(View.VISIBLE);
							mSynonymTxt.setText(mVocaNote.getSynonym());
						}
						
						
						//다시한번 ㅣㅋ보드 숨기기( 재차 확인)
						VocaCommon.getInstance().InputKeybordHidden(VocaSearchActivity.this, mSearchEdit);
						//가져온 정보 초 기화
						mVocaNote = null;
						mVocaNoteArr.clear();
					}
					
				});
				
			}
			else {
				showToast("검색한 단어가 단어장에 없습니다.");
			}
		}
	};
}
