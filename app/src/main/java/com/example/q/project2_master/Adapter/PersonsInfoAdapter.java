package com.example.q.project2_master.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.project2_master.Models.PersonsInfoModel;
import com.example.q.project2_master.R;

import java.util.List;

public class PersonsInfoAdapter extends RecyclerView.Adapter<PersonsInfoAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private Context mContext;

    private List<PersonsInfoModel> mlistContactsCall;

    public PersonsInfoAdapter(Context context, List<PersonsInfoModel> listContactsCall) {
        mContext = context;
        mlistContactsCall = listContactsCall;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.items_person_info, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView duration, date;

        duration = holder.duration;
        date = holder.date;

        duration.setText(mlistContactsCall.get(position).getDuration());
        date.setText(mlistContactsCall.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlistContactsCall.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView duration, date;
        public ViewHolder(View itemView) {
            super(itemView);

            duration = itemView.findViewById(R.id.duration);
            date = itemView.findViewById(R.id.date);
        }

    }
}
