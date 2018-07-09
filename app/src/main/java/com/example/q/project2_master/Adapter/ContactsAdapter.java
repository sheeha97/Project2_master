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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.project2_master.Activities.AppStartActivity;
import com.example.q.project2_master.Activities.MainActivity;
import com.example.q.project2_master.Activities.PersonsInfoActivity;
import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import java.util.List;

import static com.example.q.project2_master.Utils.JsonUtils.toJSon;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private String userName;
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
                /*Intent intent = new Intent(view.getContext(), PersonsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", contact_name.getText().toString());
                bundle.putString("number", contact_number.getText().toString());
                bundle.putString("number2", contact_number2.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);*/


                /*SharedPreferences sf = mContext.getSharedPreferences("user_name_saver", 0);
                userName = sf.getString("user_name_preference", "");*/  //TODO: maybe MainActivity.userName can be replaced by this??

                //Open dialog
                openDialog();

                //Create jsonString
                ContactsModel person = new ContactsModel(MainActivity.userName, contact_name.getText().toString(), contact_number.getText().toString());
                String jsonString = JsonUtils.toJSon(person);



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
        public ShareContactServerSS(String urlTail, String stringData, Activity context, int method) {
            super(urlTail, stringData, context, method);
        }
    }

    //will see if i wanna create a new class for this
    public void openDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

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
                // Gotta do something fancy here @.@


                //fill out the blank



            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
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
