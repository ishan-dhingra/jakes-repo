package com.anythingintellect.jakesgit.util;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class MockData {
    private static List<GitRepo> repoList;
    static {
        repoList = new ArrayList<>();
    }

    public static List<GitRepo> getRepoList() {
        return repoList;
    }
}
