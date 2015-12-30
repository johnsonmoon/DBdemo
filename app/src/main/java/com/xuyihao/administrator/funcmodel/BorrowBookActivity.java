package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.xuyihao.administrator.dbdemo.*;
import com.xuyihao.administrator.dbdemo.DBoperater;
import com.xuyihao.administrator.dbdemo.R;

public class BorrowBookActivity extends Activity {

    //********************* The operations between admin and reader is different
    private int userType;//for differ two different users(reader or admin) && 1 stands for admin 2 stands for reader
    private String userNo;//if the user is reader, this user number will be useful

    private Button btnOK;
    private Button btnCancel;
    private EditText editBtime;
    private EditText editBno;
    private EditText editRno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);
        //************ Getting the user type and the user number from the previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.userType = bundle.getInt("userType");
        this.userNo = bundle.getString("userNo");

        btnCancel = (Button)findViewById(R.id.btnBBookCancel);
        btnOK = (Button)findViewById(R.id.btnBBookConfirm);
        editBtime = (EditText)findViewById(R.id.editTextBBookBtime);
        editRno = (EditText)findViewById(R.id.editTextBBookRno);
        editBno = (EditText)findViewById(R.id.editTextBBookBno);

        if(userType == 2){
            editRno.setText(userNo);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowBookActivity.this.finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Rno, Bno, Btime;
                if(BorrowBookActivity.this.userType == 1){//admin
                    Rno = BorrowBookActivity.this.editRno.getText().toString().trim();
                    Bno = BorrowBookActivity.this.editBno.getText().toString().trim();
                    Btime = BorrowBookActivity.this.editBtime.getText().toString().trim();

                    DBoperater op = new DBoperater(BorrowBookActivity.this);
                    SQLiteDatabase db = op.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select * from Reader where Rno = ?", new String[]{Rno});
                    Cursor cursor1 = db.rawQuery("select Bno,Bnumber from Book where Bno = ?", new String[]{Bno});


                    if(cursor1.moveToNext() && cursor.moveToNext()){
                        int bookRemain;
                        bookRemain = cursor1.getInt(cursor1.getColumnIndex("Bnumber"));
                        if(bookRemain > 0){
                            db.execSQL("insert into Borrow values(?, ?, ?)", new Object[]{Rno, Bno, Btime});
                            db.execSQL("update Book set Bnumber = Bnumber-1 where Bno = ?", new Object[]{Bno});
                            BorrowBookActivity.this.editRno.setText("");
                            BorrowBookActivity.this.editBno.setText("");
                            new dialog(BorrowBookActivity.this, "Insert succeeded!");
                        }
                        else{
                            new dialog(BorrowBookActivity.this, "Book count remains 0, operation denied!");
                        }
                    }
                    else{
                        new dialog(BorrowBookActivity.this, "Book or reader not exist in the database!");
                    }
                }
                else if(BorrowBookActivity.this.userType == 2){//reader
                    Rno = BorrowBookActivity.this.userNo;
                    Bno = BorrowBookActivity.this.editBno.getText().toString().trim();
                    Btime = BorrowBookActivity.this.editBtime.getText().toString().trim();

                    DBoperater op = new DBoperater(BorrowBookActivity.this);
                    SQLiteDatabase db = op.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select Bno,Bnumber from Book where Bno = ?", new String[]{Bno});


                    if(cursor.moveToNext()){
                        int bookRemain;
                        bookRemain = cursor.getInt(cursor.getColumnIndex("Bnumber"));
                        if(bookRemain > 0){
                            db.execSQL("insert into Borrow values(?, ?, ?)", new Object[]{Rno, Bno, Btime});
                            db.execSQL("update Book set Bnumber = Bnumber-1 where Bno = ?", new Object[]{Bno});
                            BorrowBookActivity.this.editRno.setText("");
                            BorrowBookActivity.this.editBno.setText("");
                            new dialog(BorrowBookActivity.this, "Insert succeeded!");
                        }
                        else{
                            new dialog(BorrowBookActivity.this, "Book count remains 0, operation denied!");
                        }
                    }
                    else{
                        new dialog(BorrowBookActivity.this, "Book not exist in the database!");
                    }
                }
                else{
                    new dialog(BorrowBookActivity.this, "No such user!");
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_borrow_book, menu);
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
