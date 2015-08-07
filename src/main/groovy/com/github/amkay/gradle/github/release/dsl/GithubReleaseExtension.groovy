package com.github.amkay.gradle.github.release.dsl

import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.util.ConfigureUtil

/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    static final String NAME = 'githubRelease'


    private Project project

          String   user
          String   apiKey
          String   workingPath
    final CopySpec upload


    GithubReleaseExtension(final Project project) {
        this.project = project

        this.workingPath = "${project.buildDir.name}/github-release"
        this.upload = project.copySpec { from project.tasks[ 'jar' ] }
    }


    void user(final String user) {
        this.user = user
    }

    void apiKey(final String apiKey) {
        this.apiKey = apiKey
    }

    void workingPath(final String workingPath) {
        this.workingPath = workingPath
    }

    File getWorkingDir() {
        project.file workingPath
    }

    void upload(@DelegatesTo(CopySpec) final Closure cl) {
        ConfigureUtil.configure cl, upload
    }

}
