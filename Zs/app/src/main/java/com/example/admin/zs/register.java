package com.example.admin.zs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class register extends Activity {
    DatabaseHelper myDb;
    Button register;
    EditText name;
    EditText phone;
    EditText email;
    EditText dob;
    EditText password;
    EditText repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register=(Button) findViewById(R.id.register);
        name=(EditText) findViewById(R.id.rname);
        phone=(EditText) findViewById(R.id.rphone);
        email=(EditText) findViewById(R.id.remail);
        dob=(EditText) findViewById(R.id.rdob);
        password=(EditText) findViewById(R.id.rpassword);
        repassword=(EditText) findViewById(R.id.rrepassword);
        myDb = new DatabaseHelper(this);
        int ik=myDb.samplecontact();
        if(ik==2)
        {
            Toast.makeText(register.this, "sample contact added idiot", Toast.LENGTH_SHORT).show();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString().equals(repassword.getText().toString()))
                {

                    boolean isInserted = myDb.mydetailsinsertData(name.getText().toString(),phone.getText().toString(),email.getText().toString(),dob.getText().toString(),0,password.getText().toString());
                    if(isInserted == true) {
                        Toast.makeText(register.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(register.this, login.class);
                        startActivity(i);

                    }
                    else
                        Toast.makeText(register.this,"Phone number already present",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast toast= Toast.makeText(register.this,"Both password Not Match",Toast.LENGTH_SHORT);toast.show();
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
