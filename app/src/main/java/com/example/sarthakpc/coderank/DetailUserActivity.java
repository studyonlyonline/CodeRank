package com.example.sarthakpc.coderank;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Sarthakpc on 8/2/2016.
 */
public class DetailUserActivity extends AppCompatActivity
{
    private static final String LOG_TAG="Testing";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState==null)
        {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.codechef_fragment_container,new CodechefFragment());
            transaction.add(R.id.codeforces_fragment_container,new CodeforcesFragment());
            transaction.add(R.id.spoj_fragment_container,new SpojFragment());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_refresh:

                CodechefFragment codechefFragment=(CodechefFragment)getSupportFragmentManager().findFragmentById(R.id.codechef_fragment_container);
                codechefFragment.initializeCodechefFetchFromNetLoader();

                CodeforcesFragment codeforcesFragment=(CodeforcesFragment)getSupportFragmentManager().findFragmentById(R.id.codeforces_fragment_container);
                codeforcesFragment.initializeCodeforcesFetchFromNetLoader();

                SpojFragment spojFragment=(SpojFragment)getSupportFragmentManager().findFragmentById(R.id.spoj_fragment_container);
                spojFragment.initializeSpojFetchFromNetLoader();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
