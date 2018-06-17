package com.example.yuliia.diploma.views.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yuliia.diploma.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.lucasurbas.listitemview.ListItemView;

import java.io.InputStream;
import java.net.URL;

public class user_profile extends AppCompatActivity {

    ListView list_options;
    String options[] = {"acc settings", "friends", "lists", "likes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        list_options = (ListView) findViewById(R.id.aup_options);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.lists_layout, options);
        list_options.setAdapter(arrayAdapter);

        list_options.setClickable(true);
        list_options.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Object o = list_options.getItemAtPosition(position);
                if(position == 0){
                    Toast.makeText(getApplicationContext(),"1 cl",Toast.LENGTH_SHORT).show();
                }
                else if(position == 1){
                    Toast.makeText(getApplicationContext(),"2 cl",Toast.LENGTH_SHORT).show();
                }
                else if(position == 2){
                    Toast.makeText(getApplicationContext(),"3 cl",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3){
                    Toast.makeText(getApplicationContext(),"4 cl",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView iv = (ImageView) findViewById(R.id.aup_userimg);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String uri = acct.getPhotoUrl().toString();
        new DownLoadImageTask(iv).execute(uri);
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
