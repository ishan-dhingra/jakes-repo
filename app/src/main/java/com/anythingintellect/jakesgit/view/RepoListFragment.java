package com.anythingintellect.jakesgit.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anythingintellect.jakesgit.JakesGitApp;
import com.anythingintellect.jakesgit.R;
import com.anythingintellect.jakesgit.adapter.GitRepoAdapter;
import com.anythingintellect.jakesgit.di.FragmentModule;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.viewmodel.RepoListViewModel;

import javax.inject.Inject;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoListFragment extends Fragment {

    @Inject
    RepoListViewModel viewModel;
    GitRepoAdapter gitRepoAdapter;

    public RepoListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        gitRepoAdapter = new GitRepoAdapter(viewModel.getRealRepoList());
        viewModel.loadNext();
    }

    private void injectDependencies() {
        ((JakesGitApp)getActivity().getApplication())
                .getAppComponent()
                .plusFragmentModule(new FragmentModule(getContext()))
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvRepoList);
        setupRV(recyclerView);
    }

    private void setupRV(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(gitRepoAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getRealRepoList().addChangeListener(changeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.getRealRepoList().removeAllChangeListeners();
    }

    private final OrderedRealmCollectionChangeListener<RealmResults<GitRepo>> changeListener =
            new OrderedRealmCollectionChangeListener<RealmResults<GitRepo>>() {
        @Override
        public void onChange(RealmResults<GitRepo> collection, OrderedCollectionChangeSet changeSet) {
            // `null`  means the async query returns the first time.
            if (changeSet == null) {
                gitRepoAdapter.notifyDataSetChanged();
                return;
            }
            // For deletions, the adapter has to be notified in reverse order.
            OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
            for (int i = deletions.length - 1; i >= 0; i--) {
                OrderedCollectionChangeSet.Range range = deletions[i];
                gitRepoAdapter.notifyItemRangeRemoved(range.startIndex, range.length);
            }

            OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
            for (OrderedCollectionChangeSet.Range range : insertions) {
                gitRepoAdapter.notifyItemRangeInserted(range.startIndex, range.length);
            }

            OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
            for (OrderedCollectionChangeSet.Range range : modifications) {
                gitRepoAdapter.notifyItemRangeChanged(range.startIndex, range.length);
            }
        }
    };
}
