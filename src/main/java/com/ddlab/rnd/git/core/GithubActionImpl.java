package com.ddlab.rnd.git.core;

import com.ddlab.gitpusher.core.GitResponse;
import com.ddlab.gitpusher.core.IResponseParser;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.exception.GenericGitPushException;
import com.ddlab.gitpusher.github.bean.GitHubRepo;
import com.ddlab.gitpusher.github.bean.Repo;
import com.ddlab.gitpusher.github.core.GitHubResponseParserImpl;
import com.ddlab.gitpusher.util.HTTPUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;

import java.util.ArrayList;
import java.util.List;

import static com.ddlab.gitpusher.util.CommonConstants.*;
import static com.ddlab.gitpusher.util.CommonConstants.GENERIC_LOGIN_ERR_MSG;
import static com.ddlab.gitpusher.util.CommonConstants.HTTP_401;

@Deprecated
@Slf4j
@Data @AllArgsConstructor @NoArgsConstructor
public class GithubActionImpl implements IGitActionable {

    /** The user account. */
    private UserAccount userAccount;

//    @Override
//    public String[] getExistingRepos() throws GenericGitPushException {
//        String[] existingRepos = null;
//        try {
//            existingRepos = gitHubHandler.getAllRepositories();
//        } catch (Exception e) {
//            throw new GenericGitPushException(e.getMessage());
//        }
//
//        return existingRepos;
//    }

//    @Override
    public String[] getExistingRepos() {
        GitHubRepo gitRepo = null;
        String uri = GIT_API_URI + REPO_API;
        HttpGet httpGet = new HttpGet(uri);
        String encodedUser = HTTPUtil.getEncodedUser(userAccount.getUserName(), userAccount.getPassword());
        httpGet.setHeader("Authorization", "Basic " + encodedUser);
        try {
            GitResponse gitResponse = HTTPUtil.getHttpGetOrPostResponse(httpGet);
            gitRepo = getAllGitHubRepos(gitResponse);
        } catch (RuntimeException e) {
            throw e;
        }
        Repo[] repos = gitRepo.getRepos();
        List<String> repoList = new ArrayList<String>();
        for (Repo repo : repos)
            repoList.add(repo.getName());
        return repoList.toArray(new String[0]);
    }

    // Private Methods
    private GitHubRepo getAllGitHubRepos(GitResponse gitResponse) {
        GitHubRepo gitRepo = null;
        if (gitResponse.getStatusCode().equals(HTTP_200)) {
            IResponseParser<String, GitHubRepo> responseParser = new GitHubResponseParserImpl();
            gitRepo = responseParser.getAllRepos(gitResponse.getResponseText());
        } else if (gitResponse.getStatusCode().equals(HTTP_401)) {
            throw new GenericGitPushException(GENERIC_LOGIN_ERR_MSG);
        }
        return gitRepo;
    }
}
