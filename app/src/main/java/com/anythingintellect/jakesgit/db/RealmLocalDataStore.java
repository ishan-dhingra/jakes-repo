package com.anythingintellect.jakesgit.db;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public class RealmLocalDataStore implements LocalDataStore {

    // Keeping sorting same as API
    // so result update is in same order
    @Override
    public RealmResults<GitRepo> listGitRepo() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(GitRepo.class).findAllSortedAsync("id");
    }

    @Override
    public void saveGitRepo(final List<GitRepo> gitRepos) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyFromRealm(gitRepos);
            }
        });
    }
}
