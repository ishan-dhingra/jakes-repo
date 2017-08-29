package com.anythingintellect.jakesgit.viewmodel;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.Navigator;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public class ItemGitRepoViewModel {

    private GitRepo gitRepo;
    private final Navigator navigator;

    public ItemGitRepoViewModel(Navigator navigator) {
        this.navigator = navigator;
    }

    public GitRepo getGitRepo() {
        return gitRepo;
    }

    public void setGitRepo(GitRepo gitRepo) {
        this.gitRepo = gitRepo;
    }

    public void openRepo() {
        navigator.openBrowser(gitRepo.getUrl());
    }
}
