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
 * Created by Sarthakpc on 8/1/2016.
 */
public class CodechefLoader extends AsyncTaskLoader<CodechefModel>
{
    private long mUserId;
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private static final String CODECHEF_USER_URL="https://www.codechef.com/users/";
    private CodechefModel codersModel;

    public CodechefLoader(Context context, String username,long userId)
    {
        super(context);
        mHandle=username;
        mUserId=userId;
        codersModel=new CodechefModel();
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }


    @Override
    public CodechefModel loadInBackground()
    {
        Log.i(LOG_TAG,"CodechefLoader running for handle  "+mHandle);
        CodechefModel newModel=extractData();
        if (newModel!=null)
            newModel=insertOrUpdate(newModel);
        return newModel;
    }

    public CodechefModel extractData()
    {
        if(mHandle==null)
            return null;

        try
        {
            Document doc= Jsoup.connect(CODECHEF_USER_URL+mHandle).get();

            //get the username
            Elements content=doc.getElementsByClass("user-name-box");
            String name=content.text();
            codersModel.setUsername(name);
            Log.i(LOG_TAG,"Extract Codechef Data: Profile name : "+name);

//          get the problem stats
            getProblemStats(doc);

//            get the ratings and ranks
            getRating(doc);

            return codersModel;
        }
        catch (IOException e)
        {
            Log.i(LOG_TAG,"Error: Could not work out the JSOUP statements , in the catch block");
            e.printStackTrace();
        }
        return null;
    }

    //get the problem stats
    private void getProblemStats(Document doc)
    {
        Log.i(LOG_TAG," start executing getPRoblemStats");

        try
        {
            Element getTable=doc.getElementById("problem_stats");
            Elements getTrs=getTable.select("tr");
            Element getTr=getTrs.get(1);
            Elements getTrData=getTr.select("td");
            Element problemSolved=getTrData.get(0);
            codersModel.setProblem_solved(Integer.parseInt(problemSolved.text()));
            Log.i(LOG_TAG," Problems solved  - "+problemSolved.text());
        }
        catch (Exception ex)
        {
            Log.i(LOG_TAG," IN catch block of getProblemStats");
            ex.printStackTrace();
        }
    }

    //get the ratings
    private void getRating(Document doc)
    {

        try {

            Log.i(LOG_TAG," start executing getRating method");

            Elements table=doc.getElementsByClass("rating-table");
            Elements getTr=table.select("tr");

            //for long
            Element getLongTr=getTr.get(1);
            Elements getLongcols=getLongTr.select("td");
            Element getLongcolRanks=getLongcols.get(1);

            // long global Rank
            Element getLongcolRankGLobal=getLongcolRanks.select("a").get(0);
            codersModel.setLongGLobalRank(Integer.parseInt(getLongcolRankGLobal.text()));
            Log.i(LOG_TAG,"Long global "+getLongcolRankGLobal.text());

            //long Country rank
            Element getLongcolRankCountry=getLongcolRanks.select("a").get(1);
            codersModel.setLongCountryRank(Integer.parseInt(getLongcolRankCountry.text()));
            Log.i(LOG_TAG,"Long country "+getLongcolRankCountry.text());

            //long rating
            Element getLongcolRating=getLongcols.get(2);
            codersModel.setLongRating(getLongcolRating.text());
            Log.i(LOG_TAG,"Long rating "+getLongcolRating.text());

            //for short
            Element getShortTr=getTr.get(2);
            Elements getShortcols=getShortTr.select("td");
            Element getShortcolRanks=getShortcols.get(1);

            //short global rank
            Element getShortcolRankGLobal=getShortcolRanks.select("a").get(0);
            codersModel.setShortGlobalRank(Integer.parseInt(getShortcolRankGLobal.text()));
            Log.i(LOG_TAG,"Short global "+getShortcolRankGLobal.text());

            //Short local rank
            Element getShortcolRankCountry=getShortcolRanks.select("a").get(1);
            codersModel.setShortCountryRank(Integer.parseInt(getShortcolRankCountry.text()));
            Log.i(LOG_TAG,"Short local "+getShortcolRankCountry.text());

            //short rating
            Element getShortcolRating=getShortcols.get(2);
            codersModel.setShortRating(getShortcolRating.text());
            Log.i(LOG_TAG,"short rating "+getShortcolRating.text());

            //LTime details
            Element getLtimeTr=getTr.get(3);
            Elements getLtimecols=getLtimeTr.select("td");
            Element getLtimecolRanks=getLtimecols.get(1);

            //Ltime global rank
            Element getLtimecolRankGLobal=getLtimecolRanks.select("a").get(0);
            codersModel.setLunchtimeGlobalRank(Integer.parseInt(getLtimecolRankGLobal.text()));
            Log.i(LOG_TAG,"Ltime global "+getLtimecolRankGLobal.text());

            //Ltime local rank
            Element getLtimecolRankCountry=getLtimecolRanks.select("a").get(1);
            codersModel.setLunchtimeCountryRank(Integer.parseInt(getLtimecolRankCountry.text()));
            Log.i(LOG_TAG,"Ltime local "+getLtimecolRankCountry.text());

            //Ltime rating
            Element getLtimecolRating=getLtimecols.get(2);
            codersModel.setLunchtimeRating(getLtimecolRating.text());
            Log.i(LOG_TAG,"Ltime rating "+getLtimecolRating.text());

            Log.i(LOG_TAG," finished executing getRating method");
            return;
        }
        catch (Exception ex)
        {
            Log.i(LOG_TAG," IN catch block of getRating");
            ex.printStackTrace();
        }
    }

    //insert or update
    private CodechefModel insertOrUpdate(CodechefModel model)
    {

        Log.i(LOG_TAG,"In insertOrUpdate method of CodechefModel");
        CodechefDatabaseHelperFunction codechefDatabaseHelperFunction=new CodechefDatabaseHelperFunction(getContext(),mHandle,mUserId);
        codechefDatabaseHelperFunction.open();
        if(codechefDatabaseHelperFunction.userExist())
        {
            codechefDatabaseHelperFunction.updateUser(model);
        }
        else
        {
            model=codechefDatabaseHelperFunction.createNewUser(model);
        }
        codechefDatabaseHelperFunction.close();
        Log.i(LOG_TAG,"Exit insertOrUpdate method of CodechefLoader");
        return model;
    }

}
