package com.gratanet.mailanovadilbek.razybol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.gratanet.mailanovadilbek.razybol.fragments.LoginFragment;
import com.gratanet.mailanovadilbek.razybol.fragments.RatingFragment;
import com.gratanet.mailanovadilbek.razybol.helper.HttpRequest;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 546;
    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginFragment loginFragment = new LoginFragment(this);
    private RatingFragment ratingFragment = new RatingFragment();
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception ignored) {
        }
        toolbar.setVisibility(View.GONE);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (isLoggedIn()) {
            activateToolbar();
            transaction.replace(R.id.frame, ratingFragment);
        } else {
            diactivateToolbar();
            transaction.replace(R.id.frame, loginFragment);
        }
        transaction.commit();
    }

    private boolean isLoggedIn() {
        return account != null;
    }

    public void login() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            activateToolbar();

            RatingFragment ratingFragment = new RatingFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, ratingFragment);
            transaction.commit();
        } catch (ApiException e) {
            Log.w("mylog", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_LONG).show();
        }
    }

    private void activateToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    private void diactivateToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rating_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                sync();
                return true;
            case R.id.menu_sign_out:
                logout();
                return true;
        }
        return false;
    }

    public void onClickRating(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rating_1:
                sendRating("Плохо");
                break;
            case R.id.rating_2:
                sendRating("Удовлетворительно");
                break;
            case R.id.rating_3:
                sendRating("Хорошо");
                break;
            case R.id.rating_4:
                sendRating("Отлично");
                break;
        }
    }

    private void logout() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        diactivateToolbar();
                        LoginFragment loginFragment = new LoginFragment(MainActivity.this);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frame, loginFragment);
                        transaction.commit();
                    }
                });
    }

    public String getCurrentTimeStamp() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }

    private void sendRating(final String rating) {
        final String url = "https://docs.google.com/forms/d/e/1FAIpQLScZg_9EPJWkIOJnpK0BOPseBn10c0tx9hxN9GTzZ3QSDe-Ssg/formResponse";
        final String date = getCurrentTimeStamp();
        final String email = account.getEmail();

        HttpRequest mReq = new HttpRequest(getApplicationContext());

        mReq.send(Request.Method.POST, url, new HashMap<String, String>() {
            {
                put("entry.978440703", date);
                put("entry.1485789755", email);
                put("entry.2004267105", rating);
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addToDatabase();
            }
        });
        showAlertDialog();
    }

    private void showAlertDialog() {

    }

    private void addToDatabase() {

    }

    private void sync() {

    }

}
