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


            jsonObj.put("user_name", person.getUserName());
            jsonObj.put("contact_name", person.getContactName()); // Set the first name/pair
            jsonObj.put("contact_number", person.getNumber());


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    public static String toJSon(String userName, String encodedImg) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();


            jsonObj.put("user_name", userName);
            jsonObj.put("encoded_img", encodedImg);


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }

    public static String toJSonDownload(String targetName) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();


            jsonObj.put("target_name", targetName);


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String toJSonRedName(String redName) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("red_name", redName);


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String toJsonMove(int color, int index) {
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("red_name", color);
            jsonObj.put("index", index);


            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
