package gdou.measurecamera.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gdou.measurecamera.R;
import gdou.measurecamera.component.MeasurePixelsView;

public class MeasureActivity extends AppCompatActivity {

    MeasurePixelsView mMeasurePixelsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        mMeasurePixelsView = (MeasurePixelsView) findViewById(R.id.measure_view);
    }

    /** 提供启动该类的Intent */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MeasureActivity.class);
        return intent;
    }
}
