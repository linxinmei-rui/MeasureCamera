package gdou.measurecamera.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import gdou.measurecamera.R;

public class TakePhotoActivity extends AppCompatActivity {

    //-------- attr --------
    static final int REQ_IMAGE_CAPTURE = 1;

    Button mBOpenCamera;
    ImageView mImageView;
    Uri imageUri;


    //-------- method --------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        mBOpenCamera = (Button) findViewById(R.id.button_open_camera);
        mImageView = (ImageView) findViewById(R.id.show_picture);

        addListener();
    }

    /** 提供启动该类的Intent */
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, TakePhotoActivity.class);
        return intent;
    }

    /** 委派拍照请求，调用设备的相机应用。*/
    private void dispatchTakePictureIntent(Uri imageUri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (imageUri != null)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, REQ_IMAGE_CAPTURE);
        }
    }

    /** 处理委派设备相机应用的结果 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap imageBitmap = null;
            try {
                imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int width = imageBitmap.getWidth();
            int height = imageBitmap.getHeight();

//            mImageView.setImageBitmap(Bitmap.createBitmap(imageBitmap, 0, height/2, width, height/4));
            mImageView.setImageBitmap(Bitmap.createBitmap(imageBitmap, 0, 0, width, height ));
           /* Matrix matrix = new Matrix();
            matrix.postScale(0.2f, 0.2f);
            Canvas canvas = new Canvas();
            canvas.drawBitmap(imageBitmap, 0, 0, new Paint());
//            canvas.drawBitmap(imageBitmap, matrix, new Paint());
            mImageView.draw(canvas);*/
        }
    }



    //-------- add listener --------
    void addListener(){
        addListenerToTakePhoto();
    }

    /** 添加监听：调用设备的相机应用。 */
    void addListenerToTakePhoto(){
        mBOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = new File(getExternalCacheDir(), "output_image.jpg");
                if (imageFile.exists()){
                    imageFile.delete();
                }
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(TakePhotoActivity.this,
                            "gdou.measurecamera.activity.TakePhotoActivity", imageFile);
                }else {
                    imageUri = Uri.fromFile(imageFile);
                }
                dispatchTakePictureIntent(imageUri);
            }
        });
    }

}
