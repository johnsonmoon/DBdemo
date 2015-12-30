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
import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReturnBookDetailActivity extends Activity {

    private String readerNo;
    private ListView returnDetailView;
    private DBoperater op;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> list;
    private HashMap<String, Object> selectedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        readerNo = bundle.getString("userNo");

        returnDetailView = (ListView)findViewById(R.id.listViewReturnBookBorrowDetail);
        InitMapList();
        adapter = new SimpleAdapter(this, list, R.layout.borrow_situation_item, new String[]{"Rname", "Bno", "Bname", "Btime"}, new int[]{R.id.txtBorrowsituationRname, R.id.txtBorrowsituationBno, R.id.txtBorrowsituationBname, R.id.txtBorrowsituationBtime});
        returnDetailView.setAdapter(adapter);

        returnDetailView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReturnBookDetailActivity.this.selectedMap = (HashMap<String, Object>)ReturnBookDetailActivity.this.adapter.getItem(position);
                new AlertDialog.Builder(ReturnBookDetailActivity.this).setTitle("WARNING").setMessage("Are you sure to RETURN the book?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Rno, Bno;
                        Rno = ReturnBookDetailActivity.this.readerNo;
                        Bno = ReturnBookDetailActivity.this.selectedMap.get("Bno").toString().trim();
                        DBoperater opp = new DBoperater(ReturnBookDetailActivity.this);
                        SQLiteDatabase dbb = opp.getWritableDatabase();
                        dbb.execSQL("delete from Borrow where Rno = ? and Bno = ?", new Object[]{Rno, Bno});
                        dbb.execSQL("update Book set Bnumber = Bnumber+1 where Bno = ?", new Object[]{Bno});
                        new dialog(ReturnBookDetailActivity.this, "Return book succeeded!");
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_return_book_detail, menu);
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
