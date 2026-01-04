package com.ddlab.rnd.ui.dialog;

import com.ddlab.rnd.setting.PublisherSetting;
import com.ddlab.rnd.ui.CodePublishPanelComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Slf4j
public class CodePublishDialog extends DialogWrapper {

    private JPanel panel;
    private CodePublishPanelComponent  codePublishPanelComponent;
    private Project project;

    public CodePublishDialog(@Nullable Project project, File selectedRepo, boolean canBeParent) {
        super(project, canBeParent);
        this.project = project;
        setTitle("Temporary Title ...");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = createUIAndGetPanel();
        return panel;
    }

    @Override
    protected void doOKAction() {
        saveLastSessionSetting();
//        getGitInfo();
        close(1);
//        new RepoExecutor(this).createRepo();
    }



    // ~~~~~~~~ private methods ~~~~~~~~

    @Deprecated
    private void getGitInfo() {
        Repository repository = null;
        try {
//            String projectFilePath = this.project.getProjectFilePath();
            String projectFilePath = this.project.getBasePath();
            log.debug("project file path: {}", projectFilePath);
            repository = new FileRepositoryBuilder()
                    .setGitDir(new File(projectFilePath+"/"+".git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();
            Git git = new Git(repository);
            Repository repo = git.getRepository();
            StoredConfig config = repository.getConfig();
            String gitUrl = config.getString("remote", "origin", "url");
            log.debug("Git URL: " + gitUrl);
//            String gitUrl = getPrimaryGitUrl(repo);
//            log.debug("Git URL: " + gitUrl);


        } catch (Exception e) {
            log.error("Exception while reading git info: {}", e);
        }



//        GitRepository gitRepo = GitUtil.getRepositoryManager(project).getRepositories().get(0);
//        String branch = gitRepo.getCurrentBranchName();
    }

    @Deprecated
    public static String getPrimaryGitUrl(Repository repository) {
        StoredConfig config = repository.getConfig();

        // try origin first
        String url = config.getString("remote", "origin", "url");
        if (url != null) {
            return url;
        }

        // fallback to first available remote
        for (String remoteName : config.getSubsections("remote")) {
            url = config.getString("remote", remoteName, "url");
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    private void saveLastSessionSetting() {
        PublisherSetting setting = PublisherSetting.getInstance();
        JComboBox hostedGitTypeCombo = codePublishPanelComponent.getHostedGitTypeCombo();
        JComboBox slGitUserNameCombo = codePublishPanelComponent.getSlGitUserNameCombo();
        // Save the last session, save when window is closed, not here
        setting.setLastSavedHostedGitTypeSelection(hostedGitTypeCombo.getSelectedItem().toString());
        setting.setLastSavedGitUserNameSelection(slGitUserNameCombo.getSelectedItem().toString());
    }
    private JPanel createUIAndGetPanel() {
        codePublishPanelComponent = new CodePublishPanelComponent();
        return codePublishPanelComponent.getMainPanel();
    }

//    public GridBagLayout getPanelLayout() {
//        GridBagLayout gridBagLayout = new GridBagLayout();
//        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
//        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
//        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
//        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
//        return gridBagLayout;
//    }
}
