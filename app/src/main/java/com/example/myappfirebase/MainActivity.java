package com.example.myappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<Note>arrayList;
    RecyclerView recyclerView;
    EditText mTitle,mMessage;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle=findViewById(R.id.title_id);
        mMessage=findViewById(R.id.message_id);
        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.rv_id);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notes");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Read From DataBase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    String title=ds.child("title").getValue().toString();
                    String id=ds.child("id").getValue().toString();
                    String message=ds.child("message").getValue().toString();
                    arrayList.add(new Note(id,title,message));
                }
                NoteAdabter adabter=new NoteAdabter(arrayList,MainActivity.this);
                RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplication());
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adabter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void btn_save(View view) {
        String id=myRef.push().getKey();
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("title",mTitle.getText().toString());
        map.put("message",mMessage.getText().toString());
        myRef.child(id).setValue(map);
        mTitle.setText("");
        mMessage.setText("");
    }
}