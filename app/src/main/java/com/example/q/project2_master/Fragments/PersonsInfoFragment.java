package com.example.q.project2_master.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.project2_master.Adapter.PersonsInfoAdapter;
import com.example.q.project2_master.Models.PersonsInfoModel;
import com.example.q.project2_master.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonsInfoFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;

    public PersonsInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_persons_info, container, false);

        recyclerView = v.findViewById(R.id.rv_contacts);

        recyclerView.setBackgroundColor(Color.rgb(29, 29, 37));
        Bundle bundle=getArguments();
        String contact_call_number = bundle.getString("number");
        String contact_call_number2 = bundle.getString("number2");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        PersonsInfoAdapter adapter = new PersonsInfoAdapter(getContext(), getCallLogs(contact_call_number, contact_call_number2 ));

        recyclerView.setAdapter(adapter);
        return v;
    }

    private List<PersonsInfoModel> getCallLogs(String string1, String string2) {

        List<PersonsInfoModel> list = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CALL_LOG}, 1);
        }

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE);

        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);

        string1 = string1.replaceAll("-","");
        string1 = string1.replaceAll("\\(","");
        string1 = string1.replaceAll("\\)","");
        string1 = string1.replaceAll(" ","");
        string1 = string1.trim();
        string2 = string2.replaceAll("-","");
        string2 = string2.replaceAll("\\(","");
        string2 = string2.replaceAll("\\)","");
        string2 = string2.replaceAll(" ","");
        string2 = string2.trim();

        while (cursor.moveToNext()) {
            if (cursor.getString(number).equals(string1) || cursor.getString(number).equals(string2)) {
                Date datel = new Date(Long.valueOf(cursor.getString(date)));
                list.add(new PersonsInfoModel(cursor.getString(duration), datel.toString()));
            }
        }
        return list;
    }

}