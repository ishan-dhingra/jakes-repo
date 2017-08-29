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

    public GitRepoAdapter(RealmResults<GitRepo> repoList, Navigator navigator) {
        this.repoList = repoList;
        this.navigator = navigator;
        setHasStableIds(true);
    }


    @Override
    public GitRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGitRepoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_git_repo, parent, false);
        return new GitRepoViewHolder(binding, new ItemGitRepoViewModel(navigator));
    }

    @Override
    public void onBindViewHolder(GitRepoViewHolder holder, int position) {
        holder.bind(repoList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return repoList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    static class GitRepoViewHolder extends RecyclerView.ViewHolder {

        private final ItemGitRepoBinding binding;
        private final ItemGitRepoViewModel viewModel;

        GitRepoViewHolder(ItemGitRepoBinding binding, ItemGitRepoViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        void bind(GitRepo gitRepo) {
            viewModel.setGitRepo(gitRepo);
            binding.setVm(viewModel);
            binding.executePendingBindings();
        }
    }

}
