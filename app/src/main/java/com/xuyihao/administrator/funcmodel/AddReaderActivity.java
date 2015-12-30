package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.xuyihao.administrator.dbdemo.*;
import com.xuyihao.administrator.dbdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AddReaderActivity extends Activity {

    private Button btnOK;
    private Button btnCancel;
    private EditText editRno;
    private EditText editRname;
    private EditText editRphone;
    private Spinner spiRsex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reader);
        btnCancel = (Button)findViewById(R.id.btnAddReaderCancel);
        btnOK = (Button)findViewById(R.id.btnAddReaderConfirm);
        editRno = (EditText)findViewById(R.id.editAddReaderRno);
        editRname = (EditText)findViewById(R.id.editAddReaderRname);
        editRphone = (EditText)findViewById(R.id.editAddReaderRphone);
        spiRsex = (Spinner)findViewById(R.id.spinnerAddReader);

        List<String> sexData = new ArrayList<String>();
        sexData.add("M");
        sexData.add("F");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiRsex.setAdapter(adapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReaderActivity.this.finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Rsex = AddReaderActivity.this.spiRsex.getSelectedItem().toString().trim();
                String Rno = AddReaderActivity.this.editRno.getText().toString().trim();
                String Rname = AddReaderActivity.this.editRname.getText().toString().trim();
                String Rphone = AddReaderActivity.this.editRphone.getText().toString().trim();

                if(Rno.equals("") || Rname.equals("") || Rphone.equals("")){
                    new dialog(AddReaderActivity.this, "Please input something and do not leave any EditBox empty!");
                }
                else{
                    DBoperater op = new DBoperater(AddReaderActivity.this);
                    SQLiteDatabase db = op.getWritableDatabase();
                    db.execSQL("insert into Reader values (?, ?, ?, ?, ?)", new Object[]{Rno, Rname, Rsex, Rphone, "123456"});
                    new dialog(AddReaderActivity.this, "Insert reader information succeeded!");
                    AddReaderActivity.this.editRno.setText("");
                    AddReaderActivity.this.editRname.setText("");
                    AddReaderActivity.this.editRphone.setText("");
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reader, menu);
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
