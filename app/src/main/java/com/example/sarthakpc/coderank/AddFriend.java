package com.example.sarthakpc.coderank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sarthakpc on 8/1/2016.
 */
public class AddFriend extends AppCompatActivity
{
    private EditText mFriendName;
    private EditText mCodechefHandle;
    private EditText mCodeforcesHandle;
    private EditText mSpojHandle;
    private EditText mHackerRankHandle;
    private EditText mHackerEarthHandle;
    private Button mSubmitAddFriend;

    private String[] mHandles;

    //constant strings
    private static final int REQUEST_CODE_HOMEACTIVITY_TO_ADDFRIEND=10;
    private static final String LOG_TAG="Testing";
    private UserModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        initializeViews();
        model=(UserModel)getIntent().getSerializableExtra("UserModel_object");
        if(model!=null)
        {
            setViews(model);
        }


        mSubmitAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandles=getAllHandles();
                Intent intent=new Intent();
                intent.putExtra("Handles",mHandles);
                setResult(Activity.RESULT_OK,intent);
                Log.i(LOG_TAG,"all handles are set to be returned");
                finish();
            }
        });
    }

    private void initializeViews()
    {
        mFriendName=(EditText)findViewById(R.id.frinedName_addfriend);
        mCodechefHandle=(EditText) findViewById(R.id.codechef_addFriend_handle);
        mCodeforcesHandle=(EditText)findViewById(R.id.codeforces_addFriend_handle);
        mSpojHandle=(EditText)findViewById(R.id.spoj_addFriend_handle);
        mHackerRankHandle=(EditText)findViewById(R.id.hackerRank_addFriend_handle);
        mHackerEarthHandle=(EditText)findViewById(R.id.hackerEarth_addFriend_handle);
        mSubmitAddFriend=(Button)findViewById(R.id.submit_addFriend);
    }

    private void setViews(UserModel model)
    {
        mFriendName.setText(model.getFriendName());
        mCodechefHandle.setText(model.getCodechefHandle());
        mCodeforcesHandle.setText(model.getCodeforcesHandle());
        mSpojHandle.setText(model.getSpojHandle());
        mHackerRankHandle.setText(model.getHackerRankHandle());
        mHackerEarthHandle.setText(model.getHackerEarthHandle());
    }


    private String[] getAllHandles()
    {
        String[] handles=new String[7];
        handles[0]=mFriendName.getText().toString();
        handles[1]=mCodechefHandle.getText().toString();
        handles[2]=mCodeforcesHandle.getText().toString();
        handles[3]=mSpojHandle.getText().toString();
        handles[4]=mHackerRankHandle.getText().toString();
        handles[5]=mHackerEarthHandle.getText().toString();

        if(model!=null)
            handles[6]=String.valueOf(model.getId());
        else
            handles[6]=String.valueOf(-1);
        return handles;
    }

}
