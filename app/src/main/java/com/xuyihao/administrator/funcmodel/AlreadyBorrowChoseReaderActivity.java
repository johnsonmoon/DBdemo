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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlreadyBorrowChoseReaderActivity extends Activity {

    private ListView readerListScan;
    private DBoperater op;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> list;
    private HashMap<String, Object> selectedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_borrow_chose_reader);

        readerListScan = (ListView)findViewById(R.id.listViewAlreadyBorrowReaderList);
        InitMapList();
        adapter = new SimpleAdapter(this, list, R.layout.reader_infomation_item, new String[]{"Rno", "Rname"}, new int[]{R.id.txtLookReaderRno, R.id.txtLookReaderRname});
        readerListScan.setAdapter(adapter);

        readerListScan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlreadyBorrowChoseReaderActivity.this.selectedMap = (HashMap<String, Object>)AlreadyBorrowChoseReaderActivity.this.adapter.getItem(position);
                String userNo = AlreadyBorrowChoseReaderActivity.this.selectedMap.get("Rno").toString().trim();
                Intent intent = new Intent(AlreadyBorrowChoseReaderActivity.this, AlreadyBorrowDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userNo", userNo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void InitMapList(){
        op = new DBoperater(this);
        db = op.getReadableDatabase();
        cursor = db.rawQuery("select Rno, Rname from Reader", null);
        this.list = new ArrayList<HashMap<String, Object>>();
        while(cursor.moveToNext()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Rno", cursor.getString(cursor.getColumnIndex("Rno")).trim());
            map.put("Rname", cursor.getString(cursor.getColumnIndex("Rname")).trim());
            list.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_already_borrow_chose_reader, menu);
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
