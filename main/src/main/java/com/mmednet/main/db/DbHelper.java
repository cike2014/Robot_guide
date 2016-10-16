package com.mmednet.main.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mmednet.main.bean.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by alpha on 2016/9/2.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME="robot.db";
    private static final int DATABASE_VERSION=1;
    private static DbHelper mDbHelper;
    private Context context;

    private static final String TAG=DbHelper.class.getSimpleName();

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public static final DbHelper getInstance(Context context) {
        context=context.getApplicationContext();
        if (mDbHelper == null) {
            synchronized (DbHelper.class) {
                if (mDbHelper == null) {
                    mDbHelper=new DbHelper(context);
                }
            }
        }
        return mDbHelper;
    }


    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        int i=-1;
        while ((i=is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
