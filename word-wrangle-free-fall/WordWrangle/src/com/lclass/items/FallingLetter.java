package com.lclass.items;

import java.util.Random;

import com.lclass.common.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class FallingLetter {
	public Bitmap image;
	public final int halfWidth;
	public final int halfHeight;
	
	public Point initialPosition;
	public Point currentPosition;
	
	public Boolean isVisible;
	public int mLetter;
	public Random random;
	
	public Context c;
	
	public FallingLetter(Context c, Point point, int iLetter)
	{
		random=new Random();
		this.c = c;
		int sizeRand = random.nextInt(60) + 50;
		this.image = BitmapFactory.decodeResource(c.getResources(), Constants.selectLetter(iLetter));
		this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
		this.initialPosition = new Point(point.x, point.y);
		this.currentPosition = new Point(point.x, point.y);
		
		isVisible = true;
		this.halfWidth = image.getWidth() /2;
		this.halfHeight = image.getHeight() /2;
		
		mLetter = iLetter+'A';
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
	
	public void resetletter()
	{
		int randomNum = random.nextInt(26);
		int sizeRand = random.nextInt(60) + 80;
		 mLetter = randomNum +'a';
		this.image = BitmapFactory.decodeResource(c.getResources(), Constants.selectLetter(randomNum));
		this.image = Bitmap.createScaledBitmap(this.image, sizeRand, sizeRand, false);
	}
	
	public void resetCurrentPosition()
	{
		
		currentPosition = initialPosition;
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
	
	public String getLetter()
	{
		return String.format("%c", mLetter);
	}
	
}
