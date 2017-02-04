package com.zhaolinger.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 目前只适配到6.0
 * 调用系统相机 将拍摄好的照片进行存储、裁剪压缩、显示
 * 调用系统图库 将选择的照片进行读取（获取照片真实路径）、裁剪压缩、显示
 * 避免意外销毁带来的麻烦 保存图片的存放路径
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_camera)
    Button buttonCamera;
    @BindView(R.id.button_photo)
    Button buttonPhoto;
    @BindView(R.id.show_image)
    ImageView showImage;
    private static final int CALL_CAMERA = 1;
    private static final int CALL_PHOTO = 2;
    private static final int CALL_CROP = 3;
    private Uri imageUri;
    private static String SAVE_URI_STRING = "uri_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(null != savedInstanceState){
            String uri_string = savedInstanceState.get(SAVE_URI_STRING).toString();
            imageUri = Uri.parse(uri_string);
            Log.i("test","oncreate imageUri=="+imageUri);
        }
        showImage(); //横竖屏切换时 图片依然显示
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(null != imageUri){
            outState.putString(SAVE_URI_STRING,imageUri.toString());
        }else{
            outState.putString(SAVE_URI_STRING,""); //避免onCreate中savedInstanceState.get(SAVE_URI_STRING)==null
        }
    }

    @OnClick(R.id.button_camera)
    public void onclickButtonCamera() {
        //创建File对象，存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "call_camera.jpg");//存放路径(应用关联缓存目录)、图片名称
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File文件转换成Uri对象(7.0系统另外处理)
        imageUri = Uri.fromFile(outputImage);
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片的输出地址 保证了拍下的照片会输出到imageUri（也就是call_camera.jpg）中
        startActivityForResult(intent, CALL_CAMERA);
    }

    @OnClick(R.id.button_photo)
    public void onclickButtonPhoto() {
        //调用系统相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,CALL_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CALL_CAMERA:
                if (resultCode == RESULT_OK) {
                    Log.i("test","imageUri=="+imageUri);
                    //调用系统裁剪
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    //intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CALL_CROP);
                }
                break;
            case CALL_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.i("test","uri==="+data.getData());
                }
                break;
            case CALL_CROP:
                //将裁剪后的照片显示出来
                if(resultCode == RESULT_OK){
                    showImage();
                }
                break;
            default:
                break;
        }
    }

    //显示图片（拍照）
    private void showImage(){
        try {
            if(null != imageUri){
                Log.i("test","uri=="+imageUri);
                Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream
                        (imageUri)); // 将Uri对象转换成bitmap
                showImage.setImageBitmap(bm);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
