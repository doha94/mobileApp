package com.example.recycle_practice;

// ImageAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<ImageItem> itemList;

    public ImageAdapter(List<ImageItem> itemList) {
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.image_title);
            image = view.findViewById(R.id.image_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageItem item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
