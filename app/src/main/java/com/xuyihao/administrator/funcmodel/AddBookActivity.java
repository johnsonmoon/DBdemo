package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.xuyihao.administrator.dbdemo.*;

import com.xuyihao.administrator.dbdemo.R;

public class AddBookActivity extends Activity{

    private Button btnOK;
    private Button btnCancel;
    private EditText EBno;
    private EditText EBname;
    private EditText Eauthor;
    private EditText Epress;
    private EditText Enumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        this.EBno = (EditText)findViewById(R.id.editAddBookBno);
        this.EBname = (EditText)findViewById(R.id.editAddBookBname);
        this.Eauthor = (EditText)findViewById(R.id.editAddBookBauthor);
        this.Epress = (EditText)findViewById(R.id.editAddBookBpress);
        this.Enumber = (EditText)findViewById(R.id.editAddBookBnumber);
        this.btnCancel = (Button)findViewById(R.id.btnAddBookCancel);
        this.btnOK = (Button)findViewById(R.id.btnAddBookConfirm);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookActivity.this.finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bno = AddBookActivity.this.EBno.getText().toString().trim();
                String Bname = AddBookActivity.this.EBname.getText().toString().trim();
                String Bauthor = AddBookActivity.this.Eauthor.getText().toString().trim();
                String Bpress = AddBookActivity.this.Epress.getText().toString().trim();
                String Bnumber = AddBookActivity.this.Enumber.getText().toString().trim();

                if(Bno.equals("") || Bname.equals("") || Bauthor.equals("") || Bpress.equals("") || Bnumber.equals("")){
                    new dialog(AddBookActivity.this, "Please input something and do not leave any EditBox empty!");
                }
                else{
                    DBoperater op = new DBoperater(AddBookActivity.this);
                    SQLiteDatabase db = op.getWritableDatabase();
                    db.execSQL("insert into Book values(?, ?, ?, ?, ?)", new Object[]{Bno, Bname, Bauthor, Bpress, Integer.parseInt(Bnumber)});
                    new dialog(AddBookActivity.this, "Insert reader information succeeded!");
                    AddBookActivity.this.EBno.setText("");
                    AddBookActivity.this.EBname.setText("");
                    AddBookActivity.this.Eauthor.setText("");
                    AddBookActivity.this.Epress.setText("");
                    AddBookActivity.this.Enumber.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
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
