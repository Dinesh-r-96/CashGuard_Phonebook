package com.example.admin.zs;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class contact extends Activity {
    DatabaseHelper myDb;
    TextView cname;
    TextView cphone;
    TextView cwallet;
    Button cqrcode,cscan,csendmoney,ccontact;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        myDb = new DatabaseHelper(this);
        cname=(TextView) findViewById(R.id.cname);
        cphone=(TextView) findViewById(R.id.cphone);
        cwallet=(TextView) findViewById(R.id.cwallet);
        cqrcode=(Button) findViewById(R.id.cqr);
        cscan=(Button) findViewById(R.id.cscan);
        csendmoney=(Button) findViewById(R.id.csendmoney);
        ccontact=(Button) findViewById(R.id.ccontact);
        final Cursor res = myDb.mydetailsgetAllData();
        final Cursor res2=myDb.mycontactgetAllData();
        res.moveToFirst();
        cname.setText(res.getString(0));
        cphone.setText(res.getString(1));
        cwallet.setText(res.getString(4));
        final String name=res.getString(0);
        final String phone=res.getString(1);
        final String email=res.getString(2);
        final String dob=res.getString(3);
        ArrayList<HashMap<String, String>> userList = myDb.GetUsers();
        ListView lv = (ListView) findViewById(R.id.clistView);
        ListAdapter adapter = new SimpleAdapter(contact.this, userList, R.layout.list_row,new String[]{"name","phone","email"}, new int[]{R.id.lrname, R.id.lrphone, R.id.lremail});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                res2.moveToPosition(i);
                Intent i3= new Intent(contact.this,next1.class);
                i3.putExtra("phone",res2.getString(1));
                Toast.makeText(contact.this, "phone :" + res2.getString(1), Toast.LENGTH_SHORT).show();
                startActivity(i3);
            }
        });
        cqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String querye="INSERT INTO MYCONTACTS VALUES('"+name+"',"+phone+",'"+email+"','"+dob+"');";
                Intent intent = new Intent(contact.this, qrcodegenerator.class);
                intent.putExtra("name", querye);
                startActivity(intent);
            }
        });
        cscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            scanQR(view);
            }
        });
        csendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(contact.this,sendmoney.class);
                startActivity(i2);
            }
        });
        ccontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(contact.this, mydetails.class);
                res.moveToFirst();
                i2.putExtra("name", res.getString(0));
                i2.putExtra("phone", res.getString(1));
                i2.putExtra("email", res.getString(2));
                i2.putExtra("dob", res.getString(3));
                i2.putExtra("wallet", res.getString(4));
                i2.putExtra("password", res.getString(5));

                startActivity(i2);
            }
        });

    }
    //product barcode mode
//    public void scanBar(View v) {
//        try {
//start the scanning activity from the com.google.zxing.client.android.SCAN intent
//            Intent intent = new Intent(ACTION_SCAN);
//            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
//            startActivityForResult(intent, 0);
//        } catch (ActivityNotFoundException anfe) {
//on catch, show the download dialog
//            showDialog(contact.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
//        }}
    //product qr code mode
    public void scanQR(View v) {
        try {
//start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
//on catch, show the download dialog
            showDialog(contact.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }}
    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title,
                                          CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }}
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }
    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
//get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Intent ii=new Intent(contact.this,contact.class);
                boolean i=myDb.queries(contents);
                if(i) {
                    Toast toast = Toast.makeText(this, "Successfully DONE!!!!", Toast.LENGTH_LONG);
                    toast.show();
                    startActivity(ii);
                }

            }}}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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
