package com.example.gradlenew;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main3Activity extends AppCompatActivity {

    private TextView viewById;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = (TextView) findViewById(R.id.tv_btn);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clock();
            }
        });
    }

//    @DebugLog
//    void clock(){
//        Log.e("MainActivity","---->clock");
//    }
}
