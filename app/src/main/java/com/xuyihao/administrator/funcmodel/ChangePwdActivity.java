package com.xuyihao.administrator.funcmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuyihao.administrator.dbdemo.*;

import com.xuyihao.administrator.dbdemo.R;

public class ChangePwdActivity extends Activity {

    private String userNo;
    private String userPwd;

    private int userType;//'1' stands for admin and '2' stands for reader.

    private Button btnOK;
    private Button btnCancel;

    private EditText txtLastPwd;
    private EditText txtNewPwd;
    private EditText txtNewPwdConfirm;

    private String lastPwd;
    private String newPwd;
    private String newPwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.userNo = bundle.getString("userNo");
        this.userPwd = bundle.getString("userPwd");
        this.userType = bundle.getInt("userType");
        //new dialog(this, this.userNo+"  "+this.userPwd+"  "+this.userType);//Check while the passing user number or user password is right by dialog.
        txtLastPwd = (EditText)findViewById(R.id.editTextLastPwd);
        txtNewPwd = (EditText)findViewById(R.id.editTextNewPwd);
        txtNewPwdConfirm = (EditText)findViewById(R.id.editTextNewPwdConfirm);
        btnOK = (Button)findViewById(R.id.btnChangePwdConfirm);
        btnCancel = (Button)findViewById(R.id.btnChangePwdCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePwdActivity.this.finish();
            }
        });


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePwdActivity.this.lastPwd = txtLastPwd.getText().toString().trim();
                ChangePwdActivity.this.newPwd = txtNewPwd.getText().toString().trim();
                ChangePwdActivity.this.newPwdConfirm = txtNewPwdConfirm.getText().toString().trim();
                int kind = ChangePwdActivity.this.userType;
                if((ChangePwdActivity.this.lastPwd.equals("") == true) || (ChangePwdActivity.this.newPwd.equals("")) || (ChangePwdActivity.this.newPwdConfirm.equals(""))){
                    new dialog(ChangePwdActivity.this, "Please input something and do not leave the editBox empty!");
                }
                else{
                    if((ChangePwdActivity.this.lastPwd.equals(ChangePwdActivity.this.userPwd)) != true){
                        new dialog(ChangePwdActivity.this, "Old password is wrong! Input it again or quit!");
                    }
                    else{
                        if ((ChangePwdActivity.this.newPwd.equals(ChangePwdActivity.this.newPwdConfirm)) != true) {
                            new dialog(ChangePwdActivity.this, "New password confirming erro! Check your inputing!");
                        }
                        else{
                            if(kind == 1){//user type is admin
                                DBoperater op = new DBoperater(ChangePwdActivity.this);
                                SQLiteDatabase db = op.getWritableDatabase();
                                db.execSQL("update Admin set Apwd = ? where Ano = ?", new Object[]{ChangePwdActivity.this.newPwd, ChangePwdActivity.this.userNo});
                                ChangePwdActivity.this.userPwd = ChangePwdActivity.this.newPwd;

                                Intent intent2 = new Intent();
                                Bundle bun = new Bundle();
                                bun.putString("backPwd", ChangePwdActivity.this.userPwd);
                                intent2.putExtras(bun);
                                ChangePwdActivity.this.setResult(1, intent2);

                                new dialog(ChangePwdActivity.this, "Operating succeed!");
                                ChangePwdActivity.this.finish();
                            }
                            else{//user type is reader
                                DBoperater op = new DBoperater(ChangePwdActivity.this);
                                SQLiteDatabase db = op.getWritableDatabase();
                                db.execSQL("update Reader set Rpwd = ? where Rno = ?", new Object[]{ChangePwdActivity.this.newPwd, ChangePwdActivity.this.userNo});
                                ChangePwdActivity.this.userPwd = ChangePwdActivity.this.newPwd;

                                Intent intent3 = new Intent();
                                Bundle bun2 = new Bundle();
                                bun2.putString("backPwd", ChangePwdActivity.this.userPwd);
                                intent3.putExtras(bun2);
                                ChangePwdActivity.this.setResult(2, intent3);

                                new dialog(ChangePwdActivity.this, "Operating succeed!");
                                ChangePwdActivity.this.finish();
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_pwd, menu);
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
