package com.xuyihao.administrator.dbdemo;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;
import android.app.Dialog;

public class MainActivity extends Activity{

    private Button btnLogin;
    private Button btnQuit;
    private String userNo;
    private String userPwd;
    private EditText uno;
    private EditText upw;

    private DBoperater ope;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnLogin = (Button)findViewById(R.id.btnlogin);
        this.btnQuit = (Button)findViewById(R.id.btnQuit);
        this.uno = (EditText)findViewById(R.id.editTxtUserNo);
        this.upw = (EditText)findViewById(R.id.editTxtUserPwd);

        this.ope = new DBoperater(this);
        this.db = ope.getReadableDatabase();

        this.btnQuit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        this.btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.this.userNo = MainActivity.this.uno.getText().toString();
                MainActivity.this.userPwd = MainActivity.this.upw.getText().toString();

                String pwd1, pwd2;
                Cursor cursor1 = MainActivity.this.db.rawQuery("select Apwd from Admin where Ano = ?", new String[]{MainActivity.this.userNo});
                Cursor cursor2 = MainActivity.this.db.rawQuery("select Rpwd from Reader where Rno = ?", new String[]{MainActivity.this.userNo});

                if(cursor1.moveToNext()){
                    pwd1 = (cursor1.getString(cursor1.getColumnIndex("Apwd"))).trim();
                }
                else{
                    pwd1 = "";
                }

                if(cursor2.moveToNext()){
                    pwd2 = (cursor2.getString(cursor2.getColumnIndex("Rpwd"))).trim();
                }
                else {
                    pwd2 = "";
                }

                if(MainActivity.this.userNo.equals("") || MainActivity.this.userPwd.equals("")){
                    new dialog(MainActivity.this, "Please input something!");
                }
                else if(pwd1.equals("") && pwd2.equals("")){
                    new dialog(MainActivity.this, "No such person exist!");
                }
                else if(pwd1.equals(MainActivity.this.userPwd)){//Admin
                    Intent intent = new Intent(MainActivity.this, AdActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userNo", MainActivity.this.userNo);
                    bundle.putString("userPwd", MainActivity.this.userPwd);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if(pwd2.equals(MainActivity.this.userPwd)) {//Reader
                    Intent intent = new Intent(MainActivity.this, ReActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userNo", MainActivity.this.userNo);
                    bundle.putString("userPwd", MainActivity.this.userPwd);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    new dialog(MainActivity.this, "Wrong pssword!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
