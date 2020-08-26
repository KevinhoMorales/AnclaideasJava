package com.kevinhomorales.anclaideas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dreamer extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private Button saveData;
    private EditText editText;
    public static final String mypreference = "mypref";
    public static final String Dreamer = "dreamerDescriptionKey";

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    private ImageView mPhotoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamer);
        setTitle("Soñador");

        mPhotoImageView = (ImageView) findViewById(R.id.imageView);


        editText = (EditText) findViewById(R.id.textViewDescription);

        saveData = (Button) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


        checkData();

    }

    private void checkData() {
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Dreamer)) {
            editText.setText(sharedpreferences.getString(Dreamer, ""));
        }
    }

    public void saveData() {
        String n = editText.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Dreamer, n);
        editor.commit();
        Toast.makeText(this, "Los datos han sido guardados con éxito", Toast.LENGTH_SHORT).show();
        onClickBack();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    public void onClickBack(){
        onBackPressed();
    }

    private void goToCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager())!=null){
            //startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
            File photoFile = null;
            photoFile = createFile();
        }
    }

    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String imgFileName = "IMG " + timeStamp + " ";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgFileName, ".jpg",storageDir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToCamera();
            } else {
                Toast.makeText(this, "Necesitas habilitar los permisos de la cámara", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Log.i("TAG", "SE IMPRIME EL BITMAP: " + bitmap);
                mPhotoImageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "Necesitas tomar una foto para que se muestre", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        goToCamera();
                    } else {
                        ActivityCompat.requestPermissions(Dreamer.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    goToCamera();
                }

                break;
            default:
                break;
        }

        return true;
    }

}