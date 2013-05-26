package com.accedo.game.model;

/**
 * The latest status of the game
 * 
 * @ClassName: GameStatus
 * @Description: TODO()
 * @author zzw
 */
public class GameStatus {

	// Present score
	public int score;

	// Count of fliped
	public int flipCount;

	// Count of finished
	public int finishcount;

	// Cell Array
	public Cell[][] cells;

	// The first fliped cell
	public Cell cell1;

	// The second fliped cell
	public Cell cell2;

}
