package com.example.myappfirebase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UbdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UbdateFragment extends AppCompatDialogFragment {
  View view; EditText mTitle,mDescription; Button mUpdate,mDelete;
  String mNoteKey;
    FirebaseDatabase database;
    DatabaseReference myRef;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UbdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UbdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UbdateFragment newInstance(String param1, String param2) {
        UbdateFragment fragment = new UbdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_ubdate, container, false);
        initView();
        return view;
    }

    private void initView() {
        mTitle=view.findViewById(R.id.title_edittext_id);
        mDescription=view.findViewById(R.id.note_edittext_id);
        mTitle.setText(getArguments().getString("title"));
        mDescription.setText(getArguments().getString("message"));
        mNoteKey=getArguments().getString("key");
        mUpdate=view.findViewById(R.id.btn_ubdate_note);
        mDelete=view.findViewById(R.id.btn_delete_note);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notes");
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map=new HashMap<>();
                map.put("id",mNoteKey);
                map.put("title",mTitle.getText().toString());
                map.put("message",mDescription.getText().toString());
                myRef.child(mNoteKey).setValue(map);
             dismiss();
            }
        });
        //////////
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(mNoteKey).removeValue();
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,(int) getResources().getDimension(R.dimen.dialog_high));
    }
}