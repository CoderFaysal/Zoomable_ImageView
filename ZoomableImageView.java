package com.coderfaysal.madrasah;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ZoomableImageView extends androidx.appcompat.widget.AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener {
    private Matrix matrix;
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;

    private PointF lastTouchPoint;

    public ZoomableImageView(Context context) {
        super(context);
        initialize(context);
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ZoomableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        matrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchPoint = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastTouchPoint.x;
                float dy = event.getY() - lastTouchPoint.y;
                lastTouchPoint.set(event.getX(), event.getY());
                matrix.postTranslate(dx, dy);
                setImageMatrix(matrix);
                break;
        }

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

        matrix.setScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
        setImageMatrix(matrix);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        // Nothing to do here
    }
}
