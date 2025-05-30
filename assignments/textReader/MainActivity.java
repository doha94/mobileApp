package com.example.textreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 100;

    private TextToSpeech tts;
    private ImageView imageView;
    private Button captureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        captureBtn = findViewById(R.id.captureBtn);

        // 권한 요청 (Android 13 이상 대응 포함)
        requestGalleryPermission();

        // TTS 초기화
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH); // 영어 문서 기준, 한글이면 Locale.KOREAN
            }
        });

        // 버튼 클릭 시 갤러리 열기
        captureBtn.setOnClickListener(v -> openGallery());
    }

    private void requestGalleryPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedImageUri);
                    bitmap = ImageDecoder.decodeBitmap(source);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                }

                imageView.setImageBitmap(bitmap);
                recognizeText(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "이미지를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void recognizeText(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(result -> {
                    String resultText = result.getText();
                    if (!resultText.isEmpty()) {
                        Toast.makeText(this, "텍스트 인식 성공!", Toast.LENGTH_SHORT).show();
                        tts.speak(resultText, TextToSpeech.QUEUE_FLUSH, null, null);
                        Log.d("TEXT_RESULT", resultText);
                    } else {
                        Toast.makeText(this, "텍스트를 인식하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "인식 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
