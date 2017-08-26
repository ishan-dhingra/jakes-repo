package com.anythingintellect.jakesgit.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anythingintellect.jakesgit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            RepoListFragment repoListFragment = new RepoListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, repoListFragment).commit();
        }
    }
}
