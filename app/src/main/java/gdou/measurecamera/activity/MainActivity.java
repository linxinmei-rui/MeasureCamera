package gdou.measurecamera.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import gdou.measurecamera.R;
import gdou.measurecamera.test.UITestActivity;

public class MainActivity extends AppCompatActivity {

    //-------- attr --------
    Button mBTakePhoto;
    Button mBMeasure;
    Button mBUitest;


    //-------- method --------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBTakePhoto = (Button) findViewById(R.id.button_open_camera);
        mBMeasure = (Button) findViewById(R.id.button_open_measure);

        mBUitest = (Button) findViewById(R.id.button_open_uitest);
        addListener();
    }



    //-------- add listener --------
    void addListener(){
        addListenerToTakePhoto();
        addListenerToOpenMeasure();
        addListenerToOpenUiTest();
    }

    /** 添加监听：拍摄照片按钮监听*/
    void addListenerToTakePhoto(){
        mBTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TakePhotoActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    /** 添加监听：打开测量界面按钮监听*/
    void addListenerToOpenMeasure(){
        mBMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MeasureActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    /** 添加监听：打开测试界面按钮监听*/
    void addListenerToOpenUiTest(){
        mBUitest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UITestActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}
