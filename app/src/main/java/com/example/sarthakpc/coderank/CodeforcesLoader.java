package com.example.sarthakpc.coderank;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Sarthakpc on 8/6/2016.
 */
public class CodeforcesLoader extends AsyncTaskLoader<CodeforcesModel>
{
    private long mUserId;
    private String mHandle=null;
    private static final String LOG_TAG="Testing";
    private CodeforcesModel codersModel;

    private static final String CODEFORCES_USER_INFO="http://codeforces.com/api/user.info?handles=";
    private static final String CODEFORCES_USER_RATING="http://codeforces.com/api/user.rating?handle=";

    public CodeforcesLoader(Context context, String username, long userId)
    {
        super(context);
        mHandle=username;
        mUserId=userId;
        codersModel=new CodeforcesModel();
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public CodeforcesModel loadInBackground()
    {
        Log.i(LOG_TAG,"SpojLoader running for handle  "+mHandle);
        CodeforcesModel newModel=extractData();
        if (newModel.getCurrentRating()>0)
            newModel=insertOrUpdate(newModel);
        return newModel;
    }

    public CodeforcesModel extractData()
    {
        if(mHandle==null)
            return null;

        getUserInfo();
        getUserRating();
        return codersModel;
    }

    private void getUserInfo()
    {
        URL url=createUrl(CODEFORCES_USER_INFO+mHandle);
        String jsonResponse;
        if(url==null)
            return;
        try
        {
            jsonResponse=makeHttpRequest(url);
            Log.i(LOG_TAG,"Json response for getUser info - "+jsonResponse);
            extractUserInfoJson(jsonResponse);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG,"ExtractCodeforcesData: getUserInfo catch block - could not getUserInfo");
            e.printStackTrace();
        }
    }

    private void getUserRating()
    {
        URL url=createUrl(CODEFORCES_USER_RATING+mHandle);
        String jsonResponse;
        if(url==null)
            return;
        try
        {
            jsonResponse=makeHttpRequest(url);
            extractUserRating(jsonResponse);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG,"ExtractCodeforcesData: getUserRating catch block - could not getUserRating");
            e.printStackTrace();
        }
    }

    private URL createUrl(String stringUrl)
    {
        URL url=null;
        try
        {
            url=new URL(stringUrl);
        }
        catch (MalformedURLException exception)
        {
            Log.e(LOG_TAG,"Error creating url");
            exception.printStackTrace();
        }
        return url;
    }

    private String makeHttpRequest(URL url)throws Exception
    {
        String jsonResponse=null;

        if (url==null)
            return null;

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try
        {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromInputStream(inputStream);
            }
        }
        catch (IOException e)
        {
            Log.i(LOG_TAG,"Could not do the connection");
        }
        finally
        {
            if (urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromInputStream(InputStream inputStream)
    {
        StringBuilder builder=new StringBuilder();
        try
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null)
            {
                builder.append(line);
                line=reader.readLine();
            }
        }
        catch (IOException e)
        {
            Log.i(LOG_TAG,"Could not read from the input stream");
            e.printStackTrace();
        }
        String jsonResponse=builder.toString();
        return jsonResponse;
    }

    private void extractUserInfoJson(String jsonResponse)
    {
        if (TextUtils.isEmpty(jsonResponse))
            return;

        try
        {
            JSONObject baseJsonResponse=new JSONObject(jsonResponse);
            JSONArray resultArray=baseJsonResponse.getJSONArray("result");

            JSONObject resultArrayFirstObject=resultArray.getJSONObject(0);
            int currentRating=resultArrayFirstObject.getInt("rating");
            Log.i(LOG_TAG,"ExtractCodeforcesData: Current rating- "+currentRating);

            int maxRating=resultArrayFirstObject.getInt("maxRating");
            Log.i(LOG_TAG,"ExtractCodeforcesData: Max Rating- "+maxRating);

            String rank=resultArrayFirstObject.getString("rank");
            Log.i(LOG_TAG,"ExtractCodeforcesData: Rank-  "+rank);

            String maxRank=resultArrayFirstObject.getString("maxRank");
            Log.i(LOG_TAG,"ExtractCodeforcesData: Max rank-  "+maxRank);

            codersModel.setCurrentRating(currentRating);
            codersModel.setMaxRating(maxRating);
            codersModel.setRank(rank);
            codersModel.setMaxRank(maxRank);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG,"ExtractCodeforcesData: JSON could not fetch the user info");
            e.printStackTrace();
        }
    }

    private void extractUserRating(String jsonResponse)
    {
        if (TextUtils.isEmpty(jsonResponse))
            return;

        try
        {
            JSONObject baseJsonResponse=new JSONObject(jsonResponse);
            JSONArray resultArray=baseJsonResponse.getJSONArray("result");

            JSONObject resultArrayLastObject=resultArray.getJSONObject(resultArray.length()-1);

            String lastContest=resultArrayLastObject.getString("contestName");
            codersModel.setRecentCompGiven(lastContest);
            Log.i(LOG_TAG,"ExtractCodeforcesData: recent Competition-   "+lastContest);

            int oldRating=resultArrayLastObject.getInt("oldRating");
            codersModel.setOldRating(oldRating);
            Log.i(LOG_TAG,"ExtractCodeforcesData: Old rating  "+oldRating);

            int ratingChange=resultArrayLastObject.getInt("newRating")-resultArrayLastObject.getInt("oldRating");
            codersModel.setRecentRatingChange(ratingChange);
            Log.i(LOG_TAG,"ExtractCodeforcesData: rating change:-  "+ratingChange);
        }
        catch (Exception e)
        {
            Log.i(LOG_TAG,"ExtractCodeforcesData: could not fetch the user info");
            e.printStackTrace();
        }
    }

    private CodeforcesModel insertOrUpdate(CodeforcesModel model)
    {
        Log.i(LOG_TAG,"In insertOrUpdate method of CodeforcesLoader");
        CodeforcesDatabaseHelperFunction codeforcesDatabaseHelperFunction=new CodeforcesDatabaseHelperFunction(getContext(),mHandle,mUserId);
        codeforcesDatabaseHelperFunction.open();
        if(codeforcesDatabaseHelperFunction.userExist())
        {
            codeforcesDatabaseHelperFunction.updateUser(model);
        }
        else
        {
            model=codeforcesDatabaseHelperFunction.createNewUser(model);
        }
        codeforcesDatabaseHelperFunction.close();
        Log.i(LOG_TAG,"Exit insertOrUpdate method of Codeforces");
        return model;
    }
}
