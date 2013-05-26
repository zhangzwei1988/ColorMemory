package com.accedo.game.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.accedo.game.R;
import com.accedo.game.callback.GameCallBack;
import com.accedo.game.model.Cell;
import com.accedo.game.model.GameStatus;
import com.accedo.game.util.Logger;
import com.accedo.game.util.MemoryPreference;

/**
 * The custom surface that display the gameboard and handle the operation of
 * user
 * 
 * @ClassName: GameSurface
 * @Description: TODO()
 * @author zzw
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	protected SurfaceHolder holder;

	// The gamecallback of the instance
	private static GameCallBack gameCallBack;

	// Instance of global canvas
	private Canvas canvas;

	// Instance of global radom
	private Random random;

	private Paint paint, mPaint;

	private Logger logger;

	// The scale of the Image
	private float scale;

	// Wait for 1500 millisecond after user turned the second card
	private Timer timer;

	// For leaving the game for 1 minutes and popup the dialog of timeout
	private Timer timer2;

	// Keep the time for last operation
	private long lastOperatTime = 0;

	// The list of the bitmap
	private ArrayList<Bitmap> bitmapList;

	// SurfaceView's width and height,the image's width and height,the
	// separator's width and height
	private int width, height, imgWidth, imgHeight, splitWidth, splitHeight;

	// The bitmaps
	private Bitmap bitmapBg, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5,
			bitmap6, bitmap7, bitmap8;

	public GameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		holder = this.getHolder();
		holder.addCallback(this);

		// set the top of SurfaceView to transparent
		holder.setFormat(PixelFormat.TRANSPARENT);
		this.setZOrderOnTop(true);

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);

		paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

		timer = new Timer();
		timer2 = new Timer();
		random = new Random();

		logger = Logger.Jlog();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

		logger.d("surfaceChanged");

		this.width = arg2;
		this.height = arg3;

		lastOperatTime = System.currentTimeMillis();

		initView();
		drawSurface();

		timer2 = new Timer();
		timer2.schedule(new TimeoutTask(), 100, 1000);

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

		logger.d("surfaceCreated");

		setKeepScreenOn(true);

	}

	// Play the game again
	public void comeAagin() {
		MainActivity.gameStatus = null;

		initView();
		drawSurface();
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		logger.d("surfaceDestroyed");

		if (timer2 != null) {
			timer2.cancel();
		}
		timer2 = null;
	}

	public static void setGameCallBack(GameCallBack callBack) {
		gameCallBack = callBack;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			lastOperatTime = System.currentTimeMillis();

			if (MainActivity.gameStatus.flipCount >= 2) {
				return true;
			}

			Cell cell = getTouchedCell(event);

			if (cell != null && !cell.isTurn() && !cell.isFind()) {

				drawClick(cell);
			}

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}
		return true;
	}

	public void drawClick(Cell cell) {

		MainActivity.gameStatus.flipCount++;

		if (MainActivity.gameStatus.flipCount == 1) {
			MainActivity.gameStatus.cell1 = cell;
			MainActivity.gameStatus.cells[MainActivity.gameStatus.cell1.getX()][MainActivity.gameStatus.cell1
					.getY()].setTurn(true);
		} else if (MainActivity.gameStatus.flipCount == 2) {
			MainActivity.gameStatus.cell2 = cell;
			MainActivity.gameStatus.cells[MainActivity.gameStatus.cell2.getX()][MainActivity.gameStatus.cell2
					.getY()].setTurn(true);
		}

		drawImage(cell);

		if (MainActivity.gameStatus.cell1 != null
				&& MainActivity.gameStatus.cell2 != null) {
			timer.schedule(new ClickTask(), 1500);
		}
	}

	class ClickTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (MainActivity.gameStatus.cell1.getImageBg().equals(
					MainActivity.gameStatus.cell2.getImageBg())) {

				drawBlank(MainActivity.gameStatus.cell1);
				drawBlank(MainActivity.gameStatus.cell2);

				MainActivity.gameStatus.cells[MainActivity.gameStatus.cell1
						.getX()][MainActivity.gameStatus.cell1.getY()]
						.setFind(true);
				MainActivity.gameStatus.cells[MainActivity.gameStatus.cell2
						.getX()][MainActivity.gameStatus.cell2.getY()]
						.setFind(true);

				MainActivity.gameStatus.score += 2;
				MainActivity.gameStatus.finishcount++;

				if (MainActivity.gameStatus.finishcount == 8) {
					gameCallBack.setScore(MemoryPreference.RIGHT_ANSWER);
					gameCallBack.setSuccess();
					timer2.cancel();
				} else {
					gameCallBack.setScore(MemoryPreference.RIGHT_ANSWER);
				}

			} else {

				drawBg(MainActivity.gameStatus.cell1);
				drawBg(MainActivity.gameStatus.cell2);

				MainActivity.gameStatus.score--;

				gameCallBack.setScore(MemoryPreference.WRONG_ANSWER);

			}

			MainActivity.gameStatus.cells[MainActivity.gameStatus.cell1.getX()][MainActivity.gameStatus.cell1
					.getY()].setTurn(false);
			MainActivity.gameStatus.cells[MainActivity.gameStatus.cell2.getX()][MainActivity.gameStatus.cell2
					.getY()].setTurn(false);

			MainActivity.gameStatus.cell1 = null;
			MainActivity.gameStatus.cell2 = null;

			MainActivity.gameStatus.flipCount = 0;

		}

	}

	class TimeoutTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (System.currentTimeMillis() - lastOperatTime > 1000 * 60) {
				gameCallBack.setTimeOut();
				if (timer2 != null) {
					timer2.cancel();
					timer2 = null;
				}
			}

		}
	};

	public void initView() {

		if (bitmapList == null || bitmapList.size() != 16) {
			initImage();
		}
		initScale();

		initPanel();

	}

	public void initPanel() {

		if (MainActivity.gameStatus == null) {
			MainActivity.gameStatus = new GameStatus();

			MainActivity.gameStatus.cells = new Cell[4][4];

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					MainActivity.gameStatus.cells[i][j] = new Cell();

					RectF rectF = new RectF();

					rectF.left = MemoryPreference.MARGIN + i * imgWidth + i
							* splitWidth;
					rectF.top = MemoryPreference.MARGIN + j * imgHeight + j
							* splitHeight;
					rectF.right = MemoryPreference.MARGIN + (i + 1) * imgWidth
							+ i * splitWidth;
					rectF.bottom = MemoryPreference.MARGIN + (j + 1)
							* imgHeight + j * splitHeight;
					MainActivity.gameStatus.cells[i][j]
							.setImageBg(getRandomBitmap());
					MainActivity.gameStatus.cells[i][j].setRectF(rectF);
					MainActivity.gameStatus.cells[i][j].setX(i);
					MainActivity.gameStatus.cells[i][j].setY(j);
				}
			}
		}
	}

	public void drawSurface() {

		canvas = holder.lockCanvas(null);

		canvas.drawPaint(paint);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				if (MainActivity.gameStatus.cells[i][j].isTurn()) {
					canvas.drawBitmap(
							MainActivity.gameStatus.cells[i][j].getImageBg(),
							null,
							MainActivity.gameStatus.cells[i][j].getRectF(),
							mPaint);
				} else if (!MainActivity.gameStatus.cells[i][j].isFind()) {
					canvas.drawBitmap(bitmapBg, null,
							MainActivity.gameStatus.cells[i][j].getRectF(),
							mPaint);
				}
			}
		}

		holder.unlockCanvasAndPost(canvas);
	}

	public void initScale() {

		scale = 1.0f;

		splitWidth = width - 2 * MemoryPreference.MARGIN - 4 * imgWidth;
		splitHeight = height - 2 * MemoryPreference.MARGIN - 4 * imgHeight;

		if (splitHeight < 0 && splitWidth < 0) {
			scale = Math.min((width - 2 * MemoryPreference.MARGIN)
					/ (4.0f * imgWidth), (height - 2 * MemoryPreference.MARGIN)
					/ (4.0f * imgHeight));
		}

		else if (splitHeight < 0 && splitWidth > 0) {

			scale = (height - 2 * MemoryPreference.MARGIN) / (4.0f * imgHeight);

		} else if (splitHeight > 0 && splitWidth < 0) {
			scale = (width - 2 * MemoryPreference.MARGIN) / (4.0f * imgWidth);
		} else if (splitHeight == 0) {

			if (splitWidth > 0) {

				scale = 0.9f;

			} else if (splitWidth < 0) {
				scale = (width - 2 * MemoryPreference.MARGIN)
						/ (4.0f * imgWidth);
			}

		} else if (splitWidth == 0) {
			if (splitHeight > 0) {

				scale = 0.9f;
			} else if (splitHeight < 0) {
				scale = (height - 2 * MemoryPreference.MARGIN)
						/ (4.0f * imgHeight);
			}
		}

		imgWidth *= scale;
		imgHeight *= scale;

		splitWidth = (width - 2 * MemoryPreference.MARGIN - 4 * imgWidth) / 3;
		splitHeight = (height - 2 * MemoryPreference.MARGIN - 4 * imgHeight) / 3;
	}

	public void initImage() {

		bitmapList = new ArrayList<Bitmap>();
		Matrix matrix = new Matrix();

		float[] floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
		matrix.setValues(floats);

		bitmapBg = BitmapFactory.decodeResource(getResources(),
				R.drawable.card_bg);

		bitmap1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour1);
		bitmap2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour2);
		bitmap3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour3);
		bitmap4 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour4);
		bitmap5 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour5);
		bitmap6 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour6);
		bitmap7 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour7);
		bitmap8 = BitmapFactory.decodeResource(getResources(),
				R.drawable.colour8);

		bitmapList.add(bitmap1);
		bitmapList.add(bitmap2);
		bitmapList.add(bitmap3);
		bitmapList.add(bitmap4);
		bitmapList.add(bitmap5);
		bitmapList.add(bitmap6);
		bitmapList.add(bitmap7);
		bitmapList.add(bitmap8);

		bitmapList.addAll(bitmapList);

		imgWidth = bitmapBg.getWidth();
		imgHeight = bitmapBg.getHeight();

	}

	public Bitmap getRandomBitmap() {

		int i = random.nextInt(bitmapList.size());
		Bitmap bitmap = bitmapList.get(i);

		bitmapList.remove(i);
		return bitmap;
	}

	public Cell getTouchedCell(MotionEvent event) {

		int i = (int) event.getX();
		int j = (int) event.getY();

		if (i > MainActivity.gameStatus.cells[0][0].getRectF().left
				&& i < MainActivity.gameStatus.cells[0][0].getRectF().right) {
			i = 0;
		} else if (i > MainActivity.gameStatus.cells[1][0].getRectF().left
				&& i < MainActivity.gameStatus.cells[1][0].getRectF().right) {
			i = 1;
		} else if (i > MainActivity.gameStatus.cells[2][0].getRectF().left
				&& i < MainActivity.gameStatus.cells[2][0].getRectF().right) {
			i = 2;
		}

		else if (i > MainActivity.gameStatus.cells[3][0].getRectF().left
				&& i < MainActivity.gameStatus.cells[3][0].getRectF().right) {
			i = 3;
		} else {
			i = -1;
		}

		if (j > MainActivity.gameStatus.cells[0][0].getRectF().top
				&& j < MainActivity.gameStatus.cells[0][0].getRectF().bottom) {
			j = 0;
		} else if (j > MainActivity.gameStatus.cells[0][1].getRectF().top
				&& j < MainActivity.gameStatus.cells[0][1].getRectF().bottom) {
			j = 1;
		} else if (j > MainActivity.gameStatus.cells[0][2].getRectF().top
				&& j < MainActivity.gameStatus.cells[0][2].getRectF().bottom) {
			j = 2;
		}

		else if (j > MainActivity.gameStatus.cells[0][3].getRectF().top
				&& j < MainActivity.gameStatus.cells[0][3].getRectF().bottom) {
			j = 3;
		} else {
			j = -1;
		}

		if (i == -1 || j == -1) {
			return null;
		} else {
			return MainActivity.gameStatus.cells[i][j];
		}
	}

	public void drawImage(Cell cell) {

		canvas = holder.lockCanvas(new Rect((int) cell.getRectF().left,
				(int) cell.getRectF().top, (int) cell.getRectF().right,
				(int) cell.getRectF().bottom));

		canvas.drawBitmap(cell.getImageBg(), null, cell.getRectF(), mPaint);

		holder.unlockCanvasAndPost(canvas);

		cell.setTurn(true);
	}

	public void drawBg(Cell cell) {

		canvas = holder.lockCanvas(new Rect((int) cell.getRectF().left,
				(int) cell.getRectF().top, (int) cell.getRectF().right,
				(int) cell.getRectF().bottom));

		canvas.drawBitmap(bitmapBg, null, cell.getRectF(), mPaint);

		holder.unlockCanvasAndPost(canvas);

		cell.setTurn(false);
	}

	public void drawBlank(Cell cell) {

		canvas = holder.lockCanvas(new Rect((int) cell.getRectF().left,
				(int) cell.getRectF().top, (int) cell.getRectF().right,
				(int) cell.getRectF().bottom));

		canvas.drawPaint(paint);

		holder.unlockCanvasAndPost(canvas);
	}

}
