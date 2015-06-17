package com.lclass.wordwrangle;

import com.lclass.common.Constants;
import com.lclass.common.MusicKeys;
import com.lclass.common.SoundFX;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameOverActivity extends Activity implements OnClickListener{

	TextView mTxtScore;
	TextView wordTV;
	ImageButton mBtnRetry;
	ImageButton mBtnContinue;
	DatabaseHandler db;
	int level;
	String targetWord;
	
	private SoundFX sound;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_gameoverscreen);
	    
	    Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	level = extras.getInt("LEVEL");
        	targetWord = extras.getString("TARGET_WORD");
        }
	    
	    mTxtScore = (TextView) findViewById(R.id.txt_result);
	    wordTV =  (TextView) findViewById(R.id.needed_word);
	    mBtnRetry = (ImageButton) findViewById(R.id.btnRetry);
	    mBtnContinue = (ImageButton) findViewById(R.id.btnContinue);
	    wordTV.setText("Word: " + targetWord);
	    
	    sound = new SoundFX();
	    initializeSounds();
	    
	    //fonts
	    Typeface font = Typeface.createFromAsset(this.getAssets(), Constants.CARTOON_FONT); 
	    mTxtScore.setTypeface(font);
	    wordTV.setTypeface(font);
	    
	    SharedPreferences gameSettings = getSharedPreferences("com.lclass.wordwrangle", MODE_PRIVATE);
	    boolean bSuccess = gameSettings.getBoolean("success", false);
	    
	    if( bSuccess )
	    {
	    	mTxtScore.setText("Level " + level + " Passed!");
	    	db = new DatabaseHandler(this);
				
				try{
					db.createDataBase();
					db.openDataBase();
				}catch(Exception e){
			 }
			 db.updateCompleted(level + 1);
			 db.close();
			 
			 mBtnContinue.setImageResource(R.drawable.text_continue);
	    }
	    else
	    {
	    	mTxtScore.setText("Level " + level + " Failed!");
	    	mBtnRetry.setVisibility(View.VISIBLE);
	    }

	    mBtnRetry.setOnClickListener(this);
	    mBtnContinue.setOnClickListener(this);
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId() == R.id.btnRetry )
		{
			sound.playSound(MusicKeys.COUNTDOWN_OVER, 0);
			Intent in = new Intent(this, GameScreenActivity.class);
			in.putExtra("LEVEL", level);
			startActivity(in);
			finish();
		}
		else if( v.getId() == R.id.btnContinue )
		{
			sound.playSound(MusicKeys.COUNTDOWN_OVER, 0);
			Intent in = new Intent(this, LevelScreenActivity.class);
			startActivity(in);
			finish();
		}
	}
	
	 private void initializeSounds() {
			this.sound.initSounds(getApplicationContext());
			this.sound.addSound(MusicKeys.COUNTDOWN_OVER, R.raw.sound_pop);
	}
}
