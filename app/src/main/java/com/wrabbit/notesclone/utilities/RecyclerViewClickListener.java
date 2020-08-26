package com.wrabbit.notesclone.utilities;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

    private OnClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerViewClickListener(Context context,
                                 final RecyclerView recycleView, final OnClickListener clicklistener) {

        this.clicklistener = clicklistener;
        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recycleView.findChildViewUnder(e.getX(),
                                e.getY());
                        if (child != null && clicklistener != null) {
                            clicklistener.onLongClick(child,
                                    recycleView.getChildAdapterPosition(child));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clicklistener != null
                && gestureDetector.onTouchEvent(e)) {
            clicklistener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }



}