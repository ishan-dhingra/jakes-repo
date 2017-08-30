package com.anythingintellect.jakesgit.view;


import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anythingintellect.jakesgit.JakesGitApp;
import com.anythingintellect.jakesgit.R;
import com.anythingintellect.jakesgit.adapter.GitRepoAdapter;
import com.anythingintellect.jakesgit.di.FragmentModule;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.Navigator;
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
    @Inject
    Navigator navigator;
    private GitRepoAdapter gitRepoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvList;

    public RepoListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        gitRepoAdapter = new GitRepoAdapter(viewModel.getRealRepoList(), navigator);
        viewModel.getHasMore().addOnPropertyChangedCallback(hasMoreListener);
        viewModel.getOnError().addOnPropertyChangedCallback(onErrorListener);
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
        rvList = (RecyclerView) view.findViewById(R.id.rvRepoList);
        setupRV(rvList);
    }

    private void setupRV(RecyclerView recyclerView) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(gitRepoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // Load when last third item from bottom is visible
                if (lastVisible + 3 > linearLayoutManager.getItemCount()) {
                    viewModel.loadPage();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDispose();
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

    // Taken from https://realm.io/docs/java/latest/
    private final OrderedRealmCollectionChangeListener<RealmResults<GitRepo>> changeListener =
            new OrderedRealmCollectionChangeListener<RealmResults<GitRepo>>() {
        @Override
        public void onChange(RealmResults<GitRepo> collection, OrderedCollectionChangeSet changeSet) {
            // `null`  means the async query returns the first time.
            if (changeSet == null) {
                // TODO: Fix know bug in case of first load
                gitRepoAdapter.notifyDataSetChanged();
                viewModel.loadPage();
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

    private final Observable.OnPropertyChangedCallback hasMoreListener = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            if(viewModel.getHasMore().get()) {
                gitRepoAdapter.showLoading();
            } else {
                gitRepoAdapter.hideLoading();
            }
        }
    };

    // This part can be made better, for time limitations I am keeping it simiple
    private final Observable.OnPropertyChangedCallback onErrorListener = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            showError();
        }
    };

    private void showError() {
        Toast toast = Toast.makeText(getContext(), "Unable to sync with Server!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
