package com.example.francisco.myapplication;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class DownloadFilesTask extends AsyncTask<File,Void,String> {
    protected String doInBackground(File... file) {
        try {
        HttpClient client = new DefaultHttpClient();
        String name = file[1].getName();

        if(name == "")
            name = "http://uploader-emcor.herokuapp.com/";
            else
        name = "http://" + name + "/";

        HttpPost post = new HttpPost(name);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fb = new FileBody(file[0]);
        builder.addPart("file", fb);

        final HttpEntity entity = builder.build();

        post.setEntity(entity);

        HttpResponse response = null;

        response = client.execute(post);
        HttpEntity httpEntity = response.getEntity();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Long result) {

    }
}