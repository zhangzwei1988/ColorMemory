package com.accedo.game.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Handle the compatible of different size of screen
 * 
 * @ClassName: DimenUtils
 * @Description: TODO()
 * @author zzw
 * @date 2013-5-5 下午4:58:16
 */
public class DimenUtils {

	private static float xScale;
	private static float yScale;
	private static float width;
	private static float height;

	private Context mContext;

	Logger logger;

	public DimenUtils(Context mContext) {
		super();
		this.mContext = mContext;
		logger = Logger.Jlog();

		width = mContext.getResources().getDisplayMetrics().widthPixels;
		height = mContext.getResources().getDisplayMetrics().heightPixels;
		xScale = (float) (width / 800.0);
		yScale = (float) (height / 1280.0);

		// logger.d(xScale + "-" + yScale + "|" + width + "-" + height);

	}

	/**
	 * Turn dp to px according to resolution
	 */
	public int dip2px(float dpValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * Turn px(pixcel) to dp according to resolution
	 */
	public int px2dip(float pxValue) {

		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public int getTranslateWidth(int width) {
		return (int) (xScale * width) + 1;
	}

	public int getTranslateHeight(int height) {
		return (int) (yScale * height) + 1;
	}

	public void setSize(View view, int width, int height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (width > 0) {
			layoutParams.width = getTranslateWidth(width);
		}
		if (height > 0) {
			layoutParams.height = getTranslateHeight(height);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setSizeHorizontal(View view, int width, int height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (width > 0) {
			layoutParams.width = getTranslateWidth(width);
		}
		if (height > 0) {
			layoutParams.height = getTranslateWidth(height);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setSizeOrigan(View view, int width, int height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (width > 0) {
			layoutParams.width = width;
		}
		if (height > 0) {
			layoutParams.height = height;
		}

		view.setLayoutParams(layoutParams);
	}

	public void setSizeVertical(View view, int width, int height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (width > 0) {
			layoutParams.width = getTranslateHeight(width);
		}
		if (height > 0) {
			layoutParams.height = getTranslateHeight(height);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setMargin(View view, int left, int top, int right, int bottom) {

		ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view
				.getLayoutParams();
		layoutParams.leftMargin = getTranslateWidth(left);
		layoutParams.topMargin = getTranslateHeight(top);
		layoutParams.rightMargin = getTranslateWidth(right);
		if (bottom < 0) {
			layoutParams.bottomMargin = -getTranslateHeight(-bottom);
		} else {
			layoutParams.bottomMargin = getTranslateHeight(bottom);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setMarginHorizontal(View view, int left, int top, int right,
			int bottom) {

		ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view
				.getLayoutParams();
		layoutParams.leftMargin = getTranslateWidth(left);
		layoutParams.topMargin = getTranslateWidth(top);
		layoutParams.rightMargin = getTranslateWidth(right);
		if (bottom < 0) {
			layoutParams.bottomMargin = -getTranslateWidth(-bottom);
		} else {
			layoutParams.bottomMargin = getTranslateWidth(bottom);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setMarginVertical(View view, int left, int top, int right,
			int bottom) {

		ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view
				.getLayoutParams();
		layoutParams.leftMargin = getTranslateHeight(left);
		layoutParams.topMargin = getTranslateHeight(top);
		layoutParams.rightMargin = getTranslateHeight(right);
		if (bottom < 0) {
			layoutParams.bottomMargin = -getTranslateHeight(-bottom);
		} else {
			layoutParams.bottomMargin = getTranslateHeight(bottom);
		}

		view.setLayoutParams(layoutParams);
	}

	public void setPadding(View view, int left, int top, int right, int bottom) {

		view.setPadding(getTranslateWidth(left), getTranslateHeight(top),
				getTranslateWidth(right), getTranslateHeight(bottom));
	}

	public void setPaddingHorizontal(View view, int left, int top, int right,
			int bottom) {

		view.setPadding(getTranslateWidth(left), getTranslateWidth(top),
				getTranslateWidth(right), getTranslateWidth(bottom));
	}

	public void setPaddingVertical(View view, int left, int top, int right,
			int bottom) {

		view.setPadding(getTranslateHeight(left), getTranslateHeight(top),
				getTranslateHeight(right), getTranslateHeight(bottom));
	}

	public int getShelfOffectPadding() {

		if (height > 600) {
			return 50;
		} else {
			return 42;
		}
	}
}
