package com.example.sarthakpc.coderank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sarthakpc.coderank.UserContract.UserEntry;

import com.example.sarthakpc.coderank.UserContract.CodechefEntry;
import com.example.sarthakpc.coderank.UserContract.SpojEntry;

import static com.example.sarthakpc.coderank.UserContract.*;

/**
 * Created by Sarthakpc on 8/1/2016.
 */
public class UsersDbOpenHelper extends SQLiteOpenHelper
{

    private static final String LOG_TAG="Testing";
    private static final String DATABASE_NAME="CompData";
    private static final int DATABASE_VERSION=4;

    private static final String TABLE_CREATE_USERS="" +
            "CREATE TABLE "+ UserEntry.TABLE_NAME +" ( "+
            UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            UserEntry.FRIEND_NAME+ " TEXT UNIQUE, "+
            UserEntry.CODECHEF_HANDLE + " TEXT, "+
            UserEntry.CODEFORCES_HANDLE + " TEXT, "+
            UserEntry.HACKEREARTH_HANDLE + " TEXT, "+
            UserEntry.HACKERRANK_HANDLE + " TEXT, "+
            UserEntry.SPOJ_HANDLE + " TEXT"+
            ");";

    private static final String TABLE_CREATE_CODECHEF=""+
            "CREATE TABLE "+ CodechefEntry.TABLE_NAME + " ( "+
            CodechefEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CodechefEntry.USER_ID+ " INTEGER UNIQUE NOT NULL, "+
            CodechefEntry.PROFILE_NAME + " TEXT, "+
            CodechefEntry.PROBLEMS_SOLVED + " INTEGER, "+
            CodechefEntry.LONG_GLOBAL_RANK+ " INTEGER, "+
            CodechefEntry.LONG_COUNTRY_RANK+ " INTEGER, "+
            CodechefEntry.LONG_RATING+ " TEXT, "+
            CodechefEntry.SHORT_GLOBAL_RANK+ " INTEGER, "+
            CodechefEntry.SHORT_COUNTRY_RANK+ " INTEGER, "+
            CodechefEntry.SHORT_RATING+ " TEXT, "+
            CodechefEntry.LTIME_GLOABAL_RANK+ " INTEGER, "+
            CodechefEntry.LTIME_COUNTRY_RANK+ " INTEGER, "+
            CodechefEntry.LTIME_RATING+ " TEXT"+
            ");";

    private static final String TABLE_CREATE_SPOJ=""+
            "CREATE TABLE "+ SpojEntry.TABLE_NAME + " ( "+
            SpojEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            SpojEntry.USER_ID+ " INTEGER UNIQUE NOT NULL, "+
            SpojEntry.PROFILE_NAME + " TEXT, "+
            SpojEntry.WORLD_RANK + " TEXT, "+
            SpojEntry.PROBLEMS_SOLVED + " INTEGER, "+
            SpojEntry.SOLUTIONS_SUBMITTED + " INTEGER "+
            ");";

    private static final String TABLE_CREATE_CODEFORCES=""+
            "CREATE TABLE "+ CodeforcesEntry.TABLE_NAME + " ( "+
            CodeforcesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CodeforcesEntry.USER_ID+ " INTEGER UNIQUE NOT NULL, "+
            CodeforcesEntry.PROFILE_NAME + " TEXT, "+
            CodeforcesEntry.OLD_RATING + " INTEGER, "+
            CodeforcesEntry.CURRENT_RATING + " INTEGER, "+
            CodeforcesEntry.MAX_RATING + " INTEGER, "+
            CodeforcesEntry.RECENT_RATING_CHANGE + " INTEGER, "+
            CodeforcesEntry.RECENT_COMP_GIVEN + " TEXT, "+
            CodeforcesEntry.RANK + " TEXT, "+
            CodeforcesEntry.MAX_RANK + " TEXT "+
            ");";



    public UsersDbOpenHelper(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(TABLE_CREATE_USERS);
        sqLiteDatabase.execSQL(TABLE_CREATE_CODECHEF);
        sqLiteDatabase.execSQL(TABLE_CREATE_SPOJ);
        sqLiteDatabase.execSQL(TABLE_CREATE_CODEFORCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CodechefEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SpojEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CodeforcesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
