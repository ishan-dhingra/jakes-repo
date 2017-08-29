package com.anythingintellect.jakesgit.network;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.Constants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

public interface GitAPIService {

    // User name and per page can made dynamic as well
    // for now just sticking to basics as per requirement.
    // Keeping a specified order, so that local data set can be queried
    // by same order.
    @GET("/users/JakeWharton/repos?sort=id")
    Observable<List<GitRepo>> getRepositories(@Query("page") int page, @Query("per_page") int pageSize);

}
