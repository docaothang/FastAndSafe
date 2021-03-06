package com.teamone.fastandsafe.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.teamone.fastandsafe.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemSanPham extends AppCompatActivity {
LinearLayout linearLayoutThemAnh;
ImageView imageView,thme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
     linearLayoutThemAnh=findViewById(R.id.themAnhSanPHam);
     imageView=findViewById(R.id.anhSanPham);
     thme=findViewById(R.id.them);
     thme.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Dialog dialog = new Dialog(ThemSanPham.this);
             dialog.setContentView(R.layout.themanh);
             Button button1 =dialog.findViewById(R.id.btnCamera);
             Button button2 = dialog.findViewById(R.id.btnStorage);
             dialog.show();
             button1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (ContextCompat.checkSelfPermission(ThemSanPham.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                         startActivityForResult(intent, 888);
                     }else {
                         ActivityCompat.requestPermissions(ThemSanPham.this,new String[]{Manifest.permission.CAMERA},999);
                     }
                     dialog.dismiss();
                 }
             });
             button2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                     intent.setType("image/*");
                     startActivityForResult(Intent.createChooser(intent,"Pick an image"),1);
                     dialog.dismiss();
                 }
             });

         }
     });
//     linearLayoutThemAnh.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
//
//         }
//     });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==888){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        if (resultCode==RESULT_OK && requestCode==1){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 888);
        }else {
            Toast.makeText(this,"ko mo dc camera",Toast.LENGTH_LONG).show();
        }
    }
}