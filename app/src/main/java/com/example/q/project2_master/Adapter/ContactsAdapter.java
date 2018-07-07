package com.example.q.project2_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.project2_master.Activities.PersonsInfoActivity;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

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
        final TextView contact_name, contact_number, contact_number2;
        contact_name = holder.contact_name;
        contact_number = holder.contact_number;
        contact_number2 = holder.contact_number2;

        contact_name.setText(mListContacts.get(position).getName());
        contact_number.setText(mListContacts.get(position).getNumber());
        contact_number2.setText(mListContacts.get(position).getNumber2());

        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PersonsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", contact_name.getText().toString());
                bundle.putString("number", contact_number.getText().toString());
                bundle.putString("number2", contact_number2.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
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

}
