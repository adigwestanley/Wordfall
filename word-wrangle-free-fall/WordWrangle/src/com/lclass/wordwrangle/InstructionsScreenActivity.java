package com.lclass.wordwrangle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InstructionsScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions_screen);
	}

	
	public void goBack(View v)
	{
		Intent in = new Intent(this, MenuScreenActivity.class);
		startActivity(in);
		overridePendingTransition(R.anim.translate_down_onscreen, R.anim.translate_down_offscreen);
		finish();
	}
	
	
	@Override
	public void onBackPressed(){
		Intent in = new Intent(this, MenuScreenActivity.class);
		startActivity(in);
		overridePendingTransition(R.anim.translate_down_onscreen, R.anim.translate_down_offscreen);
		finish();
		super.onBackPressed();
	}
}
