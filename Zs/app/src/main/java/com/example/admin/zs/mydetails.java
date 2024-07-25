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

public class mydetails extends Activity {
    DatabaseHelper myDb;
    EditText mname,mphone,memail,mdob,mpassword;
    Button mok,mupdate;

   String name,wallet,phone,email,dob,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydetails);
        Intent i=getIntent();
        myDb = new DatabaseHelper(this);
        name=i.getStringExtra("name");
        phone=i.getStringExtra("phone");
        email=i.getStringExtra("email");
        dob=i.getStringExtra("dob");
        wallet=i.getStringExtra("wallet");
        password=i.getStringExtra("password");

        Toast.makeText(mydetails.this,"ur wallet==="+wallet, Toast.LENGTH_SHORT).show();
        mname=(EditText) findViewById(R.id.mname);
        mphone=(EditText) findViewById(R.id.mphone);
        memail=(EditText) findViewById(R.id.memail);
        mdob=(EditText) findViewById(R.id.mdob);
        mpassword=(EditText) findViewById(R.id.mpassword);
        mok=(Button) findViewById(R.id.mok);
        mupdate=(Button) findViewById(R.id.mupdate);


        mname.setText(name);
        mphone.setText(phone);
        memail.setText(email);
        mdob.setText(dob);
        mpassword.setText(password);
        String value="10";
        myDb.update(value);
        mok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(mydetails.this, contact.class);
                startActivity(i1);
            }
        });
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(phone,
                        mname.getText().toString(),
                        memail.getText().toString(), mdob.getText().toString(), mpassword.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(mydetails.this,"Data Update",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(mydetails.this,"Data not Updated",Toast.LENGTH_LONG).show();
                Intent i1=new Intent(mydetails.this,contact.class);
                startActivity(i1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mydetails, menu);
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
