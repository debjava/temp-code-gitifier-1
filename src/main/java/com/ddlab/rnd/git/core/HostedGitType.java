package com.ddlab.rnd.git.core;

import com.ddlab.gitpusher.core.GitType;
import com.ddlab.gitpusher.core.IGitHandler;
import com.ddlab.gitpusher.core.IGitPusher;
import com.ddlab.gitpusher.core.UserAccount;
import com.ddlab.gitpusher.github.core.GitHubHandlerImpl;
import com.ddlab.gitpusher.github.core.GitHubPusherImpl;

@Deprecated
public enum HostedGitType {

    GITHUB("GitHub") {

        IGitActionable gitAction = new GithubActionImpl();

        public IGitPusher getGitPusher(UserAccount userAccount) {
            IGitHandler gitHandler = new GitHubHandlerImpl(userAccount);
            return new GitHubPusherImpl(gitHandler);
        }
    };


    /** The git type. */
    private String gitType;

    /**
     * Instantiates a new git type.
     *
     * @param gitType the git type
     */
    private HostedGitType(String gitType) {
        this.gitType = gitType;
    }

    /**
     * Gets the git type.
     *
     * @return the git type
     */
    public String getGitType() {
        return gitType;
    }

    /**
     * From string.
     *
     * @param text the text
     * @return the git type
     */
    public static HostedGitType fromString(String text) {
        for (HostedGitType type : HostedGitType.values()) {
            if (text.equalsIgnoreCase(type.gitType))
                return type;
        }
        return null;
    }

    /**
     * Gets the git pusher.
     *
     * @param userAccount the user account
     * @return the git pusher
     */
    public abstract IGitPusher getGitPusher(UserAccount userAccount);
}
