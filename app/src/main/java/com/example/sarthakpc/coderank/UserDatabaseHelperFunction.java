package com.example.sarthakpc.coderank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sarthakpc.coderank.UserContract.UserEntry;

import java.util.ArrayList;

/**
 * Created by Sarthakpc on 8/2/2016.
 */
public class UserDatabaseHelperFunction
{
    private static final String LOG_TAG="Testing";

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String[] allColumns={
            UserEntry._ID,
            UserEntry.FRIEND_NAME,
            UserEntry.CODECHEF_HANDLE,
            UserEntry.CODEFORCES_HANDLE,
            UserEntry.SPOJ_HANDLE,
            UserEntry.HACKERRANK_HANDLE,
            UserEntry.HACKEREARTH_HANDLE
    };

    public UserDatabaseHelperFunction(Context context)
    {
        dbHelper=new UsersDbOpenHelper(context);
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
    }

    //creating the new User
    public UserModel createNewUser(UserModel model)
    {

        Log.i(LOG_TAG," on createUser the id is "+model.getId());
        if(userExist(model))
        {
            return updateUser(model);
        }

        ContentValues values=new ContentValues();

        values.put(UserEntry.FRIEND_NAME,model.getFriendName());
        values.put(UserEntry.CODECHEF_HANDLE,model.getCodechefHandle());
        values.put(UserEntry.CODEFORCES_HANDLE,model.getCodeforcesHandle());
        values.put(UserEntry.SPOJ_HANDLE,model.getSpojHandle());
        values.put(UserEntry.HACKERRANK_HANDLE,model.getHackerRankHandle());
        values.put(UserEntry.HACKEREARTH_HANDLE,model.getHackerEarthHandle());

        long insert_id=database.insert(UserEntry.TABLE_NAME,null,values);
        model.setId(insert_id);
        Log.i(LOG_TAG,"HleperFunction: created new user with id = "+insert_id);
        return model;
    }

    //retrieve all Users- display all the uesrs
    public ArrayList<UserModel> retrieveAllUsers()
    {
        ArrayList<UserModel> userModels=new ArrayList<>();

        Cursor cursor=database.query(UserEntry.TABLE_NAME,allColumns,null,null,null,null,UserEntry.FRIEND_NAME);

        //printing the no of users in the UserEntry
        Log.i(LOG_TAG,"HelperFunction: no of users - "+cursor.getCount());

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                UserModel model=new UserModel();
                model.setId(cursor.getLong(cursor.getColumnIndex(UserEntry._ID)));
                model.setFriendName(cursor.getString(cursor.getColumnIndex(UserEntry.FRIEND_NAME)));
                model.setCodechefHandle(cursor.getString(cursor.getColumnIndex(UserEntry.CODECHEF_HANDLE)));
                model.setCodeforcesHandle(cursor.getString(cursor.getColumnIndex(UserEntry.CODEFORCES_HANDLE)));
                model.setSpojHandle(cursor.getString(cursor.getColumnIndex(UserEntry.SPOJ_HANDLE)));
                model.setHackerRankHandle(cursor.getString(cursor.getColumnIndex(UserEntry.HACKERRANK_HANDLE)));
                model.setHackerRankHandle(cursor.getString(cursor.getColumnIndex(UserEntry.HACKEREARTH_HANDLE)));
                userModels.add(model);
            }
        }
        return userModels;
    }

        //check if user Exist
    public boolean userExist(UserModel model)
    {
        Log.i(LOG_TAG,"In userExist of UserDatabaseHelperFunction");
        Log.i(LOG_TAG," on clicking update user the id is "+model.getId());
        Cursor cursor=database.query(UserEntry.TABLE_NAME,allColumns,UserEntry._ID+ " = "+ model.getId(),null,null,null,null);
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //delete Item
    public void deleteUser(UserModel model)
    {
        database.delete(UserEntry.TABLE_NAME,UserEntry._ID+" = "+model.getId(),null);
        Log.i(LOG_TAG,"Delete User");
    }

    //update item
    public UserModel updateUser(UserModel model)
    {
        ContentValues values=new ContentValues();
        values.put(UserEntry.FRIEND_NAME,model.getFriendName());
        values.put(UserEntry.CODECHEF_HANDLE,model.getCodechefHandle());
        values.put(UserEntry.CODEFORCES_HANDLE,model.getCodeforcesHandle());
        values.put(UserEntry.SPOJ_HANDLE,model.getSpojHandle());
        values.put(UserEntry.HACKERRANK_HANDLE,model.getHackerRankHandle());
        values.put(UserEntry.HACKEREARTH_HANDLE,model.getHackerEarthHandle());
        long i=database.update(UserEntry.TABLE_NAME,values,UserEntry._ID+" = "+model.getId(),null);
        Log.i(LOG_TAG,"Update the user");
        return model;
    }



}
