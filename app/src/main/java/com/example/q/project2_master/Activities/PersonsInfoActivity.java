package com.example.q.project2_master.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.q.project2_master.Adapter.ViewPagerAdapter;
import com.example.q.project2_master.Fragments.PersonsInfoFragment;
import com.example.q.project2_master.R;

public class PersonsInfoActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView contact_call_name;
    private TextView contact_call_number;
    private TextView contact_call_number2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_persons_info);
        PersonsInfoFragment fragmentContactsCall =new PersonsInfoFragment();
        viewPager = findViewById(R.id.rv_contacts_call);
        Bundle bundle = getIntent().getExtras();
        contact_call_name = findViewById(R.id.contact_call_name);
        contact_call_number = findViewById(R.id.contact_call_number);
        contact_call_number2 = findViewById(R.id.contact_call_number2);
        contact_call_name.setText(bundle.getString("name"));
        contact_call_number.setText(bundle.getString("number"));
        contact_call_number2.setText(bundle.getString("number2"));

        Bundle bundle1 = new Bundle();
        bundle1.putString("number", bundle.getString("number"));
        bundle1.putString("number2", bundle.getString("number2"));
        fragmentContactsCall.setArguments(bundle1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentContactsCall, "Contacts");
        viewPager.setAdapter(adapter);
    }

    public void backto(View view) {
        finish();
    }

}
