package com.xuyihao.administrator.dbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/8/3.
 */
public class DBoperater extends SQLiteOpenHelper {

    public DBoperater(Context context) {
        super(context, "mydatabase.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Book (Bno char(10) primary key, Bname char(30), Bauthor char(20), Bpress char(30), Bnumber int)");
        db.execSQL("create table Reader (Rno char(10) primary key, Rname char(20), Rsex char(2), Rphone char(20), Rpwd char(16))");
        db.execSQL("create table Admin (Ano char(10) primary key, Apwd char(16))");
        db.execSQL("create table Borrow (Rno char(10), Bno char(10), Btime char(10), primary key (Rno, Bno),foreign key(Rno) references Reader(Rno), foreign key(Bno) references Book(Bno))");
        db.execSQL("create view borrowsituation as select Reader.Rno, Reader.Rname, Book.Bno, Book.Bname, Borrow.Btime from Reader, Book, Borrow where Borrow.Bno = Book.Bno and Borrow.Rno = Reader.Rno");

        db.execSQL("insert into Admin values('1001', '123456')");
        db.execSQL("insert into Reader values('2001', 'Johnson', 'M', '15700083767', '123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
