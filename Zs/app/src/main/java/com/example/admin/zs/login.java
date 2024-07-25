package com.example.admin.zs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class login extends Activity {
    DatabaseHelper myDb;
    EditText phone;
    EditText password;

    Button login;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        phone=(EditText) findViewById(R.id.lphone);
        password=(EditText) findViewById(R.id.lpassword);

        login=(Button) findViewById(R.id.llogin);
        reg=(Button) findViewById(R.id.lregister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.mydetailsgetAllData();
                int i=res.getCount();
                if(i>0) {
                    res.moveToFirst();
                    String passworddata = res.getString(5);
                    String phonedata = res.getString(1);

                    if (phonedata.equals(phone.getText().toString()) && passworddata.equals(password.getText().toString())) {
                        Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(login.this, contact.class);

                        startActivity(i1);
                    } else {
                        Toast.makeText(login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login.this, "First Register U Idiot fellow!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=myDb.mydetailsgetAllData();
                int i=res.getCount();
                if(i==0) {
                    Intent i2 = new Intent(login.this, register.class);
                    startActivity(i2);
                }
                else
                {
                    Toast.makeText(login.this, "U have already registered", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
