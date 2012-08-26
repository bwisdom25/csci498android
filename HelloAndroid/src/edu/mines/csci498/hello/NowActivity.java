package edu.mines.csci498.hello;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NowActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_now, menu);
        return true;
    }
}
