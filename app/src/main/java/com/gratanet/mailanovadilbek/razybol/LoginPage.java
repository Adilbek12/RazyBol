package com.gratanet.mailanovadilbek.razybol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class LoginPage extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);

//    String url = "https://docs.google.com/forms/d/e/1FAIpQLScZg_9EPJWkIOJnpK0BOPseBn10c0tx9hxN9GTzZ3QSDe-Ssg/formResponse";
//
//    final String date = "1123123";
//    String email = "fadsfasdf";
//    String rating = "Ploho";
//
//    HttpRequest mReq = new HttpRequest(getApplicationContext());
//
//    mReq.send(Request.Method.POST, url, new HashMap<String, String>() {
//      {
//        try {
//          put("entry.978440703", URLEncoder.encode(date, "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//        }
//        put("entry.1485789755", URLEncoder.encode(date));
//        put("entry.2004267105", URLEncoder.encode(date));
//      }
//    }, new Response.Listener<String>() {
//      @Override
//      public void onResponse(String response) {
//        //todo to response
//      }
//    }, new Response.ErrorListener() {
//      @Override
//      public void onErrorResponse(VolleyError error) {
//        //todo to sql
//      }
//    });
  }


  public void login(View view) {

  }

}
