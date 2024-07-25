package com.example.admin.zs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sendmoney2 extends Activity {
EditText smamount,smpassword;
    Button pay;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmoney2);
        Intent i=getIntent();
        final String phone=i.getStringExtra("phone");
        smamount=(EditText) findViewById(R.id.smamount);
        smpassword=(EditText) findViewById(R.id.smpassword);
        pay=(Button) findViewById(R.id.pay);
        myDb= new DatabaseHelper(this);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=smamount.getText().toString();
                String password=smpassword.getText().toString();
                Cursor res=myDb.mydetailsgetAllData();
                res.moveToFirst();
                String wallet=res.getString(4);
                int w=Integer.parseInt(wallet);
                int a=Integer.parseInt(amount);
                String password2=res.getString(5);

                if(password.equals(password2) && w>a)
                {
                    myDb.detectmywallet(amount);
                    String querye="UPDATE  MYDETAILS SET WALLET = WALLET+"+amount+" WHERE PHONE="+phone;
                    Intent intent = new Intent(sendmoney2.this, qrcodegenerator.class);
                    intent.putExtra("name", querye);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sendmoney2, menu);
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
