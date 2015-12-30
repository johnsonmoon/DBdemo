package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xuyihao.administrator.dbdemo.*;
import com.xuyihao.administrator.dbdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookBookActivity extends Activity {

    private int userType;
    private ListView bookView;
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
        setContentView(R.layout.activity_look_book);
        //get the user type for different usage of listview click
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.userType = bundle.getInt("userType");

        bookView = (ListView)findViewById(R.id.listViewLookBook);
        InitMapList();
        adapter = new SimpleAdapter(this, list, R.layout.book_infomation_item, new String[]{"Bno", "Bname", "Bauthor", "Bpress", "Bnumber"}, new int[]{R.id.txtBookItemBno, R.id.txtBookItemBname, R.id.txtBookItemBauthor, R.id.txtBookItemBpress, R.id.txtBookItemBnumber});
        bookView.setAdapter(adapter);

        bookView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(LookBookActivity.this.userType == 1){
                    LookBookActivity.this.selectedPosition = position;
                    LookBookActivity.this.selectedMap = (HashMap<String, Object>)LookBookActivity.this.adapter.getItem(position);

                    new AlertDialog.Builder(LookBookActivity.this).setMessage("Are you sure to delete it?").setTitle("Warning").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String deleteBno = LookBookActivity.this.selectedMap.get("Bno").toString().trim();
                            DBoperater oop = new DBoperater(LookBookActivity.this);
                            SQLiteDatabase dbb = oop.getWritableDatabase();
                            dbb.execSQL("delete from Book where Bno = ?", new Object[]{deleteBno});
                            new dialog(LookBookActivity.this, "Delete succeeded!");
                            //LookBookActivity.this.InitMapList();
                            //LookBookActivity.this.adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                else{
                    new dialog(LookBookActivity.this, "Delete permission refused!");
                }

                return true;

            }
        });
    }

    public void InitMapList(){
        op = new DBoperater(LookBookActivity.this);
        db = op.getWritableDatabase();
        cursor = db.rawQuery("select Bno, Bname, Bauthor, Bpress, Bnumber from Book", null);
        this.list = new ArrayList<HashMap<String, Object>>();
        while(cursor.moveToNext()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Bno", cursor.getString(cursor.getColumnIndex("Bno")).trim());
            map.put("Bname", cursor.getString(cursor.getColumnIndex("Bname")).trim());
            map.put("Bauthor", cursor.getString(cursor.getColumnIndex("Bauthor")).trim());
            map.put("Bpress", cursor.getString(cursor.getColumnIndex("Bpress")).trim());
            map.put("Bnumber", String.valueOf(cursor.getInt(cursor.getColumnIndex("Bnumber"))).trim());
            list.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_look_book, menu);
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
