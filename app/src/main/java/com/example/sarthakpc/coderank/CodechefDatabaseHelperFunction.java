package com.example.sarthakpc.coderank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.sarthakpc.coderank.UserContract.*;

/**
 * Created by Sarthakpc on 8/6/2016.
 */
public class CodechefDatabaseHelperFunction
{
    private static final String LOG_TAG="Testing";
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    private String mHandle;
    private long mUserId;

    private static final String allColumns[]={
            CodechefEntry._ID,
            CodechefEntry.PROFILE_NAME,
            CodechefEntry.USER_ID,
            CodechefEntry.LONG_GLOBAL_RANK,
            CodechefEntry.LONG_COUNTRY_RANK,
            CodechefEntry.LONG_RATING,
            CodechefEntry.SHORT_GLOBAL_RANK,
            CodechefEntry.SHORT_COUNTRY_RANK,
            CodechefEntry.SHORT_RATING,
            CodechefEntry.LTIME_GLOABAL_RANK,
            CodechefEntry.LTIME_COUNTRY_RANK,
            CodechefEntry.LTIME_RATING
    };

    public CodechefDatabaseHelperFunction(Context context, String handle, long user_id)
    {
        dbHelper= new UsersDbOpenHelper(context);
        mHandle=handle;
        mUserId=user_id;
    }

    //open the database for writing
    public void open()
    {
        database=dbHelper.getWritableDatabase();
        Log.i(LOG_TAG," Database Opened ");
    }

    //closing the database for writing
    public void close()
    {
        dbHelper.close();
        Log.i(LOG_TAG," Database Closed ");
    }

    //creating the new User
    public CodechefModel createNewUser(CodechefModel model)
    {
        ContentValues values=new ContentValues();

        values.put(CodechefEntry.PROFILE_NAME,mHandle);
        values.put(CodechefEntry.USER_ID,mUserId);
        values.put(CodechefEntry.PROBLEMS_SOLVED,model.getProblem_solved());
        values.put(CodechefEntry.LONG_GLOBAL_RANK,model.getLongGLobalRank());
        values.put(CodechefEntry.LONG_COUNTRY_RANK,model.getLongCountryRank());
        values.put(CodechefEntry.LONG_RATING,model.getLongRating());
        values.put(CodechefEntry.SHORT_GLOBAL_RANK,model.getShortGlobalRank());
        values.put(CodechefEntry.SHORT_COUNTRY_RANK,model.getShortCountryRank());
        values.put(CodechefEntry.SHORT_RATING,model.getShortRating());
        values.put(CodechefEntry.LTIME_GLOABAL_RANK,model.getLunchtimeGlobalRank());
        values.put(CodechefEntry.LTIME_COUNTRY_RANK,model.getLunchtimeCountryRank());
        values.put(CodechefEntry.LTIME_RATING,model.getLunchtimeRating());

        long insert_id=database.insert(CodechefEntry.TABLE_NAME,null,values);
        model.setInsert_id(insert_id);
        Log.i(LOG_TAG,"HleperFunction: created new user with id = "+insert_id);
        return model;
    }

    //check if user Exist
    public boolean userExist()
    {
        Cursor cursor=database.query(CodechefEntry.TABLE_NAME,allColumns,CodechefEntry.USER_ID+" = "+mUserId,null,null,null,null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //read particular user
    public CodechefModel retrieveUser()
    {
        Cursor cursor=database.query(CodechefEntry.TABLE_NAME,allColumns,CodechefEntry.USER_ID+" = "+mUserId,null,null,null,null);
        CodechefModel model=new CodechefModel();
        //printing the no of users in the UserEntry
        Log.i(LOG_TAG,"CodechefRetrieveUser : no of users - "+cursor.getCount());

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                model.setInsert_id(cursor.getLong(cursor.getColumnIndex(CodechefEntry._ID)));
                model.setLongGLobalRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.LONG_GLOBAL_RANK)));
                model.setLongCountryRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.LONG_COUNTRY_RANK)));
                model.setLongRating(cursor.getString(cursor.getColumnIndex(CodechefEntry.LONG_RATING)));
                model.setShortGlobalRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.SHORT_GLOBAL_RANK)));
                model.setShortRating(cursor.getString(cursor.getColumnIndex(CodechefEntry.SHORT_RATING)));
                model.setLunchtimeGlobalRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.LTIME_GLOABAL_RANK)));
                model.setLunchtimeCountryRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.LTIME_COUNTRY_RANK)));
                model.setLunchtimeRating(cursor.getString(cursor.getColumnIndex(CodechefEntry.LTIME_RATING)));

                model.setShortCountryRank(cursor.getInt(cursor.getColumnIndex(CodechefEntry.SHORT_COUNTRY_RANK)));
                break;
            }
        }
        return model;
    }

    //update the new User
    public void updateUser(CodechefModel model)
    {
        ContentValues values=new ContentValues();

        values.put(CodechefEntry.PROFILE_NAME,mHandle);
        values.put(CodechefEntry.USER_ID,mUserId);
        values.put(CodechefEntry.PROBLEMS_SOLVED,model.getProblem_solved());
        values.put(CodechefEntry.LONG_GLOBAL_RANK,model.getLongGLobalRank());
        values.put(CodechefEntry.LONG_COUNTRY_RANK,model.getLongCountryRank());
        values.put(CodechefEntry.LONG_RATING,model.getLongRating());
        values.put(CodechefEntry.SHORT_GLOBAL_RANK,model.getShortGlobalRank());
        values.put(CodechefEntry.SHORT_COUNTRY_RANK,model.getShortCountryRank());
        values.put(CodechefEntry.SHORT_RATING,model.getShortRating());
        values.put(CodechefEntry.LTIME_GLOABAL_RANK,model.getLunchtimeGlobalRank());
        values.put(CodechefEntry.LTIME_COUNTRY_RANK,model.getLunchtimeCountryRank());
        values.put(CodechefEntry.LTIME_RATING,model.getLunchtimeRating());

        long i=database.update(CodechefEntry.TABLE_NAME,values, CodechefEntry.USER_ID+" = "+mUserId,null);
        Log.i(LOG_TAG,"Updated the user of Codechef Model");
    }

}
