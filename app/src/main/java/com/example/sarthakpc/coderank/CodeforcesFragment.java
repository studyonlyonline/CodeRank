package com.example.sarthakpc.coderank;

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
public class CodeforcesFragment extends Fragment
{
    private TextView mOldRating_textView;
    private TextView mCurrentRating_textView;
    private TextView mMaxRating_textView;
    private TextView mRecentRatingChange_textView;
    private TextView mRecentComp_textView;
    private TextView mRank_textView;
    private TextView mMaxRank_textView;

    private UserModel model;
    private String mCodeforces_handle;
    private long mUserId;
    private static final int CODEFORCES_LOADER_ID=3;
    private static final int CODEFORCES_DATABASE_LOADER_ID=4;

    private static final String LOG_TAG="Testing";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_codeforces,container,false);
        initializeViews(v);
        getUserData();
        initializeCodeforcesDatabaseLoader();
        return v;
    }

    private void initializeViews(View v)
    {
        mOldRating_textView=(TextView)v.findViewById(R.id.old_rating_codeforces);
        mCurrentRating_textView=(TextView)v.findViewById(R.id.current_rating_codeforces);
        mMaxRating_textView=(TextView)v.findViewById(R.id.max_rating_codeforces);
        mRecentRatingChange_textView=(TextView)v.findViewById(R.id.recent_rating_change_codeforces);
        mRecentComp_textView=(TextView)v.findViewById(R.id.recent_comp_given_codeforces);
        mRank_textView=(TextView)v.findViewById(R.id.rank_codeforces);
        mMaxRank_textView=(TextView)v.findViewById(R.id.max_rank_codeforces);
    }

    private void initializeCodeforcesDatabaseLoader()
    {
        getLoaderManager().restartLoader(CODEFORCES_DATABASE_LOADER_ID,null,fetchCodeforcesDatabaseData);
    }

    private void restartCodeforcesDatabaseLoader()
    {
        getLoaderManager().restartLoader(CODEFORCES_DATABASE_LOADER_ID,null,fetchCodeforcesDatabaseData);
    }

    public void initializeCodeforcesFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(CODEFORCES_LOADER_ID,null,fetchCodeforcesData);
    }

    private void restartCodeforcesFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(CODEFORCES_LOADER_ID,null,fetchCodeforcesData);
    }

    private void getUserData()
    {
        Intent intent=getActivity().getIntent();
        model=(UserModel)intent.getSerializableExtra("UserModel_object");
        mCodeforces_handle=model.getCodeforcesHandle();
        mUserId=model.getId();
        Log.i(LOG_TAG,"Codeforces model handle -"+mCodeforces_handle);
    }

    private void updateUI(CodeforcesModel model)
    {
        mOldRating_textView.setText(String.valueOf(model.getOldRating()));
        mCurrentRating_textView.setText(String.valueOf(model.getCurrentRating()));
        mMaxRating_textView.setText(String.valueOf(model.getMaxRating()));
        mRecentRatingChange_textView.setText(String.valueOf(model.getRecentRatingChange()));
        mRecentComp_textView.setText(model.getRecentCompGiven());
        mRank_textView.setText(model.getRank());
        mMaxRank_textView.setText(model.getMaxRank());
    }

    private LoaderManager.LoaderCallbacks<CodeforcesModel> fetchCodeforcesData=new LoaderManager.LoaderCallbacks<CodeforcesModel>() {
        @Override
        public Loader<CodeforcesModel> onCreateLoader(int id, Bundle args) {
            return new CodeforcesLoader(getContext(),mCodeforces_handle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<CodeforcesModel> loader, CodeforcesModel data) {
            if (model!=null)
                initializeCodeforcesDatabaseLoader();
        }

        @Override
        public void onLoaderReset(Loader<CodeforcesModel> loader) {
            restartCodeforcesFetchFromNetLoader();
        }
    };

    private LoaderManager.LoaderCallbacks<CodeforcesModel> fetchCodeforcesDatabaseData=new LoaderManager.LoaderCallbacks<CodeforcesModel>() {
        @Override
        public Loader<CodeforcesModel> onCreateLoader(int id, Bundle args)
        {

            return new CodeforcesDatabaseLoader(getContext(),mCodeforces_handle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<CodeforcesModel> loader, CodeforcesModel data)
        {
            View loading_indicator=getView().findViewById(R.id.loading_indicator_codeforces);
            loading_indicator.setVisibility(View.GONE);

            updateUI(data);
            Log.i(LOG_TAG,"IN onLoadFInished of fetchCodeforcesDatabaseData");
        }

        @Override
        public void onLoaderReset(Loader<CodeforcesModel> loader) {
            restartCodeforcesDatabaseLoader();
        }
    };


//    private class CodeforcesAsyncTask extends AsyncTask<UserModel,Void,CodeforcesModel>
//    {
//
//        @Override
//        protected CodeforcesModel doInBackground(UserModel... userModels)
//        {
//            if(userModels==null)
//                return null;
//
//            ExtractCodeforcesData data=new ExtractCodeforcesData(mCodeforces_handle);
//            CodeforcesModel model=data.extractData();
//            return model;
//        }
//
//        @Override
//        protected void onPostExecute(CodeforcesModel model)
//        {
//            View loading_indicator=getView().findViewById(R.id.loading_indicator_codeforces);
//            loading_indicator.setVisibility(View.GONE);
//
//            if(model!=null)
//            {
//                updateUI(model);
//            }
//        }
//    }

}
