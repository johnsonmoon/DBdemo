package com.xuyihao.administrator.dbdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.xuyihao.administrator.funcmodel.AddAdminActivity;
import com.xuyihao.administrator.funcmodel.AddBookActivity;
import com.xuyihao.administrator.funcmodel.AddReaderActivity;
import com.xuyihao.administrator.funcmodel.AlreadyBorrowChoseReaderActivity;
import com.xuyihao.administrator.funcmodel.BorrowBookActivity;
import com.xuyihao.administrator.funcmodel.ChangePwdActivity;
import com.xuyihao.administrator.funcmodel.LookBookActivity;
import com.xuyihao.administrator.funcmodel.LookReaderActivity;
import com.xuyihao.administrator.funcmodel.ReturnBookActivity;


public class AdActivity extends Activity {

    private String adminNo;
    private String adminPwd;
    private Button btnChangePwd;
    private Button btnQuit;
    private Button btnAddBook;
    private Button btnAddReader;
    private Button btnAddAdmin;
    private Button btnLookBook;
    private Button btnLookReader;
    private Button btnAlreadyBorrow;
    private Button btnBorrowBook;
    private Button btnReturnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        this.adminNo = bundle.getString("userNo");
        this.adminPwd = bundle.getString("userPwd");
        //new dialog(this, this.adminNo+this.adminPwd);//Check while the passing user number or user password is right by dialog.

        btnQuit = (Button)findViewById(R.id.btnAdminQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdActivity.this.finish();
            }
        });

        btnChangePwd = (Button)findViewById(R.id.btnAdminChangePwd);
        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdActivity.this, ChangePwdActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userNo", AdActivity.this.adminNo);
                bundle.putString("userPwd", AdActivity.this.adminPwd);
                bundle.putInt("userType", 1);//'1' stands for administrator user.
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        btnAddBook = (Button)findViewById(R.id.btnAdminAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        btnAddReader = (Button)findViewById(R.id.btnAdminAddReader);
        btnAddReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AdActivity.this, AddReaderActivity.class);
                startActivity(intent1);
            }
        });

        btnAddAdmin = (Button)findViewById(R.id.btnAdminAddAdmin);
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdActivity.this, AddAdminActivity.class);
                startActivity(intent2);
            }
        });

        btnLookBook = (Button)findViewById(R.id.btnAdminLookBook);
        btnLookBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdActivity.this, LookBookActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("userType", 1);
                intent3.putExtras(bundle2);
                startActivity(intent3);
            }
        });

        btnLookReader = (Button)findViewById(R.id.btnAdminLookReader);
        btnLookReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AdActivity.this, LookReaderActivity.class);
                startActivity(intent4);
            }
        });

        btnAlreadyBorrow = (Button)findViewById(R.id.btnAdminLookAreadyBorrow);
        btnAlreadyBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(AdActivity.this, AlreadyBorrowChoseReaderActivity.class);
                startActivity(intent5);
            }
        });

        btnBorrowBook = (Button)findViewById(R.id.btnAdminBorrowBook);
        btnBorrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(AdActivity.this, BorrowBookActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putInt("userType", 1);
                bundle6.putString("userNo", AdActivity.this.adminNo);
                intent6.putExtras(bundle6);
                startActivity(intent6);
            }
        });

        btnReturnBook = (Button)findViewById(R.id.btnAdminReturnBook);
        btnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(AdActivity.this, ReturnBookActivity.class);
                startActivity(intent7);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            Bundle b = data.getExtras();
            AdActivity.this.adminPwd = b.getString("backPwd");
            //new dialog(AdActivity.this, AdActivity.this.adminPwd);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ad, menu);
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
