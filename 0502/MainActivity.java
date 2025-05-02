package com.example.recycle_practice;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ImageItem> imageItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageItemList = new ArrayList<>();
        imageItemList.add(new ImageItem("풍경", R.drawable.image1));
        imageItemList.add(new ImageItem("바다", R.drawable.image2));
        imageItemList.add(new ImageItem("산", R.drawable.image3));

        ImageAdapter adapter = new ImageAdapter(imageItemList);
        recyclerView.setAdapter(adapter);
    }
}

