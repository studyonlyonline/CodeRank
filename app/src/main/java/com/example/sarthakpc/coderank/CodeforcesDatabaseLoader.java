package com.example.sarthakpc.coderank;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Sarthakpc on 8/6/2016.
 */
public class CodeforcesDatabaseLoader extends AsyncTaskLoader<CodeforcesModel>
{
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private CodeforcesModel codersModel;
    private CodeforcesDatabaseHelperFunction mCodeforcesDatabaseHelperFunction;

    public CodeforcesDatabaseLoader(Context context, String username, long user_id)
    {
        super(context);
        mHandle=username;
        codersModel=new CodeforcesModel();
        mCodeforcesDatabaseHelperFunction=new CodeforcesDatabaseHelperFunction(getContext(),mHandle,user_id);
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public CodeforcesModel loadInBackground()
    {
        //open the connection
        mCodeforcesDatabaseHelperFunction.open();

        if(mCodeforcesDatabaseHelperFunction.userExist())
        {
            codersModel=mCodeforcesDatabaseHelperFunction.retrieveUser();
        }
        mCodeforcesDatabaseHelperFunction.close();
        return codersModel;
    }


}
