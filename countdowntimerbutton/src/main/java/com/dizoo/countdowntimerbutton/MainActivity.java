package com.dizoo.countdowntimerbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CountDownTimerButton.FinishCallback {

    private CountDownTimerButton countdown;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdown = findViewById(R.id.countdown);

        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdown.setEnableCountDown(true);
                countdown.startAction();
                countdown.setFinishCallback(MainActivity.this);
            }
        });
    }

    @Override
    public void countDownTimerFinish() {
        Toast.makeText(this, "倒计时到某个值会提示,默认是60秒", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countdown.removeCountDown();
    }
}
