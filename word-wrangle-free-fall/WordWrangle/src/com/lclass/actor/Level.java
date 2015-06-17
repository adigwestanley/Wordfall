package com.lclass.actor;

public class Level {

	int enabled;
	int numMoves;
	int timeToComplete;
	String word;
	
	
	public Level(String word, int  numMoves, int timeToComplete, int enabled)
	{
		this.word = word;
		this.enabled = enabled;
		this.numMoves = numMoves;
		this.timeToComplete = timeToComplete;
	}

	public Level(String word, int  numMoves, int timeToComplete)
	{
		this.word = word;
		this.numMoves = numMoves;
		this.timeToComplete = timeToComplete;
	}
	
	public int getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
