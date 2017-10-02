package com.maimai.zz.maimai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 92198 on 2017/10/2.
 */

public class SqlUserBookContribution extends SQLiteOpenHelper {
    //Data bsse
    public static final String CREATE_UserBookContribution = "create table UserBookContribution ("+
            "id integer primary key autoincrement,"+
            "StudentID text,"+
            "BookCode text,"+
            "BookPicture blob)";

    private Context mContext;


    public SqlUserBookContribution(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_UserBookContribution);
        Toast.makeText(mContext,"Created succed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
