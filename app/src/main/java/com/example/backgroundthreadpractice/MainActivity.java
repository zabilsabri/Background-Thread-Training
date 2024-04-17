package com.example.backgroundthreadpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ProgressBar progressBar;
    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.gambar);
        progressBar = findViewById(R.id.spinner);

        loadImage("https://upload.wikimedia.org/wikipedia/commons/2/2d/Snake_River_%285mb%29.jpg");
    }

    private void loadImage(String imageUrl){
        progressBar.setVisibility(ProgressBar.VISIBLE);

        executor.execute(() -> {
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e){
                e.printStackTrace();
            }

            Bitmap finalBitmap = bitmap;

            handler.post(() -> {
                progressBar.setVisibility(ProgressBar.GONE);
                imageView.setVisibility(ImageView.VISIBLE);
                if(finalBitmap != null){
                    imageView.setImageBitmap(finalBitmap);
                }
            });
        });
    }
}