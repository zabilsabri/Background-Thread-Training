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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    ProgressBar progressBar;
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressBar = findViewById(R.id.spinnerr);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        executorService.execute(new ImageDownloadTask1("https://upload.wikimedia.org/wikipedia/commons/2/2d/Snake_River_%285mb%29.jpg"));
        executorService.execute(new ImageDownloadTask2("https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_1920,c_limit/phonepicutres-TA.jpg"));
        executorService.execute(new ImageDownloadTask3("https://upload.wikimedia.org/wikipedia/commons/c/c9/Tiligul_liman_5_Mb.jpg"));

    }

    private class ImageDownloadTask1 implements Runnable {

        private String imageUrl;
        ImageView image1;

        private ImageDownloadTask1(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        public void run() {

            image1 = findViewById(R.id.gambarr1);

            final Bitmap bitmap1 = downloadBitmap(imageUrl);

            handler.post(() -> {
                image1.setImageBitmap(bitmap1);
                image1.setVisibility(ImageView.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
            });
        }
    }

    private class ImageDownloadTask2 implements Runnable {

        private String imageUrl;
        ImageView image2;

        private ImageDownloadTask2(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        public void run() {

            image2 = findViewById(R.id.gambarr2);

            final Bitmap bitmap1 = downloadBitmap(imageUrl);

            handler.post(() -> {
                image2.setImageBitmap(bitmap1);
                image2.setVisibility(ImageView.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
            });
        }
    }

    private class ImageDownloadTask3 implements Runnable {

        private String imageUrl;
        ImageView image3;

        private ImageDownloadTask3(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        public void run() {

            image3 = findViewById(R.id.gambarr3);

            final Bitmap bitmap1 = downloadBitmap(imageUrl);

            handler.post(() -> {
                image3.setImageBitmap(bitmap1);
                image3.setVisibility(ImageView.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
            });
        }
    }

    private Bitmap downloadBitmap(String imageUrl){
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }
}