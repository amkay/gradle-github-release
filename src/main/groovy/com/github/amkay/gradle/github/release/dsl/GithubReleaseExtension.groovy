package com.github.amkay.gradle.github.release.dsl

import org.gradle.api.Project
/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    static final String NAME = 'githubRelease'


    private Project  project
            String   user
            String   apiKey
            String   workingPath


    GithubReleaseExtension(final Project project) {
        this.project = project
        this.workingPath = "${project.buildDir.name}/github-release"
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

}
