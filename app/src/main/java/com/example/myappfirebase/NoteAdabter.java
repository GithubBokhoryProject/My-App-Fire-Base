package com.example.myappfirebase;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteAdabter extends RecyclerView.Adapter<NoteAdabter.NoteViewHolder> {
ArrayList<Note>arrayList;
    Context context;

    public NoteAdabter(ArrayList<Note> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,null,false);
        NoteViewHolder noteViewHolder=new NoteViewHolder(view);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdabter.NoteViewHolder holder, int position) {
        Note note=arrayList.get(position);
        holder.title.setText(note.getTitle());
        holder.message.setText(note.getMessage());
        ////////////////On Click item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UbdateFragment ubdateFragment=new UbdateFragment();
                Bundle bundle=new Bundle();
                bundle.putString("key",arrayList.get(position).getId());
                bundle.putString("title",arrayList.get(position).getTitle());
                bundle.putString("message",arrayList.get(position).getMessage());
                ubdateFragment.setArguments(bundle);
               ubdateFragment.show(((MainActivity)context).getSupportFragmentManager(),"");
            }
        });
//////////////////////////////////////end on click
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
      TextView title,message;
        public NoteViewHolder(@NonNull  View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_item_id);
            message=itemView.findViewById(R.id.mesage_item_id);
        }
    }
}
