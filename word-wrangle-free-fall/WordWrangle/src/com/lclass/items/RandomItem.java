package com.lclass.items;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.lclass.common.Constants;
import com.lclass.wordwrangle.R;
import com.lclass.wordwrangle.R.drawable;

public class RandomItem {

	public Bitmap image;
	public final int halfWidth;
	public final int halfHeight;
	
	public Point initialPosition;
	public Point currentPosition;
	
	public Boolean isVisible;
	public int mLetter;
	public Random random;
	
	public Context c;
	Boolean uncovered;
	
	public int sizeRand;
	public int uncoveredItemInt;
	
	public RandomItem(Context c, Point point)
	{
		random=new Random();
		this.c = c;
		this.initialPosition = new Point(point.x, point.y);
		this.currentPosition = new Point(point.x, point.y);
		reset();
		isVisible = true;
		this.halfWidth = image.getWidth() /2;
		this.halfHeight = image.getHeight() /2;
		
	}
	
	public Boolean getUncovered() {
		return uncovered;
	}

	public void coverItem() {
		this.uncovered = false;
	}
	
	public void uncoverItem(){
		this.uncovered = true;
		System.out.println("uncoveredItem int " + uncoveredItemInt);
		switch(uncoveredItemInt){
		
			case 0: //Constants.ITEM_TIME_DECREASE
			   this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.bomb_icon);
			   this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
			   break;
			case 1: //Constants.ITEM_TIME_INCREASE
			   this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.icon_straw);
			   this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
			   break;
			default:
			   this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.icon_straw);
			   this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
			   break;
		}
	}
	
	public int getUncoveredItemInt()
	{
		return uncoveredItemInt;
	}
	
	public Point getInitialPosition()
	{
		return initialPosition;
	}
	
	public void setVisible(Boolean bVisible)
	{
		isVisible = bVisible;
	}
	
	public Boolean getVisible()
	{
		return isVisible;
	}
	
	public Bitmap getImage()
	{
		return image;
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public void resetItem()
	{
		
	}
	
	public void reset()
	{
		uncovered = false;
		sizeRand = random.nextInt(60) + 80;
		this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.place_holder);
		this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
		this.uncoveredItemInt = random.nextInt(2);
		this.isVisible = true;
	}
	
	public void setLeft(int x)
	{
		this.currentPosition.x = x;
	}
	
	public void setTop(int y)
	{
		this.currentPosition.y = y;
	}
	
	public int getLeft()
	{
		return this.currentPosition.x;
	}
	
	public int getTop()
	{
		return this.currentPosition.y;
	}
	
	public void moveTo(int x, int y)
	{
		setLeft(x);
		setTop(y);
	}
	
}
