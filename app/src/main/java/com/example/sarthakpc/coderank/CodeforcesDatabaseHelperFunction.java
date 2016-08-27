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
public class CodeforcesDatabaseHelperFunction
{
    private static final String LOG_TAG="Testing";
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    private String mHandle;
    private long mUserId;
    private static final String allColumns[]={
            CodeforcesEntry._ID,
            CodeforcesEntry.PROFILE_NAME,
            CodeforcesEntry.USER_ID,
            CodeforcesEntry.OLD_RATING,
            CodeforcesEntry.CURRENT_RATING,
            CodeforcesEntry.MAX_RATING,
            CodeforcesEntry.RECENT_RATING_CHANGE,
            CodeforcesEntry.RECENT_COMP_GIVEN,
            CodeforcesEntry.RANK,
            CodeforcesEntry.MAX_RANK
    };

    public CodeforcesDatabaseHelperFunction(Context context, String handle, long user_id)
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
    public CodeforcesModel createNewUser(CodeforcesModel model)
    {
        ContentValues values=new ContentValues();

        values.put(CodeforcesEntry.PROFILE_NAME,mHandle);
        values.put(CodeforcesEntry.USER_ID,mUserId);
        values.put(CodeforcesEntry.OLD_RATING,model.getOldRating());
        values.put(CodeforcesEntry.CURRENT_RATING,model.getCurrentRating());
        values.put(CodeforcesEntry.MAX_RATING,model.getMaxRating());
        values.put(CodeforcesEntry.RECENT_RATING_CHANGE,model.getRecentRatingChange());
        values.put(CodeforcesEntry.RECENT_COMP_GIVEN,model.getRecentCompGiven());
        values.put(CodeforcesEntry.RANK,model.getRank());
        values.put(CodeforcesEntry.MAX_RANK,model.getMaxRank());

        long insert_id=database.insert(CodeforcesEntry.TABLE_NAME,null,values);
        model.setInsert_id(insert_id);
        Log.i(LOG_TAG,"HleperFunction: created new user Codeforces with id = "+insert_id);
        return model;
    }

    //check if user Exist
    public boolean userExist()
    {
        Cursor cursor=database.query(CodeforcesEntry.TABLE_NAME,allColumns,CodeforcesEntry.USER_ID+" = "+mUserId,null,null,null,null);
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
    public CodeforcesModel retrieveUser()
    {
        Cursor cursor=database.query(CodeforcesEntry.TABLE_NAME,allColumns,CodeforcesEntry.USER_ID+" = "+mUserId,null,null,null,null);
        CodeforcesModel model=new CodeforcesModel();
        //printing the no of users in the UserEntry
        Log.i(LOG_TAG,"CodeforcesRetrieveUser : no of users - "+cursor.getCount());

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                model.setInsert_id(cursor.getLong(cursor.getColumnIndex(CodeforcesEntry._ID)));
                model.setOldRating(cursor.getInt(cursor.getColumnIndex(CodeforcesEntry.OLD_RATING)));
                model.setCurrentRating(cursor.getInt(cursor.getColumnIndex(CodeforcesEntry.CURRENT_RATING)));
                model.setMaxRating(cursor.getInt(cursor.getColumnIndex(CodeforcesEntry.MAX_RATING)));
                model.setRecentRatingChange(cursor.getInt(cursor.getColumnIndex(CodeforcesEntry.RECENT_RATING_CHANGE)));
                model.setRecentCompGiven(cursor.getString(cursor.getColumnIndex(CodeforcesEntry.RECENT_COMP_GIVEN)));
                model.setRank(cursor.getString(cursor.getColumnIndex(CodeforcesEntry.RANK)));
                model.setRank(cursor.getString(cursor.getColumnIndex(CodeforcesEntry.MAX_RANK)));
                break;
            }
        }
        return model;
    }

    //update the new User
    public void updateUser(CodeforcesModel model)
    {
        ContentValues values=new ContentValues();
        values.put(CodeforcesEntry.PROFILE_NAME,mHandle);
        values.put(CodeforcesEntry.USER_ID,mUserId);
        values.put(CodeforcesEntry.OLD_RATING,model.getOldRating());
        values.put(CodeforcesEntry.CURRENT_RATING,model.getCurrentRating());
        values.put(CodeforcesEntry.MAX_RATING,model.getMaxRating());
        values.put(CodeforcesEntry.RECENT_RATING_CHANGE,model.getRecentRatingChange());
        values.put(CodeforcesEntry.RECENT_COMP_GIVEN,model.getRecentCompGiven());
        values.put(CodeforcesEntry.RANK,model.getRank());
        values.put(CodeforcesEntry.MAX_RANK,model.getMaxRank());

        long i=database.update(CodeforcesEntry.TABLE_NAME,values,CodeforcesEntry.USER_ID+" = "+mUserId,null);
        Log.i(LOG_TAG,"Updated the user of Codeforces Model");
    }
}
