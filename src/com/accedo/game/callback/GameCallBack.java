package com.accedo.game.callback;

/**
 * GameCallBack,handle the realtime event of the game
 * 
 * @ClassName: GameCallBack
 * @Description: TODO()
 * @author zzw
 * @date 2013-5-15 下午4:15:11
 */
public interface GameCallBack {

	public void setScore(int score);

	public void setTimeOut();

	public void setSuccess();
}
