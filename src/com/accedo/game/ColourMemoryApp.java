package com.accedo.game;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.accedo.game.util.Logger;
import com.accedo.game.util.MemoryPreference;

/**
 * Contain global variable and method
 * 
 * @ClassName: ColourMemoryApp
 * @Description: TODO()
 * @author zzw
 */
public class ColourMemoryApp extends Application {

	Logger logger;

	// Screen Size
	public DisplayMetrics dm;

	// IMEI,IMSI
	public String IMEI, IMSI;

	// Is not simulator
	public boolean isEmulator;

	// Device model
	public String phoneModel;

	// SDK version
	public int sdkVersion;

	// Phone number
	public String phoneNum;

	public Typeface tf;

	public Vibrator vibrator;

	public MediaPlayer miMediaPlayer;

	public long[] pattern = { 100, 250 };

	// Sound or vibrate is available
	public boolean isSoundOn, isVibrateOn;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// Get the phone information
		getPhoneParams();
	}

	/**
	 * Get the device information and so some init work
	 * 
	 * @Title: getPhoneParams
	 * @Description: TODO()
	 * @param
	 * @return void
	 * @throws
	 */
	public void getPhoneParams() {

		// Get information from the device
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();
		IMSI = telephonyManager.getSubscriberId();

		phoneNum = telephonyManager.getLine1Number();
		phoneModel = Build.MODEL;
		sdkVersion = Build.VERSION.SDK_INT;
		dm = new DisplayMetrics();
		if (IMEI != null) {
			isEmulator = IMEI.equals("000000000000000");
		} else {
			IMEI = Secure.getString(getApplicationContext()
					.getContentResolver(), Secure.ANDROID_ID);
		}

		tf = Typeface.createFromAsset(getAssets(), "haibao.jpg");

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		telephonyManager = null;
		System.gc();
	}

	/**
	 * Vibrate the device if there is one
	 * 
	 * @Title: vibrate
	 * @Description: TODO()
	 * @param
	 * @return void
	 * @throws
	 */
	public void vibrate() {

		if (isVibrateOn) {
			vibrator.vibrate(pattern, -1);
		}
	}

	/**
	 * Whether the present version is compatible
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * Play the sound effect
	 * 
	 * @Title: playAudio
	 * @Description: TODO()
	 * @param @param index
	 * @return void
	 * @throws
	 */
	public void playAudio(int index) {
		int res = 0;
		switch (index) {
		case MemoryPreference.RIGHT_ANSWER: {
			res = R.raw.right_answer;
			break;
		}
		case MemoryPreference.WRONG_ANSWER: {
			res = R.raw.wrong_answer;
			break;
		}
		case MemoryPreference.GAME_WIN: {
			res = R.raw.game_win;
			break;
		}
		case MemoryPreference.GAME_OVER: {
			res = R.raw.game_over;
			break;
		}
		case MemoryPreference.CLICK: {
			res = R.raw.click;
			break;
		}
		}

		if (miMediaPlayer != null && miMediaPlayer.isPlaying()) {
			miMediaPlayer.stop();
			miMediaPlayer.release();
		}

		if (isSoundOn) {
			miMediaPlayer = MediaPlayer.create(this, res);
			miMediaPlayer.start();
		}
	}

}
