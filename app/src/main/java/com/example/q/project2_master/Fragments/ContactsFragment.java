package com.example.q.project2_master.Fragments;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.project2_master.Activities.MainActivity;
import com.example.q.project2_master.Activities.Tab3Activity;
import com.example.q.project2_master.Adapter.ContactsAdapter;
import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private TextView resultText;

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

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


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

            list.add(new ContactsModel(MainActivity.userName,cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    mobile));


        }

        return list;
    }

    //will see if i wanna create a new class for this
    public void openDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //fill out if user did type out a name :D
                        String targetName = editText.getText().toString();
                        String urlTail= "/download_contacts";
                        DownloadContactServerSS dcSS = new DownloadContactServerSS(
                                urlTail, JsonUtils.toJSonDownload(targetName), getContext(), ServerSS.METHOD_POST, targetName);
                        dcSS.execute(getString(R.string.SERVER_URL) + urlTail);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                        //when user declined your request :(
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    class DownloadContactServerSS extends ServerSS {
        String targetName;
        public DownloadContactServerSS(String urlTail, String stringData, Context context, int method, String targetName) {
            super(urlTail, stringData, context, method);
            this.targetName = targetName;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String toastText;
            if (result == null) {
                toastText = "network error!";
            } else {
                toastText= "Sorry, json error";
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("server_success")) {
                        toastText = "Sorry, database error.";
                    } else if (!jsonObject.getBoolean("contacts_exist")) {
                        toastText = "No contacts found";
                    } else {
                        toastText = "contacts downloaded!";
                        try {
                            //contacts updated here
                            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                            int len = result.length();
                            ContactsModel[] contactsModels = new ContactsModel[len];
                            for (int i=0; i<result.length(); i++) {
                                String contactNumber = jsonArray.getJSONObject(i).getString("contact_number");
                                ContactsModel contactsModel = new ContactsModel(MainActivity.userName, targetName, contactNumber);
                                contactsModels[i] = contactsModel;
                                //getContacts().add(contactsModel);
                                Log.d("contact_number", contactNumber);
                                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber)
                                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                                        .putExtra(ContactsContract.Intents.Insert.NAME, targetName);


                            }
                            //TODO: contacts in device needs to be updated: by adding downloaded contacts from server.
                            // now contactsModels(Array) has all contacts from server.
                            // before adding it to the device contacts, check if there exists contact with duplicate contact_name && contact_number
                            // and if such contact already exist(satisfying both duplicate condition), don't add it to device.


                        }
                        catch (JSONException e) {
                            Log.d("tink-exception", "json array exception");
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    Log.d("tink-exception", "json object exception");
                    e.printStackTrace();
                }
            }
            Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT);
        }
    }

}
