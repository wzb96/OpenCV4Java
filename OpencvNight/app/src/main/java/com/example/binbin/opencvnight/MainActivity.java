package com.example.binbin.opencvnight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView img;

    private Bitmap srcBitmap;
    private Bitmap grayBitmap;
    private static boolean flag = true;
    private static boolean isFirst = true;


    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.img);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new ProcessClickListener());


    }

    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    Log.i("TAG", "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i("TAG", "加载失败");
                    break;
            }
        }
    };



    public void procSrc2Gray(){
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hh);
        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
        Log.i("TAG", "procSrc2Gray sucess...");
    }


    public class ProcessClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(isFirst){
                procSrc2Gray();
                isFirst = false;
            }
            if(flag){
                img.setImageBitmap(grayBitmap);
                btn.setText("查看原图");
                flag = false;
            }else{
                img.setImageBitmap(srcBitmap);
                btn.setText("灰度化");
                flag = true;
            }
        }
    }

}
