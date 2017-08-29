package com.anythingintellect.jakesgit.repo;

import com.anythingintellect.jakesgit.db.LocalDataStore;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.network.GitAPIService;
import com.anythingintellect.jakesgit.util.MockData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class GitDataRepositoryTest {

    private GitDataRepository gitDataRepository;
    @Mock
    GitAPIService apiService;
    @Mock
    LocalDataStore localDataStore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.gitDataRepository = new GitDataRepositoryImpl(apiService, localDataStore);
    }

    // Test lisGitRepo: Should call local store only for result
    @Test
    public void testListGitRepo_shouldCallLocalStoreOnlyForResult() {
        gitDataRepository.listGitRepo();

        verify(localDataStore, only()).listGitRepo();
        verify(apiService, never()).getRepositories(anyInt(), anyInt());
    }

    // Test fetchRepoList: Should call network API only
    @Test
    public void testFetchRepoList_shouldCallNetworkAPIOnly() {
        when(apiService.getRepositories(1, 15)).thenReturn(Observable.<List<GitRepo>>empty());

        gitDataRepository.fetchRepoList(1, 15);

        verify(apiService, only()).getRepositories(1, 15);
        verify(localDataStore, never()).listGitRepo();
    }

    // Test saveGitRepoList: Should store git repo list to localStore
    @Test
    public void testSaveRepoList_ShouldStoreGitRepoListToLocalStore() {
        List<GitRepo> dummyList = MockData.getEmptyRepoList();
        gitDataRepository.saveGitRepoList(dummyList);
        verify(localDataStore, only()).saveGitRepo(dummyList);
    }

}
