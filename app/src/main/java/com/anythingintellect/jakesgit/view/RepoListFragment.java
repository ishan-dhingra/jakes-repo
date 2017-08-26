package com.anythingintellect.jakesgit.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anythingintellect.jakesgit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoListFragment extends Fragment {


    public RepoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

}
