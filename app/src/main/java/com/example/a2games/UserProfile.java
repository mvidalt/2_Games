package com.example.a2games;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;

    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView nombre = findViewById(R.id.userName);
        TextView score2048 = findViewById(R.id.score2048Value);
        TextView scoreSenku = findViewById(R.id.scoreSenkuValue);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("username", "");
        int savedScore2048 = sharedPreferences.getInt("score2048", 0);
        int savedScoreSenku = sharedPreferences.getInt("scoreSenku", 0);
        nombre.setText(savedName);
        score2048.setText(String.valueOf(savedScore2048));
        scoreSenku.setText(String.valueOf(savedScoreSenku));

        profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setOnClickListener(v -> openGallery());
        imagePath = loadImagePathFromInternalStorage();
        if (!TextUtils.isEmpty(imagePath)) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                profileImageView.setImageBitmap(bitmap);
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Guardar la imagen en la memoria interna
                imagePath = saveImageToInternalStorage(bitmap);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("profile_images", Context.MODE_PRIVATE);
        File imagePath = new File(directory, "profile.jpg");

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath.getAbsolutePath();
    }

    private String loadImagePathFromInternalStorage() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("profile_images", Context.MODE_PRIVATE);
        File imagePath = new File(directory, "profile.jpg");
        if (imagePath.exists()) {
            return imagePath.getAbsolutePath();
        }
        return null;
    }

    public void goMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("imagePath", imagePath);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}