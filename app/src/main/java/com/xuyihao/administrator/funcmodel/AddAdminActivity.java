package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;
import com.xuyihao.administrator.dbdemo.dialog;

public class AddAdminActivity extends Activity {

    Button btnOK;
    Button btnCancel;
    EditText editAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        btnCancel = (Button)findViewById(R.id.btnAddAdminCancel);
        btnOK = (Button)findViewById(R.id.btnAddAdminConfirm);
        editAno = (EditText)findViewById(R.id.editAddAdminAno);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAdminActivity.this.finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ano = AddAdminActivity.this.editAno.getText().toString().trim();
                if(Ano.equals("")){
                    new dialog(AddAdminActivity.this, "Please input something and do not leave any EditBox empty!");
                }
                else{
                    DBoperater op = new DBoperater(AddAdminActivity.this);
                    SQLiteDatabase db = op.getWritableDatabase();
                    db.execSQL("insert into Admin values (?, ?)", new Object[]{Ano, "123456"});
                    new dialog(AddAdminActivity.this, "Insert reader information succeeded!");
                    AddAdminActivity.this.editAno.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_admin, menu);
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
