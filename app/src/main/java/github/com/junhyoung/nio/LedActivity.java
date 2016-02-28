package github.com.junhyoung.nio;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class LedActivity extends AppCompatActivity {
    Camera mCamera = null;
    ImageView light;
    boolean isOn=false;

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
        light=(ImageView)findViewById(R.id.light);


    }

    public void on(View v) {
        if(isOn){
            if (mCamera != null) {
                mCamera.release();
            }
            light.setImageResource(R.drawable.off);
            isOn=false;
        }
        else{
            mCamera = Camera.open();
            Camera.Parameters mCameraParameter;
            mCameraParameter = mCamera.getParameters();
            mCameraParameter.setFlashMode("torch");
            mCamera.setParameters(mCameraParameter);
            light.setImageResource(R.drawable.on);
            isOn=true;
        }





    }

}
