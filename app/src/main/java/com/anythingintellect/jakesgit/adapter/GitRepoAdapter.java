package com.anythingintellect.jakesgit.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anythingintellect.jakesgit.R;
import com.anythingintellect.jakesgit.databinding.ItemGitRepoBinding;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.Navigator;
import com.anythingintellect.jakesgit.viewmodel.ItemGitRepoViewModel;

import io.realm.RealmResults;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

public class GitRepoAdapter extends RecyclerView.Adapter<GitRepoAdapter.GitRepoViewHolder> {

    private RealmResults<GitRepo> repoList;
    private final Navigator navigator;
    private boolean showLoading = true;

    public GitRepoAdapter(RealmResults<GitRepo> repoList, Navigator navigator) {
        this.repoList = repoList;
        this.navigator = navigator;
        setHasStableIds(true);
    }


    @Override
    public GitRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_git_repo) {
            ItemGitRepoBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_git_repo, parent, false);
            return new GitRepoViewHolder(binding, new ItemGitRepoViewModel(navigator));
        } else {
            View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new GitRepoViewHolder(loadingView);
        }
    }

    @Override
    public void onBindViewHolder(GitRepoViewHolder holder, int position) {
        if (repoList.size() != position) {
            holder.bind(repoList.get(position));
        }
    }

    @Override
    public long getItemId(int position) {
        if (position != repoList.size()) {
            return repoList.get(position).getId();
        }
        return 0L;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != repoList.size()) {
            return R.layout.item_git_repo;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (!showLoading) {
            return repoList.size();
        }
        return repoList.size() + 1;
    }

    public void showLoading() {
        this.showLoading = true;
        notifyItemInserted(repoList.size());
    }

    public void hideLoading() {
        this.showLoading = false;
        notifyItemRemoved(repoList.size());
    }

    static class GitRepoViewHolder extends RecyclerView.ViewHolder {

        private final ItemGitRepoBinding binding;
        private final ItemGitRepoViewModel viewModel;

        GitRepoViewHolder(View staticView) {
            super(staticView);
            this.binding = null;
            this.viewModel = null;
        }
        GitRepoViewHolder(ItemGitRepoBinding binding, ItemGitRepoViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        void bind(GitRepo gitRepo) {
            if (binding != null) {
                viewModel.setGitRepo(gitRepo);
                binding.setVm(viewModel);
                binding.executePendingBindings();
            }
        }
    }

}
