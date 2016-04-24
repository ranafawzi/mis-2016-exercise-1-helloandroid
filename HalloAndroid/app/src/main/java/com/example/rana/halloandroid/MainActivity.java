package com.example.rana.halloandroid;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import java.net.HttpURLConnection;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button;
        button = (Button) findViewById(R.id.button);
        TextView textView1 = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlTxt = ((EditText) findViewById(R.id.editText)).getText().toString().toLowerCase();
                TextView textView1 = (TextView) findViewById(R.id.textView);
                textView1.setMovementMethod(new ScrollingMovementMethod());
                //calling the ReadData function to get the url from HTTP server
                String contents =  ReadData(urlTxt);

                // Displaying the URL as a Text in TextView
               // textView1.setText(contents); // Uncomment it to display it


                //Displaying the URL as HTML in WEB view
                WebView wbvw=(WebView) findViewById(R.id.webView);
               wbvw.loadDataWithBaseURL(null, contents, "text/html", "utf-8", null);
            }
        });
    }
   protected String ReadData(String urlTxt) {
        try {
              // checking if it is URL or not
            if (!urlTxt.contains("http://"))
                urlTxt = "http://" + urlTxt;

            URL url = new URL(urlTxt);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(65000 /* milliseconds */);
            InputStream in = conn.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String webPage = "",data="";
            while ((data = reader.readLine()) != null)
                webPage += data + "\n";
            return webPage;
        }
        catch (MalformedURLException excUrl){
            Toast.makeText(getApplicationContext(),"Error- Server is not found "+ excUrl.getMessage(),Toast.LENGTH_LONG).show();
             return "Error";
        }
        catch(IOException exIO) {
            Toast.makeText(getApplicationContext(),"Error-exIO: "+ exIO.getMessage(),Toast.LENGTH_LONG).show();
            return "Error"; }

        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
            return "Error";
        }

    }



}
