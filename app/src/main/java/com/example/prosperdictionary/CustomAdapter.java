package com.example.prosperdictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

private ArrayList<DictObjectModel> dataSet=new ArrayList<DictObjectModel>();



    public CustomAdapter(ArrayList<DictObjectModel> filteredList) {
        this.dataSet=filteredList;

    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist,parent,false);
        return  new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
  holder.word.setText(dataSet.get(position).getWord());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public  class CustomViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView word;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
             word = itemView.findViewById(R.id.words);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            DictObjectModel selectedBook=dataSet.get(position);
            Intent intent=new Intent(view.getContext(),WordActivity.class);
            intent.putExtra("DictObjectModel", selectedBook);
            view.getContext().startActivity(intent);


        }
    }

}
