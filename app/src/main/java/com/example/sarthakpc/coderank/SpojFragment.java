package com.example.sarthakpc.coderank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sarthakpc on 8/5/2016.
 */
public class SpojFragment extends Fragment
{
    private String mSpojHandle;
    private long mUserId;
    private TextView mSpojWorldRank;
    private TextView mSpojProblemsSolved;
    private TextView mSpojSolutionsSubmitted;
    private UserModel model;

    private ProgressDialog mProgressDialog;

    private static final String LOG_TAG="Testing";
    private static final int SPOJ_LOADER_ID=1;
    private static final int SPOJ_DATABASE_LOADER_ID=2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_spoj,container,false);
        initializeViews(v);
        getUserData();

        initializeSpojDatabaseLoader();
//        SpojAsyncTask asynTack=new SpojAsyncTask();
//        asynTack.execute(model);

        return v;
    }

    private void initializeSpojDatabaseLoader()
    {
        getLoaderManager().restartLoader(SPOJ_DATABASE_LOADER_ID,null,fetchSpojDatabaseData);
    }

    private void restartSpojDatabaseLoader()
    {
        getLoaderManager().restartLoader(SPOJ_DATABASE_LOADER_ID,null,fetchSpojDatabaseData);
    }

    public void initializeSpojFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(SPOJ_LOADER_ID,null,fetchSpojData);
    }

    private void restartSpojFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(SPOJ_LOADER_ID,null,fetchSpojData);
    }

    private void initializeViews(View v)
    {
        mSpojWorldRank=(TextView)v.findViewById(R.id.spoj_world_rank);
        mSpojProblemsSolved=(TextView)v.findViewById(R.id.spoj_problems_solved);
        mSpojSolutionsSubmitted=(TextView)v.findViewById(R.id.spoj_solutions_submitted);
    }

    private void getUserData()
    {
        Intent intent=getActivity().getIntent();
        model=(UserModel)intent.getSerializableExtra("UserModel_object");
        mSpojHandle=model.getSpojHandle();
        mUserId=model.getId();
        Log.i(LOG_TAG,"Spoj model handle in getUserData -"+mSpojHandle);
    }

    private void updateUI(SpojModel model)
    {
        mSpojWorldRank.setText(model.getWorldRank());
        mSpojProblemsSolved.setText(String.valueOf(model.getProblemsSolved()));
        mSpojSolutionsSubmitted.setText(String.valueOf(model.getSolutionsSubmitted()));
    }

//    public void reload()
//    {
//        new SpojAsyncTask().execute(model);
//    }

    //Loader Manager to fetch data online
    private LoaderManager.LoaderCallbacks<SpojModel> fetchSpojData=new LoaderManager.LoaderCallbacks<SpojModel>()
    {
        @Override
        public Loader<SpojModel> onCreateLoader(int id, Bundle args)
        {
            //code for the progress dialogue box
            mProgressDialog=ProgressDialog.show(getContext(),"Fetching Data","Please wait",true);
            return new SpojLoader(getContext(),mSpojHandle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<SpojModel> loader, SpojModel model)
        {
//            View loading_indicator=getView().findViewById(R.id.loading_indicator_spoj);
//            loading_indicator.setVisibility(View.GONE);
            mProgressDialog.dismiss();
            if (model!=null)
                initializeSpojDatabaseLoader();
        }

        @Override
        public void onLoaderReset(Loader<SpojModel> loader)
        {
            restartSpojFetchFromNetLoader();
        }
    };

    //Loader manager to store data
    private LoaderManager.LoaderCallbacks<SpojModel> fetchSpojDatabaseData=new LoaderManager.LoaderCallbacks<SpojModel>()
    {

        @Override
        public Loader<SpojModel> onCreateLoader(int id, Bundle args)
        {
            return new SpojDatabaseLoader(getContext(),mSpojHandle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<SpojModel> loader, SpojModel data)
        {
            View loading_indicator=getView().findViewById(R.id.loading_indicator_spoj);
            loading_indicator.setVisibility(View.GONE);

            updateUI(data);
            Log.i(LOG_TAG,"IN onLoadFInished of fetchSpojDatabaseData");
        }

        @Override
        public void onLoaderReset(Loader<SpojModel> loader)
        {
            restartSpojDatabaseLoader();
        }
    };

//    AsyncTask commented out
//    private class SpojAsyncTask extends AsyncTask<UserModel,Void,SpojModel>
//    {
//
//        @Override
//        protected SpojModel doInBackground(UserModel... userModels)
//        {
//            if (userModels[0]==null)
//                return null;
//
//            ExtractSpojData data=new ExtractSpojData(mSpojHandle);
//            SpojModel model=data.extractData();
//            return model;
//        }
//
//        @Override
//        protected void onPostExecute(SpojModel model)
//        {
//            View loading_indicator=getView().findViewById(R.id.loading_indicator_spoj);
//            loading_indicator.setVisibility(View.GONE);
//
//            if (model!=null)
//                updateUI(model);
//        }
//    }


}
