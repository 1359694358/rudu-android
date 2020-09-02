package com.google.android.app.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;


public class RoundAngleImageView extends androidx.appcompat.widget.AppCompatImageView {

	public int radius = 0;

	public boolean topRadius=false;

	public RoundAngleImageView(Context context) {
		this(context, null);
	}

	public RoundAngleImageView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundAngleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(radius>0) {
			float[] radii;
			if(topRadius)
			{
				radii= new float[]{radius, radius,radius, radius, 0, 0,0,0};
			}
			else
			{
				radii= new float[]{radius, radius, radius, radius,radius, radius, radius, radius};
			}
			Path path = new Path();
			path.addRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), radii, Path.Direction.CW);
			canvas.clipPath(path);
		}
		super.onDraw(canvas);
	}


}