package com.accedo.game.model;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * @ClassName: Cell
 * @Description: TODO()
 * @author zzw
 */

public class Cell {

	// Front Image
	private Bitmap imageBg;

	// Area of the Card
	private RectF rectF;

	// Whether it is turned
	private boolean isTurn;

	// Whether it is found
	private boolean isFind;

	// Index in the array
	private int x;
	private int y;

	public Bitmap getImageBg() {
		return imageBg;
	}

	public void setImageBg(Bitmap imageBg) {
		this.imageBg = imageBg;
	}

	public RectF getRectF() {
		return rectF;
	}

	public void setRectF(RectF rectF) {
		this.rectF = rectF;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public boolean isFind() {
		return isFind;
	}

	public void setFind(boolean isFind) {
		this.isFind = isFind;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
