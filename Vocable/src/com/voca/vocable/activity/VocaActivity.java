package com.voca.vocable.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.voca.vocable.R;

@SuppressLint("NewApi")
public class VocaActivity extends Activity {




	//	public ArrayList<ResourceClass> mResourceArray;


	private ImageView mHeaderBtn;
	private ImageButton mFaceBookBtn;
	private ImageButton mYoutubeBtn;
	private ImageButton mBlogBtn;
	private ImageButton mCafeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	/**
	 * 공통 헤더 부분 초기화
	 */
	public void initHeader() {
		View haderView = (View) findViewById(R.id.top_layout);
		mHeaderBtn = (ImageView) haderView.findViewById(R.id.voca_top_header_btn);

		mHeaderBtn.setOnClickListener(mHeaderBtnListener);

		initFooter();
	}

	/**
	 * 공통 푸터 초기화
	 */
	public void initFooter() {
		View footerView = (View) findViewById(R.id.bottom_layout);

		mFaceBookBtn = (ImageButton) footerView.findViewById(R.id.facebook_btn);
		mYoutubeBtn = (ImageButton) footerView.findViewById(R.id.youtube_btn);
		mBlogBtn = (ImageButton) footerView.findViewById(R.id.blog_btn);
		mCafeBtn = (ImageButton) footerView.findViewById(R.id.cafe_btn);

		mFaceBookBtn.setTag(getResources().getString(R.string.facebook_url));
		mYoutubeBtn.setTag(getResources().getString(R.string.youtube_url));
		mBlogBtn.setTag(getResources().getString(R.string.naver_blog_url));
		mCafeBtn.setTag(getResources().getString(R.string.daum_cafe_url));

		mFaceBookBtn.setOnClickListener(mWebStieListener);
		mYoutubeBtn.setOnClickListener(mWebStieListener);
		mBlogBtn.setOnClickListener(mWebStieListener);
		mCafeBtn.setOnClickListener(mWebStieListener);
	}

	/**
	 * 
	 * 토스트 출력
	 * @param message
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, 1).show();
	}

	/**
	 * 액티비티 이동
	 * @param intent
	 */
	public void toActivity(final Intent intent) {
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
		startActivity(intent);
	}

	/**
	 * 메인액티비티로 이동
	 */
	public void toMainActivity() {
		Intent intent = new Intent(this, VocaMainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/**
	 * 해당 웹사이트로 연결
	 * @param url
	 */
	private void toWebSite( String url ) {
		Uri uri = Uri.parse(url);
		Intent it  = new Intent(Intent.ACTION_VIEW,uri);
		startActivity(it);
	}

	/**
	 * 웹사이트 연결  리스너
	 */
	OnClickListener mWebStieListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String url = (String) v.getTag();
			toWebSite(url);
		}
	};


	/**
	 * 헤더버튼 리스너
	 */
	OnClickListener mHeaderBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			toMainActivity();
		}
	};
}
