package com.example.q.project2_master.Utils;

import android.app.Person;

import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.Models.PersonsInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {
    public static String toJSon( ContactsModel person) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();


            jsonObj.put("name", person.getUserName());
            jsonObj.put("contact_name", person.getContactName()); // Set the first name/pair
            jsonObj.put("contact_number", person.getNumber());


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
