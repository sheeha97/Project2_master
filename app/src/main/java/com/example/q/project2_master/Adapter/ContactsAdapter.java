package com.example.q.project2_master.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.project2_master.Activities.AppStartActivity;
import com.example.q.project2_master.Activities.MainActivity;
import com.example.q.project2_master.Activities.PersonsInfoActivity;
import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.q.project2_master.Utils.JsonUtils.toJSon;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    //private String userName;
    private Context mContext;
    private LayoutInflater inflater;
    private List<ContactsModel> mListContacts;
    public ContactsAdapter (Context context, List<ContactsModel> listContacts) {
        mContext = context;
        mListContacts = listContacts;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.fragment_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TextView contact_name, contact_number;
        contact_name = holder.contact_name;
        contact_number = holder.contact_number;

        contact_name.setText(mListContacts.get(position).getContactName());
        contact_number.setText(mListContacts.get(position).getNumber());


        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                ContactsModel person = new ContactsModel(MainActivity.userName, contact_name.getText().toString(), contact_number.getText().toString());
                String jsonString = JsonUtils.toJSon(person);

                openDialog(jsonString);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contact_name, contact_number, contact_number2;
        public ViewHolder(View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_number2 = itemView.findViewById(R.id.contact_number2);
        }

    }

    class ShareContactServerSS extends ServerSS {
        //String userName;
        AlertDialog dialog;
        public ShareContactServerSS(String urlTail, String stringData, Context context, int method, AlertDialog dialog) {
            super(urlTail, stringData, context, method);
            this.dialog = dialog;

        }
        @Override
        protected void onPostExecute(String result) {
            String toastText;
            if (result == null) {
                toastText = "network error!";
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("server_success")) {
                        toastText = "Sorry, database error.";
                    } else if (!jsonObject.getBoolean("upload_success")) {
                        toastText = "You are an undefined user!";
                    } else {
                        toastText = "contact uploaded to server!";
                    }
                } catch (JSONException e) {
                    Log.d("tink-exception", "json exception");
                    toastText = "sorry, json error.";
                    e.printStackTrace();
                }
            }
            Toast.makeText(this.context, toastText, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    //will see if i wanna create a new class for this
    public void openDialog(final String jsonString) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

        // Set Custom Title
        TextView title = new TextView(mContext);
        // Title Properties
        title.setText("Custom Dialog Box");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(mContext);
        // Message Properties
        msg.setText("I am a Custom Dialog Box. \n Please Customize me.");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String urlTail = "/share_contact";
                ShareContactServerSS scSS = new ShareContactServerSS(urlTail, jsonString, mContext, ServerSS.METHOD_POST, alertDialog);
                scSS.execute(mContext.getString(R.string.SERVER_URL) + urlTail);
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        new Dialog(mContext);
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }

}
