package com.example.q.project2_master.Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.project2_master.Adapter.ContactsAdapter;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private View v2;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ContactsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_precontacts, container, false);

        recyclerView = v.findViewById(R.id.rv_contacts);
        recyclerView.setBackgroundColor(Color.rgb(29, 29, 37));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager layoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(layoutManager);
        ContactsAdapter adapter = new ContactsAdapter(getContext(), getContacts());

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),getResources().getColor(android.R.color.holo_red_dark)
                ,getResources().getColor(android.R.color.holo_blue_dark),getResources().getColor(android.R.color.holo_orange_dark) );

        return v;
    }
    @Override
    public void onRefresh() {
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        swipeRefreshLayout.setRefreshing(false);
    }

    private List<ContactsModel> getContacts() {

        List<ContactsModel> list = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE);

        int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        while (cursor.moveToNext()) {

            String id = cursor.getString(ididx);
            Cursor cursor2 = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

            int typeidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
            int numidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String mobile = null;
            String home = null;
            while(cursor2.moveToNext()) {
                String num = cursor2.getString(numidx);
                switch ( cursor2.getInt(typeidx)) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        mobile = num;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        home = num;
                        break;
                }
            }

            list.add(new ContactsModel(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    mobile, home));


        }

        return list;
    }
}
