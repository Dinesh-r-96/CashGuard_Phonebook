package com.example.admin.zs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class displaydetails extends Activity {
TextView tname,tphone,temail,tdob;
    Button dok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydetails);
        tname=(TextView) findViewById(R.id.dname);
        tphone=(TextView) findViewById(R.id.dphone);
        temail=(TextView) findViewById(R.id.demail);
        tdob=(TextView) findViewById(R.id.ddob);
        dok=(Button) findViewById(R.id.ok);
        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String phone=i.getStringExtra("phone");
        String email=i.getStringExtra("email");
        String dob=i.getStringExtra("dob");
        tname.setText(name);
        tphone.setText(phone);
        temail.setText(email);
        tdob.setText(dob);
        dok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(displaydetails.this,contact.class);
                startActivity(i2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_displaydetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
