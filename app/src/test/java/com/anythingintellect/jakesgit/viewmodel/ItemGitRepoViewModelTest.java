package com.anythingintellect.jakesgit.viewmodel;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.MockData;
import com.anythingintellect.jakesgit.util.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class ItemGitRepoViewModelTest {

    @Mock
    private Navigator navigator;

    private ItemGitRepoViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ItemGitRepoViewModel(navigator);
    }

    // openRepo
    // Should open browser using navigator
    @Test
    public void testOpenRepo_ShouldOpenBrowser() {
        GitRepo gitRepo = MockData.getGitRepo();
        viewModel.setGitRepo(gitRepo);
        viewModel.openRepo();
        verify(navigator).openBrowser(gitRepo.getUrl());
    }

}
