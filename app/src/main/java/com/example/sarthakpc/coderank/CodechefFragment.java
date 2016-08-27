package com.example.sarthakpc.coderank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sarthakpc on 8/2/2016.
 */
public class CodechefFragment extends Fragment
{
    private TextView codechef_details_textView;
    private TextView longGlobal_textView;
    private TextView longCountry_textView;
    private TextView longRating_textView;
    private TextView shortGlobal_textView;
    private TextView shortCountry_textView;
    private TextView shortRating_textView;
    private TextView ltimeGlobal_textView;
    private TextView ltimeCountry_textView;
    private TextView ltimeRating_textView;


    private String mHandle;
    private long mUserId;
    private UserModel model;

    private static final int CODECHEF_LOADER_ID=5;
    private static final int CODECHEF_DATABASE_LOADER_ID=6;

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
        View v=inflater.inflate(R.layout.fragment_codechef,container,false);
        initializeViews(v);
        getUserData();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mHandle);

        initializeCodechefDatabaseLoader();
        return v;
    }

    public void getUserData()
    {
        Intent intent=getActivity().getIntent();
        model=(UserModel)intent.getSerializableExtra("UserModel_object");
        mHandle=model.getCodechefHandle();
        mUserId=model.getId();
        Log.i(LOG_TAG,"COdechef model handle -"+mHandle);
    }

    private void initializeCodechefDatabaseLoader()
    {
        getLoaderManager().restartLoader(CODECHEF_DATABASE_LOADER_ID,null,fetchCodechefDatabaseData);
    }

    private void restartCodechefDatabaseLoader()
    {
        getLoaderManager().restartLoader(CODECHEF_DATABASE_LOADER_ID,null,fetchCodechefDatabaseData);
    }

    public void initializeCodechefFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(CODECHEF_LOADER_ID,null,fetchCodechefData);
    }

    private void restartCodechefFetchFromNetLoader()
    {
        getLoaderManager().restartLoader(CODECHEF_LOADER_ID,null,fetchCodechefData);
    }

    private void initializeViews(View v)
    {
        codechef_details_textView=(TextView)v.findViewById(R.id.codechef_fragment_details);
        longGlobal_textView=(TextView)v.findViewById(R.id.longGlobal_textView);
        longCountry_textView=(TextView)v.findViewById(R.id.longCountry_textView);
        longRating_textView=(TextView)v.findViewById(R.id.longRating_textView);
        shortGlobal_textView=(TextView)v.findViewById(R.id.shortGlobal_textView);
        shortCountry_textView=(TextView)v.findViewById(R.id.shortCountry_textView);
        shortRating_textView=(TextView)v.findViewById(R.id.shortRating_textView);
        ltimeGlobal_textView=(TextView)v.findViewById(R.id.ltimeGlobal_textView);
        ltimeCountry_textView=(TextView)v.findViewById(R.id.ltimeCountry_textView);
        ltimeRating_textView=(TextView)v.findViewById(R.id.ltimeRating_textView);
    }

    private void updateUI(CodechefModel model)
    {
        longGlobal_textView.setText(String.valueOf(model.getLongGLobalRank()));
        longCountry_textView.setText(String.valueOf(model.getLongCountryRank()));
        longRating_textView.setText(model.getLongRating());
        shortGlobal_textView.setText(String.valueOf(model.getShortGlobalRank()));
        shortCountry_textView.setText(String.valueOf(model.getShortCountryRank()));
        shortRating_textView.setText(model.getShortRating());
        ltimeGlobal_textView.setText(String.valueOf(model.getLunchtimeGlobalRank()));
        ltimeCountry_textView.setText(String.valueOf(model.getLunchtimeCountryRank()));
        ltimeRating_textView.setText(model.getLunchtimeRating());
    }



    private LoaderManager.LoaderCallbacks<CodechefModel> fetchCodechefData=new LoaderManager.LoaderCallbacks<CodechefModel>() {
        @Override
        public Loader<CodechefModel> onCreateLoader(int id, Bundle args)
        {
            return new CodechefLoader(getContext(),mHandle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<CodechefModel> loader, CodechefModel model)
        {
            if (model!=null)
                initializeCodechefDatabaseLoader();
        }

        @Override
        public void onLoaderReset(Loader<CodechefModel> loader) {
            restartCodechefFetchFromNetLoader();
        }
    };

    private LoaderManager.LoaderCallbacks<CodechefModel> fetchCodechefDatabaseData=new LoaderManager.LoaderCallbacks<CodechefModel>() {
        @Override
        public Loader<CodechefModel> onCreateLoader(int id, Bundle args) {
            return new CodechefDatabaseLoader(getContext(),mHandle,mUserId);
        }

        @Override
        public void onLoadFinished(Loader<CodechefModel> loader, CodechefModel data)
        {
            View loading_indicator=getView().findViewById(R.id.loading_indicator_codechef);
            loading_indicator.setVisibility(View.GONE);

            updateUI(data);
            Log.i(LOG_TAG,"IN onLoadFInished of fetchCodechefDatabaseData");
        }

        @Override
        public void onLoaderReset(Loader<CodechefModel> loader)
        {
            restartCodechefDatabaseLoader();
        }
    };

//    private class CodeChefAsyncTask extends AsyncTask<UserModel,Void,CodechefModel>
//    {
//
//        @Override
//        protected CodechefModel doInBackground(UserModel... userModels)
//        {
//            Log.i(LOG_TAG," Running CodechefFragment doInBackground() ");
//            ExtractCodechefData extract=new ExtractCodechefData(codechef_handle);
//            CodechefModel codechefModel=extract.extractData();
//            Log.i(LOG_TAG," FInished Running CodechefFragment doInBackground() ");
//            return codechefModel;
//        }
//
//        @Override
//        protected void onPostExecute(CodechefModel model)
//        {
//            //hide the loading indicator when the data is loaded
//            View loadingIndicator=(View)getView().findViewById(R.id.loading_indicator_codechef);
//            loadingIndicator.setVisibility(View.GONE);
//            Log.i(LOG_TAG,"fragment- in onPostExecute");
//            if(model!=null)
//                updateUI(model);
//        }
//    }
}
