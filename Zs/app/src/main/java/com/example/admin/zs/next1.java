package com.example.admin.zs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class next1 extends Activity {

    Button call,view1,qrcode,delete;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next1);
        myDb=new DatabaseHelper(this);
        Intent i=getIntent();
        final String phone=i.getStringExtra("phone");
        call=(Button) findViewById(R.id.call);
        view1=(Button) findViewById(R.id.view);
        qrcode=(Button) findViewById(R.id.qrcode);
        delete=(Button) findViewById(R.id.delete);
        final Cursor res=myDb.mycontactgetAllDatabyphone(phone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent= new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:"+phone));
                startActivity(callintent);
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewintent=new Intent(next1.this,displaydetails.class);

                res.moveToFirst();
                viewintent.putExtra("name", res.getString(0));
                viewintent.putExtra("phone", res.getString(1));
                viewintent.putExtra("email", res.getString(2));
                viewintent.putExtra("dob", res.getString(3));
               startActivity(viewintent);
            }
        });
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qrcodeintent=new Intent(next1.this,qrcodegenerator.class);
                res.moveToFirst();
                String name="INSERT INTO MYCONTACTS VALUES('"+res.getString(0)+"',"+res.getString(1)+",'"+res.getString(2)+"','"+res.getString(3)+"')";
                qrcodeintent.putExtra("name", name);
                startActivity(qrcodeintent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=myDb.mycontactsdeleteData(phone);
                if(i!=-1)
                {
                    Toast.makeText(next1.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                }
                Intent di=new Intent(next1.this,contact.class);
                startActivity(di);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next1, menu);
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
