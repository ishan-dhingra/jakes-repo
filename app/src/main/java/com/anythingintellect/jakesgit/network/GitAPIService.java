package com.anythingintellect.jakesgit.network;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

public interface GitAPIService {

    // User name and per page can made dynamic as well
    // for now just sticking to basics as per requirement
    @GET("/users/JakeWharton/repos?page={page}&per_page=15")
    public Observable<List<GitRepo>> getRepositories(int page);

}
