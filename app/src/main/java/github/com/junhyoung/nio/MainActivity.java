package github.com.junhyoung.nio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void  memo(View v){
        Intent intent = new Intent(getApplicationContext(),MemoActivity.class);
        startActivity(intent);
    }
    public void computer(View v){
        Intent intent = new Intent(getApplicationContext(),ComputeActivity.class);
        startActivity(intent);
    }
    public  void calender(View v){
        Intent intent = new Intent(getApplicationContext(),CalenderActivity.class);
        startActivity(intent);
    }
    public void led(View v){
        Intent intent = new Intent(getApplicationContext(),LedActivity.class);
        startActivity(intent);
    }
    public void call(View v){
        Intent intent = new Intent(getApplicationContext(),ShorteningActivity.class);
        startActivity(intent);
    }
    public void map(View v){
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(intent);
    }
}
