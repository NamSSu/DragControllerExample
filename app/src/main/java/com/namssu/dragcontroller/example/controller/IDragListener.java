package com.namssu.dragcontroller.example.controller;

public interface IDragListener {

    void onPositiveDragged(boolean isXMove);
    void onNegativeDragged(boolean isXMove);
    void onDragComplete();

    void onYUpDragged();
    void onYDownDragged();
    void onXDragged();
}
