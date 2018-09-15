package com.gratanet.mailanovadilbek.razybol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class RatingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rating);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.rating_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }


  public void onClickRating(View view) {
    int id = view.getId();

    switch (id) {
      case R.id.rating_1:

        break;
      case R.id.rating_2:

        break;
      case R.id.rating_3:

        break;
      case R.id.rating_4:

    }
  }
}
