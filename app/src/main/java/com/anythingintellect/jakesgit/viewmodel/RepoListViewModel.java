package com.anythingintellect.jakesgit.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.repo.GitDataRepository;
import com.anythingintellect.jakesgit.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public class RepoListViewModel {

    @Inject
    GitDataRepository repository;
    private RealmResults<GitRepo> realRepoList;
    private int page;
    private ObservableField<Boolean> hasMore;
    private ObservableField<Boolean> onError;
    private Disposable apiDisposable;


    @Inject
    public RepoListViewModel(GitDataRepository repository) {
        this.repository = repository;
        this.realRepoList = repository.listGitRepo();
        this.page = 1;
        this.hasMore = new ObservableField<>(true);
        this.onError = new ObservableField<>(false);
    }

    public RealmResults<GitRepo> getRealRepoList() {
        return realRepoList;
    }

    public void loadNext() {
        if (apiDisposable == null && hasMore.get()) {
            onError.set(false);
            repository
                    .fetchRepoList(page)
                    .subscribe(new Observer<List<GitRepo>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    apiDisposable = d;
                }

                @Override
                public void onNext(@NonNull List<GitRepo> gitRepoList) {
                    repository.saveGitRepoList(gitRepoList);
                    ++page;
                    if (gitRepoList.size() < Constants.ITEM_PER_PAGE) {
                        hasMore.set(false);
                    }
                    Log.d("repo", "item fetched: " + gitRepoList.size());
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    apiDisposable = null;
                    onError.set(true);
                }

                @Override
                public void onComplete() {
                    apiDisposable = null;
                }
            });
        }
    }

    public void onDispose() {
        if (apiDisposable != null) {
            apiDisposable.dispose();;
        }
    }


}
