package com.anythingintellect.jakesgit.repo;

import com.anythingintellect.jakesgit.db.LocalDataStore;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.network.GitAPIService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public class GitDataRepositoryImpl implements GitDataRepository {

    private final GitAPIService gitAPIService;
    private final LocalDataStore localDataStore;

    public GitDataRepositoryImpl(GitAPIService gitAPIService, LocalDataStore localDataStore) {
        this.gitAPIService = gitAPIService;
        this.localDataStore = localDataStore;
    }

    @Override
    public RealmResults<GitRepo> listGitRepo() {
        return localDataStore.listGitRepo();
    }

    @Override
    public Observable<List<GitRepo>> fetchRepoList(int page) {
        return gitAPIService.getRepositories(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveGitRepoList(List<GitRepo> gitRepoList) {
        localDataStore.saveGitRepo(gitRepoList);
    }
}
