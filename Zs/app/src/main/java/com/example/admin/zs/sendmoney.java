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

public class sendmoney extends Activity {
    DatabaseHelper myDb;
    TextView sname;
    TextView sphone;
    TextView swallet;
    Button sqrcode,sscan,ssendmoney,scontact;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmoney);
        myDb = new DatabaseHelper(this);
        sname=(TextView) findViewById(R.id.sname);
        sphone=(TextView) findViewById(R.id.sphone);
        swallet=(TextView) findViewById(R.id.swallet);
        sqrcode=(Button) findViewById(R.id.sqr);
        sscan=(Button) findViewById(R.id.sscan);
        ssendmoney=(Button) findViewById(R.id.ssendmoney);
        scontact=(Button) findViewById(R.id.scontact);
        final Cursor res = myDb.mydetailsgetAllData();
        final Cursor res2=myDb.mycontactgetAllData();
        res.moveToFirst();
        sname.setText(res.getString(0));
        sphone.setText(res.getString(1));
        swallet.setText(res.getString(4));
        final String name=res.getString(0);
        final String phone=res.getString(1);
        final String email=res.getString(2);
        final String dob=res.getString(3);
        ArrayList<HashMap<String, String>> userList = myDb.GetUsers();
        ListView lv = (ListView) findViewById(R.id.slistView);
        ListAdapter adapter = new SimpleAdapter(sendmoney.this, userList, R.layout.list_row,new String[]{"name","phone","email"}, new int[]{R.id.lrname, R.id.lrphone, R.id.lremail});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                res2.moveToPosition(i);
                Intent i3= new Intent(sendmoney.this,sendmoney2.class);
                i3.putExtra("phone", res2.getString(1));
                Toast.makeText(sendmoney.this,"phone :"+res2.getString(1), Toast.LENGTH_SHORT).show();
                startActivity(i3);
            }
        });
        sqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String querye="INSERT INTO MYCONTACTS VALUES('"+name+"',"+phone+",'"+email+"','"+dob+"');";
                Intent intent = new Intent(sendmoney.this, qrcodegenerator.class);
                intent.putExtra("name", querye);
                startActivity(intent);
            }
        });
        sscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR(view);
            }
        });
        ssendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(sendmoney.this,next2.class);
                startActivity(i2);
            }
        });
        scontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(sendmoney.this,contact.class);
                startActivity(i2);
            }
        });
    }
    public void scanQR(View v) {
        try {
//start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
//on catch, show the download dialog
            showDialog(sendmoney.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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
                boolean i=myDb.queries(contents);
                if(i) {
                    Toast toast = Toast.makeText(this, "Successfully DONE!!!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }}}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sendmoney, menu);
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
