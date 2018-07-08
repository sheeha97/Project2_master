package com.example.q.project2_master.Utils;

import android.app.Person;

import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.Models.PersonsInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtils {
    public static String toJSon( List<ContactsModel> persons) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArr = new JSONArray();
            for (ContactsModel person:persons) {
                JSONObject jsonAdd = new JSONObject();
                jsonAdd.put("name", person.getName()); // Set the first name/pair
                jsonAdd.put("number", person.getNumber());
                jsonAdd.put("homeNumber", person.getNumber2());
                jsonArr.put(jsonAdd);
            }

            /*
            **For tab 3, comment it out.
            *
            jsonAdd.put("address", person.getAddress().getAddress());
            jsonAdd.put("city", person.getAddress().getCity());
            jsonAdd.put("state", person.getAddress().getState());
             We add the object to the main object
            jsonObj.put("address", jsonAdd);


            // and finally we add the phone number
            // In this case we need a json array to hold the java list
            JSONArray jsonArr = new JSONArray();

            for (PersonsInfoModel pn : person_info.getPhoneList()) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("num", pn.getNumber());
                pnObj.put("type", pn.getType());
                jsonArr.put(pnObj);
            }

            jsonObj.put("phoneNumber", jsonArr);

            */

            jsonObj.put("phoneNumber", jsonArr);
            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
