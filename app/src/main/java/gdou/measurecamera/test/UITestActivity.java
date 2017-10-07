package gdou.measurecamera.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gdou.measurecamera.R;
import gdou.measurecamera.component.CursorView;

public class UITestActivity extends AppCompatActivity {

    CursorView mLeftCursorView;
    CursorView mRightCursorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitest);

        mLeftCursorView = (CursorView) findViewById(R.id.cursor_test_left);
        mRightCursorView = (CursorView) findViewById(R.id.cursor_test_right);
    }



    /** 提供启动该类的Intent */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, UITestActivity.class);
        return intent;
    }
}
