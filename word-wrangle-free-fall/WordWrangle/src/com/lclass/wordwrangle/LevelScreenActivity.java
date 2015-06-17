package com.lclass.wordwrangle;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.lclass.actor.Level;
import com.lclass.common.Constants;
import com.lclass.common.MusicKeys;
import com.lclass.common.SoundFX;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LevelScreenActivity extends Activity implements OnClickListener{
	
	
	DatabaseHandler db;
	private ImageButton go;
	private LinearLayout levelLayout;
	
	private Typeface font;
	String	word ;
	TextView textView1;
	ArrayList<Level> levels;
	int selectedLevel;
	int numMoves;
	public LinearLayout adHolder;
	
	private SoundFX sound;
	 
		@Override
		 protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		     setContentView(R.layout.layout_levelscreen_new);
		    
		     textView1 = (TextView) findViewById(R.id.textView1);
			 go = (ImageButton) findViewById(R.id.go_button);
			 levelLayout = (LinearLayout) findViewById(R.id.layout_level_select);
		     adHolder = (LinearLayout) findViewById (R.id.adholder);
		     
		     //insert ads
		     AdRequest adreq=new AdRequest();
			 AdView adView = new AdView(this, AdSize.SMART_BANNER, Constants.PUBLISHER_ID);
			 adHolder.addView(adView);
			 adView.loadAd(adreq);
		     
		     levels = new ArrayList<Level>();
		     db = new DatabaseHandler(this);
				
				try{
					db.createDataBase();
					db.openDataBase();
				}catch(Exception e){
				
				}
				
			 levels = db.getLevels();
			 db.close();
			 word = "";
			 
			 //sounds
			 sound = new SoundFX();
			 initializeSounds();
			 
			 //set font
			 font = Typeface.createFromAsset(this.getAssets(), Constants.CARTOON_FONT); 
			 textView1.setTypeface(font);
			 go.setOnClickListener(this);
			 addLevelstoLayout();
		 }
		
		
		@SuppressWarnings("deprecation")
		@SuppressLint("ResourceAsColor") public void addLevelstoLayout()
		{
			int levelCounter = 1;
			LinearLayout.LayoutParams outsideContainterParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);outsideContainterParams.setMargins(20, 10, 20, 10);
			LayoutParams titleContainterParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			//create a layout for each level
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT, 1);
			
			for(int i = 0; i < ((int) levels.size()/3); i++)
			{
				LinearLayout outsideContainer = new LinearLayout(this.getApplicationContext());
				outsideContainer.setLayoutParams(outsideContainterParams);
				outsideContainer.setOrientation(LinearLayout.HORIZONTAL);
				outsideContainer.setPadding(8, 8, 8, 4);
				
				for(int j = 0; j < 3; j++)
				{
					
					LinearLayout innerLayout = new LinearLayout(this.getApplicationContext());
					innerLayout.setLayoutParams(param);
					innerLayout.setOrientation(LinearLayout.VERTICAL);
					innerLayout.setOnClickListener(this);
					innerLayout.setTag(Integer.valueOf(levelCounter));
					//innerLayout = (LinearLayout) findViewById(R.layout.layout_levelscreen_new);
					
					ImageView levelIm = new ImageView(this.getApplicationContext());
					//levelIm = (ImageView) findViewById(R.id.level_image);
					
					TextView levelText = new TextView(this.getApplicationContext());
				    levelText.setTypeface(font);
				    levelText.setTextColor(R.color.black);
					levelText.setText("Level " + (levelCounter));
					levelText.setGravity(Gravity.CENTER);
					levelText.setPadding(0, 0, 0, 0);
					
					//set transparency of image if locked
					if(levels.get(levelCounter -1).getEnabled() == 0)
					{
						levelIm.setImageResource(R.drawable.letter_x_mark);
					}
					else{
						//if the next level hasnt been played yet put exclamation mark
						if(((levelCounter + 1) < levels.size()) && !(levels.get(levelCounter).getEnabled() == 0))
							levelIm.setImageResource(R.drawable.letter_check_mark);
						else
							levelIm.setImageResource(R.drawable.letter_exclamation);
					}
					levelIm.setPadding(0, 2, 0, 2);
					
					innerLayout.addView(levelIm);
					innerLayout.addView(levelText);
					outsideContainer.addView(innerLayout);
					
					levelCounter++;
				}
				
				levelLayout.addView(outsideContainer);
				
			}
			
		}
		
		@Override
		public void onClick(View v) {
			
			//Intent intent = null;
			int id = v.getId();
			if(id == R.id.go_button){
				Intent intent = new Intent(LevelScreenActivity.this, GameScreenActivity.class);
				if (word.equalsIgnoreCase("")){
					textView1.setText("No Level Selected");
				}
				else{
					intent.putExtra("LEVEL", selectedLevel);
					startActivity(intent);
					overridePendingTransition(R.anim.translate_up_onscreen, R.anim.translate_up_offscreen);
					finish();
				}
			}
			else{//if the user clicked on a level
				int tag = ((Integer) v.getTag()) -1;
				if(levels.get(tag).getEnabled() == 1){
					 sound.playSound(MusicKeys.POP, 0);
					 word = levels.get(tag).getWord();
					 textView1.setText("Word to Spell: "+ word);
					 selectedLevel = tag+1;
				 }
				 else{
					 sound.playSound(MusicKeys.ERROR, 0);
					 textView1.setText("Level Locked");
					 word = "";
				 }
			}
		}
		
   @Override
   public void onBackPressed()
   {
	   super.onBackPressed();
	   overridePendingTransition(R.anim.translate_down_onscreen, R.anim.translate_down_offscreen);
   }
   
   private void initializeSounds() {
		this.sound.initSounds(getApplicationContext());
		this.sound.addSound(MusicKeys.GAME_OVER_WIN, R.raw.game_over_win);
		this.sound.addSound(MusicKeys.GAME_OVER_LOSS, R.raw.game_over_lose);
		this.sound.addSound(MusicKeys.COUNTDOWN_BEEP, R.raw.sound_countdown_beep);
		this.sound.addSound(MusicKeys.POP, R.raw.sound_pop);
		this.sound.addSound(MusicKeys.ERROR, R.raw.wrong_selection);
	}
		
}


