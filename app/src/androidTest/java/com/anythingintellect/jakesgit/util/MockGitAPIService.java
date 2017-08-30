package com.anythingintellect.jakesgit.util;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.network.GitAPIService;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * Created by ishan.dhingra on 30/08/17.
 */

public class MockGitAPIService implements GitAPIService {

    @Override
    public Observable<List<GitRepo>> getRepositories(@Query("page") int page, @Query("per_page") int pageSize) {
        return Observable.just(MockData.getListWithElementCount(pageSize));
    }
}
