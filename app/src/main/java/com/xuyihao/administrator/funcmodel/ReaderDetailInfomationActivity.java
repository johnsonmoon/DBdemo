package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;

import org.w3c.dom.Text;

public class ReaderDetailInfomationActivity extends Activity {

    private String readerNo;
    private TextView txtRno;
    private TextView txtRname;
    private TextView txtRsex;
    private TextView txtRphone;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_detail_infomation);

        txtRno = (TextView)findViewById(R.id.txtReaderDetailRno);
        txtRname = (TextView)findViewById(R.id.txtReaderDetailRname);
        txtRsex = (TextView)findViewById(R.id.txtReaderDetailRsex);
        txtRphone = (TextView)findViewById(R.id.txtReaderDetailRphone);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        readerNo = bundle.getString("userNo");

        DBoperater op = new DBoperater(this);
        SQLiteDatabase db = op.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Rno, Rname, Rsex, Rphone from Reader where Rno = ?", new String[]{this.readerNo});

        if(cursor.moveToNext()){
            txtRno.setText(cursor.getString(cursor.getColumnIndex("Rno")).trim());
            txtRname.setText(cursor.getString(cursor.getColumnIndex("Rname")).trim());
            if(cursor.getString(cursor.getColumnIndex("Rsex")).trim().equals("M")){
                txtRsex.setText("Male");
            }
            else if(cursor.getString(cursor.getColumnIndex("Rsex")).trim().equals("F")){
                txtRsex.setText("Female");
            }
            else{
                txtRsex.setText("Unknown");
            }
            txtRphone.setText(cursor.getString(cursor.getColumnIndex("Rphone")).trim());
        }

        btnOk = (Button)findViewById(R.id.btnReaderDetailConfirm);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReaderDetailInfomationActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reader_detail_infomation, menu);
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
