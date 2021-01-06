package com.namssu.dragcontroller.example.controller;


import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class DragController {
    private int selectedPos;

    private float startYPos;
    private float startXPos;
    private static final int DRAG_THRESHOLD = 5;
    private HashMap<String, IDragListener> dragListeners;
    private String tag;

    private static final String TAG = "DragController";

    private boolean oneDragged = false;
    private boolean onePositive = false;

    private boolean xDragged = false;
    private boolean yUpDragged = false;
    private boolean yDownDragged = false;

    public static final String TAG_VALUE1 = "VALUE1";

    private static class DragControllerHolder {
        private static DragController instance = new DragController();
    }

    public static DragController getInstance() {
        return DragControllerHolder.instance;
    }

    private DragController() {
        this.selectedPos = -1;
        this.startXPos = 0;
        this.startYPos = 0;
        this.dragListeners = new HashMap<>();
    }

    public void addDragListener(String tag, IDragListener listener) {
        this.dragListeners.put(tag, listener);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void onDragged(MotionEvent ev) {
        switch(ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startYPos = ev.getY();
                startXPos = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float xDragPercent = (startXPos - ev.getX());
                float yDragPercent = (startYPos - ev.getY());

                if (startXPos > 0 ) {
                    if (xDragPercent > DRAG_THRESHOLD || xDragPercent < -DRAG_THRESHOLD) {
                        IDragListener listener = dragListeners.get(tag);

                        xDragged = true;
                        oneDragged = true;

                        if (xDragPercent < -DRAG_THRESHOLD){
                            onePositive = true;
                        }
                        startXPos = ev.getX();
                        startYPos = ev.getY();
                    } else if (yDragPercent > DRAG_THRESHOLD || yDragPercent < -DRAG_THRESHOLD) {
                        if (yDragPercent > DRAG_THRESHOLD) {
                            if (tag != null && tag.length() > 0) {
                                IDragListener listener = dragListeners.get(tag);
                                if (listener != null) {
                                    listener.onPositiveDragged(false);
                                    yUpDragged = true;
                                }
                            }
                        } else if (yDragPercent < -DRAG_THRESHOLD) {
                            if (tag != null && tag.length() > 0) {
                                IDragListener listener = dragListeners.get(tag);
                                if (listener != null) {
                                    listener.onNegativeDragged(false);
                                    yDownDragged = true;
                                }
                            }
                        }
                        startXPos = ev.getX();
                        startYPos = ev.getY();
                    }
                    break;
                }
                break;

            // 디바이스 화면의 1% 이상 드래그하였다면 값 변경
            case MotionEvent.ACTION_UP:
                startXPos = 0;
                startYPos = 0;

                if (tag != null && tag.length() > 0) {
                    IDragListener listener = dragListeners.get(tag);

                    if (listener != null) {
                        if (oneDragged) {
                            if (onePositive) {
                                listener.onPositiveDragged(true);
                            } else {
                                listener.onNegativeDragged(false);
                            }
                            oneDragged = false;
                            onePositive = false;
                        }

                        if (xDragged) {
                            listener.onXDragged();
                            xDragged = false;
                        }

                        if (yUpDragged) {
                            listener.onYUpDragged();
                            yUpDragged = false;
                        }

                        if (yDownDragged) {
                            listener.onYDownDragged();
                            yDownDragged = false;
                        }

                        listener.onDragComplete();
                    }
                }
                break;

            default:
                break;
        }
    }
}