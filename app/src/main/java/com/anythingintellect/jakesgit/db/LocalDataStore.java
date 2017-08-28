package com.anythingintellect.jakesgit.db;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public interface LocalDataStore {

    RealmResults<GitRepo> listGitRepo();
    void saveGitRepo(List<GitRepo> gitRepos);

}
