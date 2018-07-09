package com.example.q.project2_master.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.PhotoView.PhotoView;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity{

    private ArrayList<String> paths;
    Context mContext = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Bundle bundle = getIntent().getExtras();
        final int p = bundle.getInt("image_id");
        paths = bundle.getStringArrayList("image_path");
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter(paths));
        viewPager.setCurrentItem(p);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //now gotta do something about json file.....shi...
                File imgFile = new File(paths.get(p));
                Bitmap bitmap;
                if (imgFile.exists()) {
                    bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    String jsonString = JsonUtils.toJSon(MainActivity.userName, encodedImage);
                    //do sharing
                    String urlTail = "/upload_img";
                    ImgUploadServerSS iuSS  = new ImgUploadServerSS(urlTail, jsonString, mContext, ServerSS.METHOD_POST);
                    iuSS.execute(getString(R.string.SERVER_URL) + urlTail);
                }
            }
        });

    }

    class ImgUploadServerSS extends ServerSS {
        public ImgUploadServerSS(String urlTail, String stringData, Context context, int method) {
            super(urlTail, stringData, context, method);
        }

        @Override
        protected void onPostExecute(String result) {
            this.handleUploadResponse(result);
        }
    }


    static class SamplePagerAdapter extends PagerAdapter {

        private ArrayList<String> mpaths;
        public SamplePagerAdapter(ArrayList<String> paths) {
            mpaths = paths;
        }

        @Override
        public int getCount() { return mpaths.size();}

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            File imgFile = new File(mpaths.get(position));
            PhotoView photoView = new PhotoView(container.getContext());

            if (imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                photoView.setImageBitmap(bitmap);
            }
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {return view == object; }
    }

    public void backto2(View view) {
        finish();
    }
}
