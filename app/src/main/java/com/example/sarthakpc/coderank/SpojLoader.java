package com.example.sarthakpc.coderank;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Sarthakpc on 8/6/2016.
 */

public class SpojLoader extends AsyncTaskLoader<SpojModel>
{
    private long mUserId;
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private static final String SPOJ_USER_URL="http://www.spoj.com/users/";
    private SpojModel codersModel;

    public SpojLoader(Context context, String username,long userId)
    {
        super(context);
        mHandle=username;
        mUserId=userId;
        codersModel=new SpojModel();
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public SpojModel loadInBackground()
    {
        Log.i(LOG_TAG,"SpojLoader running for handle  "+mHandle);
        SpojModel newModel=extractData();
        if (newModel!=null)
            newModel=insertOrUpdate(newModel);
        return newModel;
    }

    public SpojModel extractData()
    {

        if(mHandle==null)
            return null;

        try
        {
            Document doc= Jsoup.connect(SPOJ_USER_URL+mHandle).get();

//          get the world
            getWorldRank(doc);

//            get the problem stats
            getProblemStats(doc);

            return codersModel;
        }
        catch (IOException e)
        {
            Log.i(LOG_TAG,"Extract Spoj data Error: Could not work out the JSOUP statements , in the catch block");
            e.printStackTrace();
        }
        return null;
    }

    private void getWorldRank(Document doc)
    {
        try
        {
            Element profileDiv = doc.getElementById("user-profile-left");

            if(profileDiv==null)
            {
                Log.i(LOG_TAG," ExtractSPojDAta --- "+mHandle+" does not exist");
                return;
            }

            Element getWorldRank = profileDiv.select("p").get(2);
            Log.i(LOG_TAG, "ExtractSpojData-  world rank : " + getWorldRank.text());

            codersModel.setWorldRank(getWorldRank.text());
        }
        catch (Exception ex)
        {
            Log.i(LOG_TAG, "ExtractSpojData- in catch block of getWorldRank");
            ex.printStackTrace();
        }
    }

    private void getProblemStats(Document doc)
    {
        try
        {
            Elements profileInfoDataStats=doc.getElementsByClass("profile-info-data-stats");

            if (profileInfoDataStats.isEmpty())
            {
                Log.i(LOG_TAG," ExtractSPojDAta --- "+mHandle+" does not exist");
                return;
            }

            Element problemsData=profileInfoDataStats.get(0);
            Elements data=problemsData.select("dd");
            Element noOfProblemsSolved=data.get(0);
            Element noOfSolutionsSubmitted=data.get(1);

            Log.i(LOG_TAG,"ExtractSpojData-      problems solved "+noOfProblemsSolved.text());
            Log.i(LOG_TAG,"ExtractSpojData-      solutions submitted "+noOfSolutionsSubmitted.text());

            codersModel.setProblemsSolved(Integer.parseInt(noOfProblemsSolved.text()));
            codersModel.setSolutionsSubmitted(Integer.parseInt(noOfSolutionsSubmitted.text()));
        }
        catch (Exception ex)
        {
            Log.i(LOG_TAG,"ExtractSpojData-      in catch block of getProblemStats");
            ex.printStackTrace();
        }
    }

    private SpojModel insertOrUpdate(SpojModel model)
    {

        Log.i(LOG_TAG,"In insertOrUpdate method of SpojLoader");
        SpojDatabseHelperFunction spojDatabseHelperFunction=new SpojDatabseHelperFunction(getContext(),mHandle,mUserId);
        spojDatabseHelperFunction.open();
        if(spojDatabseHelperFunction.userExist())
        {
            spojDatabseHelperFunction.updateUser(model);
        }
        else
        {
            model=spojDatabseHelperFunction.createNewUser(model);
        }
        spojDatabseHelperFunction.close();
        Log.i(LOG_TAG,"Exit insertOrUpdate method of SpojLoader");
        return model;
    }
}

