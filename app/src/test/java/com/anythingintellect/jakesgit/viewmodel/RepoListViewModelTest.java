package com.anythingintellect.jakesgit.viewmodel;

import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.repo.GitDataRepository;
import com.anythingintellect.jakesgit.util.Constants;
import com.anythingintellect.jakesgit.util.MockData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ishan.dhingra on 29/08/17.
 */

public class RepoListViewModelTest {

    private RepoListViewModel viewModel;

    @Mock
    GitDataRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModel = new RepoListViewModel(repository);
    }


    // getHasMore
    // Should have hasMore true by default ~ loading is dependent on this
    @Test
    public void testHasMore_ShouldBeTrueByDefault() {
        assertEquals(true, viewModel.getHasMore().get());
    }

    // loadPage
    // Should load from APIService
    @Test
    public void testLoadPage_ShouldLoadFromAPIService() {
        givenRepoWithFetchRepoResponse(MockData.getListWithElementCount(Constants.ITEM_PER_PAGE));

        viewModel.loadPage();

        verify(repository).fetchRepoList(1, Constants.ITEM_PER_PAGE);
    }

    private Observable<List<GitRepo>> givenRepoWithFetchRepoResponse(List<GitRepo> gitRepoList) {
        Observable<List<GitRepo>> obs = Observable.just(gitRepoList);
        when(repository.fetchRepoList(1, Constants.ITEM_PER_PAGE))
                .thenReturn(obs);
        return obs;
    }

    // Should save after success response
    @Test
    public void testLoadPage_shouldSaveResponseAfterSuccess() {
        List<GitRepo> gitRepoList = MockData.getListWithElementCount(Constants.ITEM_PER_PAGE);
        givenRepoWithFetchRepoResponse(gitRepoList);
        viewModel.loadPage();
        verify(repository).saveGitRepoList(gitRepoList);
    }

    // Should onError on API error
    @Test
    public void testLoadPage_ShouldSetOnErrorWhenAPIErrorResponse() {
        when(repository.fetchRepoList(1, Constants.ITEM_PER_PAGE))
                .thenReturn(Observable.<List<GitRepo>>error(new IOException("")));

        viewModel.loadPage();

        assertEquals(true, viewModel.getOnError().get());
    }

    // Should auto increase page for next request after full successful load
    @Test
    public void testLoadPage_ShouldAutoIncreasePageCountForNextRequest() {

        // First request
        List<GitRepo> fullResponse = MockData.getListWithElementCount(Constants.ITEM_PER_PAGE);
        givenRepoWithFetchRepoResponse(fullResponse);

        viewModel.loadPage();

        verify(repository).fetchRepoList(1, Constants.ITEM_PER_PAGE);

        reset(repository);

        List<GitRepo> emptyListResponse = MockData.getEmptyRepoList();
        when(repository.fetchRepoList(2, Constants.ITEM_PER_PAGE))
                .thenReturn(Observable.just(emptyListResponse));

        viewModel.loadPage();

        verify(repository).fetchRepoList(2, Constants.ITEM_PER_PAGE);

    }

    // Should hasMore false when less than expected item received from API
    @Test
    public void testLoadPage_ShouldSetHasMoreFalseWhenItemCountIsLessThanExpected() {
        givenRepoWithFetchRepoResponse(MockData.getEmptyRepoList());

        viewModel.loadPage();

        assertEquals(false, viewModel.getHasMore().get());

    }






}
