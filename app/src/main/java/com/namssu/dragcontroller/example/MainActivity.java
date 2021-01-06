package com.namssu.dragcontroller.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.namssu.dragcontroller.example.controller.DragController;
import com.namssu.dragcontroller.example.controller.IDragListener;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = getApplicationContext();

        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DragController.getInstance().setTag(DragController.TAG_VALUE1);
                return true;
            }
        });

        DragController.getInstance().addDragListener(DragController.TAG_VALUE1, new IDragListener() {
            @Override
            public void onPositiveDragged(boolean isXMove) {

            }

            @Override
            public void onNegativeDragged(boolean isXMove) {

            }

            @Override
            public void onDragComplete() {

            }

            @Override
            public void onYUpDragged() {
                makeToast("onYUpDragged");
            }

            @Override
            public void onYDownDragged() {
                makeToast("onYDownDragged");
            }

            @Override
            public void onXDragged() {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DragController.getInstance().onDragged(ev);
        return super.dispatchTouchEvent(ev);
    }

    public static void makeToast(String value) {
        if (value != null) {
            Toast.makeText(mainContext, value, Toast.LENGTH_SHORT).show();
        }
    }
}