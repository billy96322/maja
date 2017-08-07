package com.salmonzhg.maja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.salmonzhg.maja.core.Maja;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Maja.init(new MyStrategyAdapter(), MajaSerializerDispatcher.instance());
    }

}
