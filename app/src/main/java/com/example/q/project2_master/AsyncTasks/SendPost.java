package com.example.q.project2_master.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.q.project2_master.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SendPost extends AsyncTask<String, String, String> {
    String urlTail;
    String stringData;
    Activity context;
    //Method doOnPostExecute;

    public SendPost(String urlTail, String stringData, Activity context) {
        this.urlTail = urlTail;
        this.stringData = stringData;
        this.context= context;
        //this.doOnPostExecute = doOnPostExecute;
    }

    @Override
    public String doInBackground(String... urls) {
        try {
            //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_name_post", stringData); //TODO: [name:"user_name_post"] needs to be fixed to a variable

            HttpURLConnection con = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(context.getString(R.string.SERVER_URL) + urlTail);

                //연결을 함
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");//POST방식으로 보냄
                con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                con.connect();

                //서버로 보내기위해서 스트림 만듬
                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();//버퍼를 닫아줌

                //get data from server
                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                return buffer.toString();//test1.js// res.end("Server connected to Android!");

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();//버퍼를 닫아줌
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    //doInBackground메소드가 끝나면 호출됨
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
