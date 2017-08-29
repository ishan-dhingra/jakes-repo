package com.anythingintellect.jakesgit.repo;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public interface GitDataRepository {

    // Should return local reference - Single Source Of Truth
    RealmResults<GitRepo> listGitRepo();

    // Should call API
    Observable<List<GitRepo>> fetchRepoList(int page, int pageSize);

    void saveGitRepoList(List<GitRepo> gitRepoList);

}
