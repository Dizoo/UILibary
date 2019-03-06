package com.dizoo.takephoto;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    private Button selecteImage;
    private ImageView image;
    private BottomDialog mBottomDialog;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private static final int CAMERA_CODE = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selecteImage = findViewById(R.id.selecte_image);
        image = findViewById(R.id.image);
        imageResult();
        selecteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoPicker();
            }
        });
        if (!PermissionUtil.isPermissionGranted(this, Manifest.permission.CAMERA)
                || !PermissionUtil.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !PermissionUtil.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionGen.needPermission(this, CAMERA_CODE, perms);
            }
        }
    }

    private void imageResult() {
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(final File outputFile, Uri outputUri) {
                Glide.with(MainActivity.this).load(outputFile).into(image);
            }
        }, true);//true裁剪，false不裁剪
    }

    private void showPhotoPicker() {
        mBottomDialog = new BottomDialog(this, true, true);
        View contentView = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_choose_photo, null);
        mBottomDialog.setContentView(contentView);

        mBottomDialog.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLqrPhotoSelectUtils.selectPhoto();
                mBottomDialog.dismiss();
            }
        });

        mBottomDialog.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请权限
                requestPermissions();
                mBottomDialog.dismiss();
            }
        });

        mBottomDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomDialog.dismiss();
            }
        });

        mBottomDialog.show();
    }

    private void requestPermissions() {
        if (!PermissionUtil.isPermissionGranted(this, Manifest.permission.CAMERA)
                || !PermissionUtil.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !PermissionUtil.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionGen.needPermission(this, CAMERA_CODE, perms);
            }
        } else {
            mLqrPhotoSelectUtils.takePhoto();
        }
    }

    @PermissionSuccess(requestCode = CAMERA_CODE)
    public void permissionSuccess() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionFail(requestCode = CAMERA_CODE)
    public void permissionFailed() {
        //权限获取失败
        Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
