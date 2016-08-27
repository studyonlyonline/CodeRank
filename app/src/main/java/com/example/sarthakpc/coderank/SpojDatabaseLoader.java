package com.example.sarthakpc.coderank;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Sarthakpc on 8/6/2016.
 */

public class SpojDatabaseLoader extends AsyncTaskLoader<SpojModel>
{
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private SpojModel codersModel;
    private SpojDatabseHelperFunction mSpojDatabseHelperFunction;

    public SpojDatabaseLoader(Context context, String username,long user_id)
    {
        super(context);
        mHandle=username;
        codersModel=new SpojModel();
        mSpojDatabseHelperFunction=new SpojDatabseHelperFunction(getContext(),mHandle,user_id);
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public SpojModel loadInBackground()
    {
        //open the connection
        mSpojDatabseHelperFunction.open();

        if(mSpojDatabseHelperFunction.userExist())
        {
            codersModel=mSpojDatabseHelperFunction.retrieveUser();
        }
        mSpojDatabseHelperFunction.close();
        return codersModel;
    }

}
