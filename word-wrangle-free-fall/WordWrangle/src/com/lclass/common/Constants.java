package com.lclass.common;

import com.lclass.wordwrangle.R;

public class Constants {

	public static final String SHARED_PREFERENCE_NAME = "GameSettings";
	public static final String SETTINGS_VOLUME_KEY = "volume";
	
	public static final int SETTINGS_VOLUME_ON = 1;
	public static final int SETTINGS_VOLUME_OFF = 0;
	
	public final static String RUNNING_FONT = "fonts/running.ttf";
	public final static String CARTOON_FONT = "fonts/big_bottom.ttf";
	
	public final static String PUBLISHER_ID = "ca-app-pub-9943604836792192/1800378865";
	
	public final static int ITEM_TIME_DECREASE = 0;
	public final static int ITEM_TIME_INCREASE = 1;
	
	//how the player won the game
	public final static int GAME_OVER_TIME = 0;
	public final static int GAME_OVER_NO_MOVES_LEFT = 1;
	public final static int GAME_OVER_SUCCESS = 2;
	
	public static final int COUNTDOWN_FROM_VALUE = 4;
	
	public static int selectNumber(int i)
	{
		
		switch(i){
		case 0:  return R.drawable.number_0;
		
		case 1:  return R.drawable.number_1;
		
		case 2:  return R.drawable.number_2;
		
		case 3:  return R.drawable.number_3;
		
		case 4:  return R.drawable.number_4;
		
		case 5:  return R.drawable.number_5;
		
		case 6:  return R.drawable.number_6;
		
		case 7:  return R.drawable.number_7;
		
		case 8:  return R.drawable.number_8;
		
		case 9:  return R.drawable.number_9;
		
		default: return R.drawable.number_0;
		}
	}
	
	public static int selectLetter(int i)
	{
		switch(i){
		case 0:  return R.drawable.letter_a;
		
		case 1:  return R.drawable.letter_b;
		
		case 2:  return R.drawable.letter_c;
		
		case 3:  return R.drawable.letter_d;
		
		case 4:  return R.drawable.letter_e;
		
		case 5:  return R.drawable.letter_f;
		
		case 6:  return R.drawable.letter_g;
		
		case 7:  return R.drawable.letter_h;
		
		case 8:  return R.drawable.letter_i;
		
		case 9:  return R.drawable.letter_j;
		
		case 10:  return R.drawable.letter_k;
		
		case 11:  return R.drawable.letter_l;
		
		case 12:  return R.drawable.letter_m;
		
		case 13:  return R.drawable.letter_n;
		
		case 14:  return R.drawable.letter_o;
		
		case 15:  return R.drawable.letter_p;
		
		case 16:  return R.drawable.letter_q;
		
		case 17:  return R.drawable.letter_r;
		
		case 18:  return R.drawable.letter_s;
		
		case 19:  return R.drawable.letter_t;
		
		case 20:  return R.drawable.letter_u;
		
		case 21:  return R.drawable.letter_v;
		
		case 22:  return R.drawable.letter_w;
		
		case 23:  return R.drawable.letter_x;
		
		case 24:  return R.drawable.letter_y;
		
		case 25:  return R.drawable.letter_z;
		
		default: return R.drawable.letter_a;
		
		}
	}
	
	public static int selectNumberCharacter(char i)
	{
		
		switch(i){
		case '0':  return R.drawable.number_0;
		
		case '1':  return R.drawable.number_1;
		
		case '2':  return R.drawable.number_2;
		
		case '3':  return R.drawable.number_3;
		
		case '4':  return R.drawable.number_4;
		
		case '5':  return R.drawable.number_5;
		
		case '6':  return R.drawable.number_6;
		
		case '7':  return R.drawable.number_7;
		
		case '8':  return R.drawable.number_8;
		
		case '9':  return R.drawable.number_9;
		
		default: return R.drawable.number_0;
		}
	}
}
