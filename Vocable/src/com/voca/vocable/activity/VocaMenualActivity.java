package com.voca.vocable.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.voca.vocable.R;

public class VocaMenualActivity extends VocaActivity {
	private LayoutInflater mInflater;
	public int[] mResource = new int[] {
            R.drawable.menual_1, R.drawable.menual_2, R.drawable.menual_3,
            R.drawable.menual_4, R.drawable.menual_5, R.drawable.menual_6,
            R.drawable.menual_7, R.drawable.menual_8, R.drawable.menual_9,
            R.drawable.menual_10, R.drawable.menual_11
    };
	
	
	private ViewPager mMenualPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_voca_menual);
		init();
	}
	
	
	/**
	 * 컴포넌트 초기화 및 리스너 연결
	 */
	private void init() {
		
		try {
			mInflater = LayoutInflater.from(this);
			
			mMenualPager = (ViewPager) findViewById(R.id.menual_pager);
			mMenualPager.setOffscreenPageLimit(5);
			mMenualPager.setAdapter(new ImageAdapter());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 페이저 어댑터 클래스
	 * @author 이민석
	 *
	 */
	class ImageAdapter extends PagerAdapter {
		 
        @Override
        public int getCount() {
            return mResource.length;
        }
 
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }
 
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	View v = mInflater.inflate(R.layout.item_pager, null);
        	ImageView itemImgv = (ImageView) v.findViewById(R.id.item_pager_img);
        	
        	itemImgv.setImageResource(mResource[position]);
            ((ViewPager) container).addView(v, 0);
            return v;
        }
 
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
    }
}
