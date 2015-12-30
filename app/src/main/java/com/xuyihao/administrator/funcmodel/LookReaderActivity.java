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
import java.util.Objects;

public class LookReaderActivity extends Activity {

    private ListView readerView;
    private DBoperater op;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> list;
    private int selectedPosition;
    private HashMap<String, Object> selectedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_reader);
        readerView = (ListView)findViewById(R.id.listViewLookReader);
        InitMapList();
        adapter = new SimpleAdapter(this, list, R.layout.reader_infomation_item, new String[]{"Rno", "Rname"}, new int[]{R.id.txtLookReaderRno, R.id.txtLookReaderRname});
        readerView.setAdapter(adapter);

        readerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LookReaderActivity.this.selectedPosition = position;
                LookReaderActivity.this.selectedMap = (HashMap<String, Object>)LookReaderActivity.this.adapter.getItem(position);
                String userNo = LookReaderActivity.this.selectedMap.get("Rno").toString().trim();
                Intent intent2 = new Intent(LookReaderActivity.this, ReaderDetailInfomationActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("userNo", userNo);
                intent2.putExtras(bundle2);
                startActivity(intent2);
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
        getMenuInflater().inflate(R.menu.menu_look_reader, menu);
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
