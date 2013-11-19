package com.sukohi.lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapTrim {
	
	public static final int MODE_CIRCLE = 0;
	public static final int MODE_OVAL = 1;
	public static final int MODE_ARC = 2;
	public static final int MODE_ROUND_RECT = 3;
	public static final int MODE_RECT = 4;
	private final int drawColor = 0xff424242;
	private Bitmap blankBitmap;
	private Paint paint = new Paint();
	private Canvas drawCanvas;
	
	private float trimX, trimY, trimRadius, trimStartAngle, trimSweepAngle;
	private RectF trimRectF;
	private boolean trimCenterFlag;
	private int mode;
	
	public BitmapTrim(int width, int height) {
		
		blankBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		drawCanvas = new Canvas(blankBitmap);
		
	}
	
	public void setTrimCircle(float cx, float cy, int radius) {
		
		mode = MODE_CIRCLE;
		trimX = cx;
		trimY = cy;
		trimRadius = radius;
		
	}
	
	public void setTrimOval(RectF rectF) {
		
		mode = MODE_OVAL;
		trimRectF = rectF;
		
	}
	
	public void setTrimArc(RectF rectF, float startAngle, float sweepAngle, boolean useCenter) {

		mode = MODE_ARC;
		trimRectF = rectF;
		trimStartAngle = startAngle;
		trimSweepAngle = sweepAngle;
		trimCenterFlag = useCenter;
		
	}
	
	public void setTrimRoundRect(RectF rectF, float rx, float ry) {

		mode = MODE_ROUND_RECT;
		trimRectF = rectF;
		trimX = rx;
		trimY = ry;
		
	}
	
	public void setTrimRect(RectF rectF) {
		
		mode = MODE_RECT;
		trimRectF = rectF;
		
	}
	
	public void drawBitmap(Bitmap drawBitmap, RectF rectF) {
		
		drawCanvas.drawBitmap(drawBitmap, null, rectF, null);
		
	}
	
	public void drawBitmap(Bitmap drawBitmap, Rect rect) {
		
		drawCanvas.drawBitmap(drawBitmap, null, rect, null);
		
	}
	
	public void drawBitmap(Bitmap drawBitmap, float left, float top) {
		
		drawCanvas.drawBitmap(drawBitmap, left, top, null);
		
	}
	
	public void setAntiAlias(boolean flag) {
		
		paint.setAntiAlias(flag);
		
	}
	
	public Bitmap getBitmap() {
		
		Bitmap trimBitmap = Bitmap.createBitmap(blankBitmap.getWidth(), blankBitmap.getHeight(), Config.ARGB_8888);
		Canvas trimCanvas = new Canvas(trimBitmap);

        paint.setColor(drawColor);
        trimCanvas.drawARGB(0, 0, 0, 0);
        
        switch (mode) {
        
		case MODE_CIRCLE:
			trimCanvas.drawCircle(trimX, trimY, trimRadius, paint);
			break;
		case MODE_OVAL:
			trimCanvas.drawOval(trimRectF, paint);
			break;
		case MODE_ARC:
			trimCanvas.drawArc(trimRectF, trimStartAngle, trimSweepAngle, trimCenterFlag, paint);
			break;
		case MODE_ROUND_RECT:
			trimCanvas.drawRoundRect(trimRectF, trimX, trimY, paint);
			break;
		case MODE_RECT:
			trimCanvas.drawRect(trimRectF, paint);
			break;
		default:
			break;
			
		}
        
        Rect rect = new Rect(0, 0, blankBitmap.getWidth(), blankBitmap.getHeight());
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        trimCanvas.drawBitmap(blankBitmap, rect, rect, paint);
        return trimBitmap;
		
	}
	
}
/***Sample

	BitmapTrim bitmapTrim = new BitmapTrim(width, height);
	bitmapTrim.setAntiAlias(true);	// Optional(default: false)
	
	// Set trim
	
	bitmapTrim.setTrimOval(new RectF(0, 0, width, height);		// Oval
	bitmapTrim.setTrimCircle(width/2, height/2, width/2);		// Circle
	bitmapTrim.setTrimRoundRect(								// Round rectangle(square)
			new RectF(0, 0, width, height), 
			width/10, 
			height/10
	);
	bitmapTrim.setTrimRect(new RectF(0, 0, width, height);		// Rectangle(square)
	
	int left = 0, top = 0;
	
	bitmapTrim.drawBitmap(bitmap1, left, top);
	bitmapTrim.drawBitmap(bitmap2, left, top);
	bitmapTrim.drawBitmap(bitmap3, left, top);
	scanedImageBitmap = bitmapTrim.getBitmap();

***/
