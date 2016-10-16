package com.mmednet.robotGuide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mmednet.robotGuide.bean.IntelligentGuideDepartment;
import com.mmednet.robotGuide.bean.IntelligentGuideSymptomDepartment;
import com.mmednet.robotGuide.bean.IntelligentGuideSymptoms;

/**
 * Created by alpha on 2016/9/2.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "robot.db";
    private static final int DATABASE_VERSION = 1;
    private static DbHelper mDbHelper;
    private Context context;

    private static final String TAG = DbHelper.class.getSimpleName();

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static final DbHelper getInstance(Context context) {
        context = context.getApplicationContext();
        if (mDbHelper == null) {
            synchronized (DbHelper.class) {
                if (mDbHelper == null) {
                    mDbHelper = new DbHelper(context);
                }
            }
        }
        return mDbHelper;
    }

//    private String sql = "CREATE TABLE `sys_intelligent_guide_symptom` (\n" +
//            "  `id` INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
//            "  `parts` VARCHAR,\n" +
//            "  `symptom` VARCHAR\n" +
//            ") ";
//    private String sql2 = "INSERT INTO `sys_intelligent_guide_symptom` VALUES (1, '全身', '发热'), (2, '全身', '抽搐惊厥');";


    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        Log.d(TAG, "执行吗？");

        //sys_intelligent_guide_department
        database.execSQL(IntelligentGuideDepartment.getCreateSql());
        database.execSQL(IntelligentGuideDepartment.getInsertSql());


//        //sys_intelligent_guide_symptom
        database.execSQL(IntelligentGuideSymptoms.getCreateSql());
        database.execSQL(IntelligentGuideSymptoms.getInsertSql());

//        //sys_intelligent_guide_symptom_department
        database.execSQL(IntelligentGuideSymptomDepartment.getCreateSql());
        database.execSQL(IntelligentGuideSymptomDepartment.getInsertSql());

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
