package com.example.q.project2_master.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.q.project2_master.Activities.MainActivity;
import com.example.q.project2_master.Adapter.RecyclerPicAdapter;
import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.Models.ContactsModel;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class GalleryFragment extends Fragment{
    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> thumbsDadaList;
    private ArrayList<String> thumbsIDList;


    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        thumbsIDList = new ArrayList<String>();
        thumbsDadaList = new ArrayList<String>();

        getThumbInfo(thumbsIDList, thumbsDadaList);

        layoutManager = new GridLayoutManager(getContext(), 3);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerPicAdapter adapter = new RecyclerPicAdapter(thumbsDadaList, getContext());

        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        return v;


    }
    private void getThumbInfo(ArrayList<String> thumbsIDs, ArrayList<String> thumbsDatas) {
        String[] proj ={MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};

        Cursor imageCursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        if (imageCursor != null && imageCursor.moveToFirst()){
            String thumbsID;
            String thumbsImagesID;
            String thumbsData;

            int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int thumbsImagesIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int num =0;
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImagesID = imageCursor.getString(thumbsImagesIDCol);
                num++;
                if (thumbsImagesID != null) {
                    thumbsIDs.add(thumbsID);
                    thumbsDatas.add(thumbsData);
                }
            }while (imageCursor.moveToNext());
        }
        imageCursor.close();
        return;
    }

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
                        String targetName = editText.getText().toString();
                        String jsonString = JsonUtils.toJSonDownload(targetName);
                        String urlTail = "/download_images";
                        DownloadImgsServerSS diSS = new DownloadImgsServerSS(urlTail, jsonString, getContext(), ServerSS.METHOD_POST, targetName);
                        diSS.execute(getContext().getString(R.string.SERVER_URL) + urlTail);
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

    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DownloadImgsServerSS extends ServerSS {
        String targetName;
        public DownloadImgsServerSS(String urlTail, String stringData, Context context, int method, String targetName) {
            super(urlTail, stringData, context, method);
            this.targetName = targetName;
        }

        @Override
        protected void onPostExecute(String result) {
            String toastText;
            if (result == null) {
                toastText = "network error!";
            } else {
                toastText= "Sorry, json error";
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("server_success")) {
                        toastText = "Sorry, database error.";
                    } else if (!jsonObject.getBoolean("images_exist")) {
                        toastText = "No images found";
                    } else {
                        toastText = "images downloaded!";
                        try {
                            //contacts updated here
                            int len = result.length();
                            String[] encodedImages = new String[len];
                            JSONArray jsonArray = jsonObject.getJSONArray("images");
                            for (int i=0; i<len; i++) {
                                String encodedImg = jsonArray.getJSONObject(i).getString("encoded_img");
                                encodedImages[i] = encodedImg;
                                Log.d("image", encodedImg.substring(10));
                            }
                            for (int i = 0; i < encodedImages.length; i++) {
                                byte[] decodedString = Base64.decode(encodedImages[i], Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                saveImage(decodedByte,  targetName + Integer.toString(i));
                            }
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
