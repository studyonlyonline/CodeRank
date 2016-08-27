package com.example.sarthakpc.coderank;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Sarthakpc on 8/6/2016.
 */
public class CodechefDatabaseLoader extends AsyncTaskLoader<CodechefModel>
{
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private CodechefModel codersModel;
    private CodechefDatabaseHelperFunction mCodechefDatabaseHelperFunction;

    public CodechefDatabaseLoader(Context context, String username, long user_id)
    {

        super(context);
        mHandle=username;
        codersModel=new CodechefModel();
        mCodechefDatabaseHelperFunction=new CodechefDatabaseHelperFunction(getContext(),mHandle,user_id);
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public CodechefModel loadInBackground()
    {
        //open the connection
        mCodechefDatabaseHelperFunction.open();

        if(mCodechefDatabaseHelperFunction.userExist())
        {
            codersModel=mCodechefDatabaseHelperFunction.retrieveUser();
        }
        mCodechefDatabaseHelperFunction.close();
        return codersModel;
    }
}
