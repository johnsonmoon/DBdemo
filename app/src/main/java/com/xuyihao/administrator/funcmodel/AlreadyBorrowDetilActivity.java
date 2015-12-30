package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xuyihao.administrator.dbdemo.AdActivity;
import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlreadyBorrowDetilActivity extends Activity {

    private String readerNo;
    private ListView borrowView;
    private DBoperater op;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_borrow_detil);
        //get the user number for look up in the database
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        readerNo = bundle.getString("userNo");

        borrowView = (ListView)findViewById(R.id.listViewAlreadyBorrowList);
        InitMapList();
        adapter = new SimpleAdapter(this, list, R.layout.borrow_situation_item, new String[]{"Rname", "Bno", "Bname", "Btime"}, new int[]{R.id.txtBorrowsituationRname, R.id.txtBorrowsituationBno, R.id.txtBorrowsituationBname, R.id.txtBorrowsituationBtime});
        borrowView.setAdapter(adapter);


    }

    public void InitMapList(){
        op = new DBoperater(this);
        db = op.getReadableDatabase();
        cursor = db.rawQuery("select Rname, Bno, Bname, Btime from borrowsituation where Rno = ?", new String[]{this.readerNo});
        this.list = new ArrayList<HashMap<String, Object>>();
        while(cursor.moveToNext()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Rname", cursor.getString(cursor.getColumnIndex("Rname")).trim());
            map.put("Bno", cursor.getString(cursor.getColumnIndex("Bno")).trim());
            map.put("Bname", cursor.getString(cursor.getColumnIndex("Bname")).trim());
            map.put("Btime", cursor.getString(cursor.getColumnIndex("Btime")).trim());
            list.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_already_borrow_detil, menu);
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
