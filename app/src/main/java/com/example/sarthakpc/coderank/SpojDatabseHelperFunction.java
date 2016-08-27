package com.example.sarthakpc.coderank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.sarthakpc.coderank.UserContract.SpojEntry;

/**
 * Created by Sarthakpc on 8/6/2016.
 */
public class SpojDatabseHelperFunction
{
    private static final String LOG_TAG="Testing";
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    private String mHandle;
    private long mUserId;

    private static final String allColumns[]={
            SpojEntry._ID,
            SpojEntry.PROFILE_NAME,
            SpojEntry.USER_ID,
            SpojEntry.WORLD_RANK,
            SpojEntry.PROBLEMS_SOLVED,
            SpojEntry.SOLUTIONS_SUBMITTED
    };

    public SpojDatabseHelperFunction(Context context,String handle,long user_id)
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
    public SpojModel createNewUser(SpojModel model)
    {
        ContentValues values=new ContentValues();

        values.put(SpojEntry.PROFILE_NAME,mHandle);
        values.put(SpojEntry.USER_ID,mUserId);
        values.put(SpojEntry.PROBLEMS_SOLVED,model.getProblemsSolved());
        values.put(SpojEntry.WORLD_RANK,model.getWorldRank());
        values.put(SpojEntry.SOLUTIONS_SUBMITTED,model.getSolutionsSubmitted());

        long insert_id=database.insert(SpojEntry.TABLE_NAME,null,values);
        model.setInsert_id(insert_id);
        Log.i(LOG_TAG,"HleperFunction: created new user with id = "+insert_id);
        return model;
    }

    //check if user Exist
    public boolean userExist()
    {
        Cursor cursor=database.query(SpojEntry.TABLE_NAME,allColumns,SpojEntry.USER_ID+" = "+mUserId,null,null,null,null);
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
    public SpojModel retrieveUser()
    {
        Cursor cursor=database.query(SpojEntry.TABLE_NAME,allColumns,SpojEntry.USER_ID+" = "+mUserId,null,null,null,null);
        SpojModel model=new SpojModel();
        //printing the no of users in the UserEntry
        Log.i(LOG_TAG,"SpojRetrieveUser : no of users - "+cursor.getCount());

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                model.setInsert_id(cursor.getLong(cursor.getColumnIndex(SpojEntry._ID)));
                model.setWorldRank(cursor.getString(cursor.getColumnIndex(SpojEntry.WORLD_RANK)));
                model.setProblemsSolved(cursor.getInt(cursor.getColumnIndex(SpojEntry.PROBLEMS_SOLVED)));
                model.setSolutionsSubmitted(cursor.getInt(cursor.getColumnIndex(SpojEntry.SOLUTIONS_SUBMITTED)));
                break;
            }
        }
        return model;
    }

    //update the new User
    public void updateUser(SpojModel model)
    {
        ContentValues values=new ContentValues();
        values.put(SpojEntry.PROFILE_NAME,mHandle);
        values.put(SpojEntry.USER_ID,mUserId);
        values.put(SpojEntry.PROBLEMS_SOLVED,model.getProblemsSolved());
        values.put(SpojEntry.WORLD_RANK,model.getWorldRank());
        values.put(SpojEntry.SOLUTIONS_SUBMITTED,model.getSolutionsSubmitted());

        long i=database.update(SpojEntry.TABLE_NAME,values,SpojEntry.USER_ID+" = "+mUserId,null);
        Log.i(LOG_TAG,"Updated the user of Spoj Model");
    }



}
