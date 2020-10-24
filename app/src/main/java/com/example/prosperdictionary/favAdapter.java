package com.example.prosperdictionary;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class favAdapter extends RecyclerView.Adapter<favAdapter.favViewHolder> {
    private Context context;
  private static ArrayList<String> data = new ArrayList<>();

    public favAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        favAdapter.data =data;
    }

    @NonNull
    @Override
    public favViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favwordlist,parent,false);
        return new favViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final favViewHolder holder, final int position) {


        holder.mTextView.setText(data.get(position).trim());




               holder.mImageButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       final Dialog dialog=new Dialog(context);

                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                       dialog.setContentView(R.layout.dialog_design);
                       Window window=dialog.getWindow();
                       assert window != null;
                       window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                       final TextView Yes=dialog.findViewById(R.id.yes);
                       final  TextView cancel =dialog.findViewById(R.id.no);
                       Yes.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               data.get(position);
                               data.remove(position);
                               notifyItemRemoved(position);
                               notifyItemRangeChanged(position,getItemCount());
                               Toast.makeText(context, "Favourite successfully deleted", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                           }
                       });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                   }
               });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class favViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView mTextView;
        ImageButton mImageButton;
        public favViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.word2);
            mImageButton=itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



        }
    }
}
