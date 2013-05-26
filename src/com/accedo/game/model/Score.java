package com.accedo.game.model;

/**
 * Entity of high scores
 * 
 * @ClassName: Score
 * @Description: TODO()
 * @author zzw
 */
public class Score {

	// Identification
	private int id;

	// The preson's name of the score
	private String name;

	// The score that achieved
	private int Score;

	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Score(String name, int score) {
		super();
		this.name = name;
		Score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

}
