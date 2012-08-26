package edu.mines.csci498.hello;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import java.util.Date;

public class NowActivity extends Activity implements View.OnClickListener {
  Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        btn=new Button(this);
        btn.setOnClickListener(this);
        updateTime();
        setContentView(btn);
    }
    
    public void onClick(View view){
    	updateTime();
    }
    
    public void updateTime(){
    	btn.setText(new Date().toString());
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_now, menu);
        return true;
    }
    */
}
