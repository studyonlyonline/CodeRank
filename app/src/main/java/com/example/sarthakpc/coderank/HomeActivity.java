package com.example.sarthakpc.coderank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sarthakpc on 8/1/2016.
 */
public class HomeActivity extends AppCompatActivity
{
    //Views
    private Button mAddFriend_button;
    private ListView mListView;
    private ArrayList<UserModel> mHandlesArrayList=new ArrayList<>();
    private ArrayAdapter<UserModel> mAdapter;

    //constant strings
    private static final int REQUEST_CODE_HOMEACTIVITY_TO_ADDFRIEND=10;
    private static final String LOG_TAG="Testing";

    //class objects
    UserDatabaseHelperFunction mHelperFunction=new UserDatabaseHelperFunction(this);;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeViews();

        mAddFriend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext() , AddFriend.class);
                UserModel model=null;
                intent.putExtra("UserModel_object",model);
                startActivityForResult(intent,REQUEST_CODE_HOMEACTIVITY_TO_ADDFRIEND);
//                Log.i(LOG_TAG,"add friend activity called");
            }
        });

        //registering fot the contextual menu
        registerForContextMenu(mListView);

        //implementing the listView on click listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                UserModel model=(UserModel)adapterView.getAdapter().getItem(position);
                Intent intent=new Intent(getApplicationContext(),DetailUserActivity.class);
                intent.putExtra("UserModel_object",model);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        getMenuInflater().inflate(R.menu.homeactivity_userslist_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position=info.position;
        UserModel model=mAdapter.getItem(position);

        switch (item.getItemId())
        {
            case R.id.update_user_listItem:
                Log.i(LOG_TAG,"Update user list item id "+model.getId());
                Intent intent=new Intent(getApplicationContext(),AddFriend.class);
                intent.putExtra("UserModel_object",model);
                startActivityForResult(intent,REQUEST_CODE_HOMEACTIVITY_TO_ADDFRIEND);
                return true;

            case R.id.delete_user_listItem:
                deleteItem(model);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    //class to initialize the views
    private void initializeViews()
    {
        mAddFriend_button=(Button)findViewById(R.id.addFriend_button);
        mListView=(ListView)findViewById(R.id.handles_listView_home);

    }

    //UI update code
    private void updateUI()
    {
//        Log.i(LOG_TAG,"Home Activity: updateUI() called");
        mAdapter=new ArrayAdapter<UserModel>(this,android.R.layout.simple_list_item_1,mHandlesArrayList);
        mListView.setAdapter(mAdapter);
//        Log.i(LOG_TAG,"Home Activity: updateUI() finished");
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        //Log.i(LOG_TAG,"on activity REsult Called RequestCode :-"+requestCode+" resultCode- "+resultCode);

        if(requestCode==REQUEST_CODE_HOMEACTIVITY_TO_ADDFRIEND)
        {
            //Log.i(LOG_TAG,"In first if");
            if(resultCode== Activity.RESULT_OK)
            {
//                Log.i(LOG_TAG,"In second if");
                String[] handles=data.getStringArrayExtra("Handles");
                UserModel model=new UserModel();
                model.setFriendName(handles[0]);
                model.setCodechefHandle(handles[1]);
                model.setCodeforcesHandle(handles[2]);
                model.setSpojHandle(handles[3]);
                Log.i(LOG_TAG,"checking name  while creating user" +model.getFriendName());
                model.setHackerRankHandle(handles[4]);
                model.setHackerEarthHandle(handles[5]);
                model.setId(Long.parseLong(handles[6]));
                create(model);
            }
        }
//        Log.i(LOG_TAG,"in on activity result Finished");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        Log.i(LOG_TAG,"HomeActivity: onResume called");
        read();
    }

    //add the user
    private void create(UserModel model)
    {
        mHelperFunction.open();
        mHelperFunction.createNewUser(model);
        mHelperFunction.close();
    }

    //get All users
    private void read()
    {
        mHelperFunction.open();
        mHandlesArrayList.clear();
        mHandlesArrayList=mHelperFunction.retrieveAllUsers();
        updateUI();
        mHelperFunction.close();
    }

    //delete Item
    private void deleteItem(UserModel model)
    {
        mHelperFunction.open();
        mHelperFunction.deleteUser(model);
        mAdapter.remove(model);
        mAdapter.notifyDataSetChanged();
        mHelperFunction.close();
    }

    //update Item
    private void updateItem(UserModel model)
    {
        mHelperFunction.open();
        mHelperFunction.updateUser(model);
        read();
        mHelperFunction.close();
    }


}
