package com.voca.vocable;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



public class VocaCommon {


	private static VocaCommon VOCA_INSTANCE;
	
	/**
	 * 싱글톤 클래스
	 * @return
	 */
	public static VocaCommon getInstance() {
		if(VOCA_INSTANCE == null) {
			VOCA_INSTANCE = new VocaCommon();
		}
		return VOCA_INSTANCE;
	}
	
	
	private VocaCommon() {
		mResourceArray = new ArrayList<ResourceClass>();
		
		//foundation 리소스 객체
		ResourceClass f = new ResourceClass();
		//introduction 리소스 객체
		ResourceClass i = new ResourceClass();
		//completion 리소스 객체
		ResourceClass c = new ResourceClass();
		
		
		//리소스파일 저장
		f.setRecousrceImage(R.drawable.f_main_title);
		f.setBtnImg1(R.drawable.f_main_btn1);
		f.setBtnImg2(R.drawable.f_main_btn2);
		f.setBtnImg3(R.drawable.f_main_btn3);
		
		i.setRecousrceImage(R.drawable.i_main_title);
		i.setBtnImg1(R.drawable.i_main_btn1);
		i.setBtnImg2(R.drawable.i_main_btn2);
		i.setBtnImg3(R.drawable.i_main_btn3);
		
		c.setRecousrceImage(R.drawable.c_main_title);
		c.setBtnImg1(R.drawable.c_main_btn1);
		c.setBtnImg2(R.drawable.c_main_btn2);
		c.setBtnImg3(R.drawable.c_main_btn3);
		
		
		mResourceArray.add(f);
		mResourceArray.add(i);
		mResourceArray.add(c);
	}
	
	
	/**
	 * 키보드 숨기기
	 * @param context
	 * @param p1
	 */
	public void InputKeybordHidden(Context context , EditText edit){
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}
	
	/**
	 * 다이알로그 callback 
	 * @author 이민석
	 *
	 */
	public static interface onDialogClick {
		/**
		 * 확인버튼 callback
		 */
		public void setOnDialogoskclick();
		/**
		 * 취소버튼 callback
		 */
		public void setOnDialogoskCancleclick();
	}
	
	/**
	 * 다이알로그(list 형식) callback(single)
	 * @author 이민석
	 *
	 */
	public static interface onDialogChoice {
		/**
		 * 아이템선택 callback(which = index)
		 * @param which
		 */
		public void setOnDialogSingleChoice(int which);
	}

	public onDialogClick mOnDialogClick;
	public onDialogChoice mOnDialogChoice;
	
	/**
	 * 리스트 다이알로그
	 * @param context
	 * @param title
	 * @param array
	 * @param basevalue
	 * @param callback
	 */
	public void ShowArrayDialog(Context context,String title,String[] array,int basevalue,onDialogChoice callback) {
		mOnDialogChoice = callback;
		
		AlertDialog.Builder builder;
		if (Integer.parseInt(Build.VERSION.SDK)  < 15) {
			builder = new AlertDialog.Builder(context);
		} else {
			builder = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		}
		builder.setTitle(title);
		builder.setSingleChoiceItems(array, basevalue,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						if(mOnDialogChoice != null){
							mOnDialogChoice.setOnDialogSingleChoice(which);
						}
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	/**
	 * 확인/취소 다이알로그
	 * @param context
	 * @param title
	 * @param msg
	 * @param callback
	 */
	public void showDialog(Context context, String title, String msg, final onDialogClick callback) {
		mOnDialogClick = callback;
		
		AlertDialog.Builder a_builder;
		if (Integer.parseInt(Build.VERSION.SDK) < 15) {
			a_builder = new AlertDialog.Builder(context);
		} else {
			a_builder = new AlertDialog.Builder(context ,AlertDialog.THEME_HOLO_LIGHT);
		}
		a_builder.setTitle(title).setMessage(msg).setCancelable(true)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(mOnDialogClick != null) {
					mOnDialogClick.setOnDialogoskclick();	
				}
				dialog.dismiss();
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(mOnDialogClick != null) {
					mOnDialogClick.setOnDialogoskCancleclick();	
				}
				dialog.dismiss();
			}
		});;
		a_builder.show();
	}
	

	
	public ArrayList<ResourceClass> mResourceArray;
	
	/**
	 * @author 이민석
	 * 리소스  정보 VO
	 */
	public class ResourceClass { 
		
		private int recousrceImage;
		private String recourceString;
		private int btnImg1;
		private int btnImg2;
		private int btnImg3;
		
		
		public int getRecousrceImage() {
			return recousrceImage;
		}
		/**
		 * 레벨별 타이틀 이미지 
		 * @param recousrceImage
		 */
		public void setRecousrceImage(int recousrceImage) {
			this.recousrceImage = recousrceImage;
		}
		public String getRecourceString() {
			return recourceString;
		}
		/**
		 * 레벨별 타이틀 스트링
		 * @param recourceString
		 */
		public void setRecourceString(String recourceString) {
			this.recourceString = recourceString;
		}
		public int getBtnImg1() {
			return btnImg1;
		}
		public void setBtnImg1(int btnImg1) {
			this.btnImg1 = btnImg1;
		}
		public int getBtnImg2() {
			return btnImg2;
		}
		public void setBtnImg2(int btnImg2) {
			this.btnImg2 = btnImg2;
		}
		public int getBtnImg3() {
			return btnImg3;
		}
		public void setBtnImg3(int btnImg3) {
			this.btnImg3 = btnImg3;
		}
	}
}
