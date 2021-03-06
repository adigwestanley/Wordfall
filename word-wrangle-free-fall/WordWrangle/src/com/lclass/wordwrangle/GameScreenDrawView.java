package com.lclass.wordwrangle;

import java.util.ArrayList;
import java.util.Random;

import com.lclass.common.Constants;
import com.lclass.items.FallingLetter;
import com.lclass.items.RandomItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class GameScreenDrawView extends View{

	Context mContext;
	public Paint mPaint;
	
	public Handler mHandler;
	
	DisplayMetrics mDisplayMetrics;
	Random mRandom;
	
	public int mScore;
	public int mDifficulty;
	
	String mTargetWord;
	int mMoves;
	
	ArrayList<FallingLetter> mLetterList;
	ArrayList<RandomItem> boosterItems;
	
	String mTouchedString;
	GameScreenActivity parent;
	
    int numMoves;	
	int level;
	
	public GameScreenDrawView(Context context, DisplayMetrics dm, String targetWord, int level, int numMoves) {
		super(context);
		mContext = context;
		parent = (GameScreenActivity) context;
		
		//get height and width of screen
		this.mDisplayMetrics = dm;
		this.level = level;
		mScore = 0;
		mTargetWord = targetWord;
		mMoves = numMoves; //mTargetWord.length()+2;
		mLetterList = new ArrayList<FallingLetter>();
		boosterItems = new ArrayList<RandomItem>();
		mTouchedString = "";
		
		//get random int
		mRandom = new Random();
		createLetters();
		
		//instantiate runnable
		mHandler = new Handler();
		updateRunnable.run();
		
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{		
		for (FallingLetter letter : mLetterList) {
			if(letter.getVisible()) 
				canvas.drawBitmap(letter.image, letter.getLeft(), letter.getTop(), null);
			
		}
		
		for(RandomItem boost: boosterItems)
		{
			if(boost.getVisible())
				canvas.drawBitmap(boost.image, boost.getLeft(), boost.getTop(), null);	
		}

		
		invalidate();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(action){
		case MotionEvent.ACTION_DOWN:
			FallingLetter touchedLetter = getTouchedLetter(x,y);
			RandomItem touchedItem = getTouchedItem(x,y);
			if(touchedLetter != null)
			{
				MediaPlayer sounds;
				sounds=MediaPlayer.create(this.getContext(), R.raw.letter_clicked);
				sounds.start();
				
				mTouchedString += touchedLetter.getLetter().toLowerCase();
				mMoves--;
				parent.setMovesLeftText(mMoves);
				parent.setSeletedLettersText(mTouchedString);
				
				if( checkWord() )
				{
					//Game Over! Success
					SharedPreferences gameSettings = mContext.getSharedPreferences("com.lclass.wordwrangle", mContext.MODE_PRIVATE);
					Editor editor = gameSettings.edit();
					editor.putBoolean("success", true);
					editor.putInt("score", mMoves);
					editor.apply();
					
					parent.queueGameOver(Constants.GAME_OVER_SUCCESS);
				}
				else if( mMoves <= 0 )
				{
					//Game Over! Fail
					SharedPreferences gameSettings = mContext.getSharedPreferences("com.lclass.wordwrangle", mContext.MODE_PRIVATE);
					Editor editor = gameSettings.edit();
					editor.putBoolean("success", false);
					editor.putInt("score", mMoves);
					editor.apply();
					
					parent.queueGameOver(Constants.GAME_OVER_NO_MOVES_LEFT);
				}
			}
			else if(touchedItem != null)
			{
				
				MediaPlayer sounds;
				sounds=MediaPlayer.create(this.getContext(), R.raw.letter_clicked);
				sounds.start();
				//if the item is not uncovered, change it's image to a bomb/clock
				if(!(touchedItem.getUncovered()))
				{
					touchedItem.uncoverItem();
				}
				else{ //if it is uncovered, reset
					touchedItem.setVisible(false);
					if(touchedItem.getUncoveredItemInt() == Constants.ITEM_TIME_INCREASE)
						parent.increaseTime();
					else
						parent.decreaseTime();
				}
			}

			break;
		}
		invalidate();
		return true;
	}
	
	public FallingLetter getTouchedLetter(int x, int y)
	{
		for (FallingLetter letter : mLetterList) {
			
			if(letter.getLeft() < x && letter.getLeft() + letter.getWidth() > x && 
					letter.getTop() < y && letter.getTop() + letter.getHeight() > y &&
					letter.getVisible() ) {
				letter.setVisible(false);
				return letter;
				
			}
		}
		
		return null;
	}
	
	public RandomItem getTouchedItem(int x, int y)
	{
		for (RandomItem item : boosterItems) {
			
			if(item.getLeft() < x && item.getLeft() + item.getWidth() > x && 
					item.getTop() < y && item.getTop() + item.getHeight() > y &&
					item.getVisible() ) {
				return item;
			}
		}
		
		return null;
	}

	private void moveLetters() {
		for (FallingLetter letter : mLetterList) {
			letter.setTop(letter.getTop() + 10);
			
			if( letter.getTop() > mDisplayMetrics.heightPixels+letter.getHeight()+10 )
			{
				letter.resetletter();
				int iPosX = mRandom.nextInt(mDisplayMetrics.widthPixels-100);
				int iPosY = -150 - mRandom.nextInt(100) - letter.getHeight();
				
				if( null != getTouchedLetter(iPosX, iPosY) )
					continue;
				
				if( null != getTouchedLetter(iPosX+letter.getWidth(), iPosY+letter.getHeight()) )
					continue;
			
				letter.moveTo(iPosX, iPosY);
				letter.setVisible(true);
			}
		}
	}
	
	private void moveItems() {
		for (RandomItem item : boosterItems) {
			item.setTop(item.getTop() + 10);
			
			if( item.getTop() > mDisplayMetrics.heightPixels+item.getHeight()+10 )
			{
				item.reset();
				int iPosX = mRandom.nextInt(mDisplayMetrics.widthPixels-100);
				int iPosY = -150 - mRandom.nextInt(100) - item.getHeight();
				
				if( null != getTouchedLetter(iPosX, iPosY) )
					continue;
				
				if( null != getTouchedLetter(iPosX+item.getWidth(), iPosY+item.getHeight()) )
					continue;
			
				item.moveTo(iPosX, iPosY);
				item.setVisible(true);
			}
		}
	}
	
	private void createLetters()
	{
		
		FallingLetter newLetter;
		for( int i = 0 ; i < 25 ; i++){
			newLetter = new FallingLetter(mContext, 
				new Point(mRandom.nextInt(mDisplayMetrics.widthPixels-100), (-150*i)), 
				mRandom.nextInt(26));
			
			mLetterList.add(newLetter);
		}
		
		RandomItem newItem;
		for( int i = 0 ; i < 2; i++){
			newItem = new RandomItem(mContext, 
				new Point(mRandom.nextInt(mDisplayMetrics.widthPixels-100), (-300*i)));
			boosterItems.add(newItem);
		}
	}
	
	
	private boolean checkWord()
	{
		for (int i = 0; i < mTargetWord.length() ; i++) {
			char letter = mTargetWord.charAt(i);
			if( !mTouchedString.contains(letter + "") )
			{
				return false;				
			}
		}
		
		return true;
	}
	
	
	protected Runnable updateRunnable = new Runnable() {

		int mCount = 0;
		public void run() {

			mCount++;
	
			moveLetters();
			moveItems();
			mHandler.postDelayed(updateRunnable, 10);
		}

	};
	
	public String getTargetWord()
	{
		return mTargetWord;
	}
	
	public String getTouchedString()
	{
		return mTouchedString;
	}
	
}
