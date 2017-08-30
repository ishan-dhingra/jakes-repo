package com.anythingintellect.jakesgit.util;

import com.anythingintellect.jakesgit.model.GitRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class MockData {
    private static List<GitRepo> repoList;
    private static GitRepo gitRepo;

    static {
        repoList = new ArrayList<>();
        gitRepo = new GitRepo();
        gitRepo.setName("Test Repo");
        gitRepo.setId(1100L);
        gitRepo.setDescription("Test");
        gitRepo.setLanguage("Java");
        gitRepo.setUrl("http://www.github.com");
    }


    public static List<GitRepo> getEmptyRepoList() {
        return repoList;
    }

    public static List<GitRepo> getListWithElementCount(int count) {
        List<GitRepo> list = new ArrayList<>();
        Random random = new Random(Long.MAX_VALUE);
        while (count != 0) {
            GitRepo repo = new GitRepo();
            repo.setName("Test Repo");
            repo.setId(random.nextLong());
            repo.setDescription("Test");
            repo.setLanguage("Java");
            repo.setUrl("http://www.github.com");
            list.add(repo);
            --count;
        }
        return list;
    }

    public static GitRepo getGitRepo() {
        return gitRepo;
    }
}
