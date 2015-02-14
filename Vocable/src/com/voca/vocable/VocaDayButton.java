package com.voca.vocable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint({"NewApi"})
public class VocaDayButton
extends Button {
	private boolean mCheckYn;
	private int mDefaultImage;
	private int mSelectorImage;
	private boolean mTouchYn;

	public VocaDayButton(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public int getDefaultImage() {
		return this.mDefaultImage;
	}

	public int getSelectorImage() {
		return this.mSelectorImage;
	}

	public boolean isCheckYn() {
		return this.mCheckYn;
	}

	public boolean isTouchYn() {
		return this.mTouchYn;
	}

	public void setCheckYn(boolean paramBoolean) {
		this.mCheckYn = paramBoolean;
	}

	public void setDefaultImage(int paramInt) {
		this.mDefaultImage = paramInt;
	}

	public void setSelectorImage(int paramInt) {
		this.mSelectorImage = paramInt;
	}

	public void setTouchYn(boolean paramBoolean) {
		this.mTouchYn = paramBoolean;
	}

	public void toggleCheckYn() {
		if(mTouchYn) {
			if (!this.mCheckYn) {
				setBackground(getResources().getDrawable(this.mSelectorImage)); 
			}
			else {
				setBackground(getResources().getDrawable(this.mDefaultImage));
			}
			this.mCheckYn = !mCheckYn;

		}
	}
}
