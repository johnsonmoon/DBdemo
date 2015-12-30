package com.xuyihao.administrator.dbdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.xuyihao.administrator.funcmodel.AlreadyBorrowDetilActivity;
import com.xuyihao.administrator.funcmodel.BorrowBookActivity;
import com.xuyihao.administrator.funcmodel.ChangePwdActivity;
import com.xuyihao.administrator.funcmodel.LookBookActivity;
import com.xuyihao.administrator.funcmodel.ReaderDetailInfomationActivity;
import com.xuyihao.administrator.funcmodel.ReturnBookDetailActivity;


public class ReActivity extends Activity {

    private String readerNo;
    private String readerPwd;
    private Button changePwd;
    private Button btnQuit;
    private Button btnLookBook;
    private Button btnReaderInfo;
    private Button btnLookBorrowInfo;
    private Button btnBorrowBook;
    private Button btnReturnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        this.readerNo = bundle.getString("userNo");
        this.readerPwd = bundle.getString("userPwd");
        //new dialog(this, readerNo+readerPwd);//Check while the passing user number or user password is right by dialog.

        btnQuit = (Button)findViewById(R.id.btnReaderQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReActivity.this.finish();
            }
        });

        changePwd = (Button)findViewById(R.id.btnReaderChangePwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReActivity.this, ChangePwdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userNo", ReActivity.this.readerNo);
                bundle.putString("userPwd", ReActivity.this.readerPwd);
                bundle.putInt("userType", 2);//'2' stands for reader type.
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });

        btnLookBook = (Button)findViewById(R.id.btnReaderLookBook);
        btnLookBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ReActivity.this, LookBookActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("userType", 2);
                intent2.putExtras(bundle2);
                startActivity(intent2);
            }
        });

        btnReaderInfo = (Button)findViewById(R.id.btnReaderLookReader);
        btnReaderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ReActivity.this, ReaderDetailInfomationActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("userNo", ReActivity.this.readerNo);
                intent3.putExtras(bundle3);
                startActivity(intent3);
            }
        });

        btnLookBorrowInfo = (Button)findViewById(R.id.btnReaderLookBorrowBook);
        btnLookBorrowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(ReActivity.this, AlreadyBorrowDetilActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("userNo", ReActivity.this.readerNo);
                intent4.putExtras(bundle4);
                startActivity(intent4);
            }
        });

        btnBorrowBook = (Button)findViewById(R.id.btnReaderBorrowBook);
        btnBorrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(ReActivity.this, BorrowBookActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("userType", 2);
                bundle5.putString("userNo", ReActivity.this.readerNo);
                intent5.putExtras(bundle5);
                startActivity(intent5);
            }
        });

        btnReturnBook = (Button)findViewById(R.id.btnReaderReturnBook);
        btnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(ReActivity.this, ReturnBookDetailActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putString("userNo", ReActivity.this.readerNo);
                intent6.putExtras(bundle6);
                startActivity(intent6);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == 2){
            Bundle b = data.getExtras();
            ReActivity.this.readerPwd = b.getString("backPwd");
            //new dialog(ReActivity.this, ReActivity.this.readerPwd);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_re, menu);
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
