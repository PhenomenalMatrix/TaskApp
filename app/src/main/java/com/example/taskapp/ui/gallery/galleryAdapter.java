package com.example.taskapp.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.ui.OnitemCickListner;
import com.example.taskapp.ui.home.TaskAdapter;

import java.io.File;
import java.util.ArrayList;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.ViewHolderGallery> {

   private ArrayList<File> list;

    public galleryAdapter(ArrayList<File> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dcim, parent,false);
        return new ViewHolderGallery(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGallery holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderGallery extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolderGallery(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewGall);
        }

        public void onBind(File file) {
            textView.setText(file.getName());
        }
    }

}
