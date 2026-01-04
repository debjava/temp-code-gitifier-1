package com.ddlab.rnd.git.core;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;

@Deprecated
public class GitRepoRetriever {

    public String[] getSomeRepos(String selectedGitType, UserAccount userAccount) {
        IGitPusher gitPusher = GitType.fromString(selectedGitType).getGitPusher(userAccount);
        String[] repos = gitPusher.getExistingRepos();
        return repos;
    }


}
