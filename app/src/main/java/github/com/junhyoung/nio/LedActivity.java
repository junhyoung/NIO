package github.com.junhyoung.nio;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class LedActivity extends AppCompatActivity {
    Camera mCamera = null;
    ImageView On;
    ImageView Off;

    @Override
    protected void onPause() {
        if (mCamera != null) {
            mCamera.release();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);
        On=(ImageView)findViewById(R.id.on);
        Off=(ImageView)findViewById(R.id.off);


    }

    public void on(View v) {
        mCamera = Camera.open();
        Camera.Parameters mCameraParameter;
        mCameraParameter = mCamera.getParameters();
        mCameraParameter.setFlashMode("torch");
        mCamera.setParameters(mCameraParameter);
        On.setImageResource(R.drawable.temp);
        Off.setImageResource(R.drawable.temp);
    }

    public void off(View v) {
        if (mCamera != null) {
            mCamera.release();
        }
        On.setImageResource(R.drawable.temp);
        Off.setImageResource(R.drawable.temp);
    }
}
