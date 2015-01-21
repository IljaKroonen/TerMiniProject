//package com.m2dl.nater.view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.TextView;
//
//*
// * Created by Romain on 15/01/2015.
//
//
//public class CustomTextView extends View implements View.OnTouchListener {
//    private TextView mDrawable;
//    int x = 10;
//    int y = 10;
//
//    public CustomTextView(Context context) {
//        super(context);
//    }
//
//    public CustomTextView(Context context, AttributeSet attr) {
//        super(context, attr);
//    }
//    protected void onDraw(Canvas canvas) {
//        mDrawable.setBounds(x, y, x + width, y + height);
//        mDrawable.draw(canvas);
//    }
//
//    public boolean onTouch(View arg0, MotionEvent arg1) {
//
//        switch (arg1.getAction()) {
//
//            case MotionEvent.ACTION_MOVE: {
//                x = (int) arg1.getX();
//                y = (int) arg1.getY();
//                invalidate();
//            }
//        }
//        return true;
//    }
