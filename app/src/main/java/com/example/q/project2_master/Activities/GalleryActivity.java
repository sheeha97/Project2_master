package com.example.q.project2_master.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.project2_master.PhotoView.PhotoView;
import com.example.q.project2_master.R;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity{

    private ArrayList<String> paths;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Bundle bundle = getIntent().getExtras();
        int p = bundle.getInt("image_id");
        paths = bundle.getStringArrayList("image_path");
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter(paths));
        viewPager.setCurrentItem(p);
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
