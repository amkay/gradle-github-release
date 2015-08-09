/*
 * Copyright 2015 Max Käufer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.amkay.gradle.github.release.dsl

import org.ajoberstar.grgit.Grgit
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.CopySpec
import org.gradle.util.ConfigureUtil

/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    static final String NAME = 'githubRelease'

    private static final String SECTION_GITFLOW   = 'gitflow'
    private static final String SUBSECTION_PREFIX = 'prefix'
    private static final String KEY_VERSIONTAG    = 'versiontag'

    private static final DEFAULT_WORKING_DIR = 'github-release'

    private static final Collection<String> DEFAULT_TASKS_TO_UPLOAD_FROM = [ 'jar',
                                                                             'sourcesJar',
                                                                             'javadocJar',
                                                                             'groovydocJar',
                                                                             'distTar',
                                                                             'distZip' ]


    protected Project project

          String   user
          String   apiKey
          String   repo
          String   tagPrefix = 'v'
          String   workingPath
    final CopySpec upload


    GithubReleaseExtension(final Project project) {
        this.project = project

        String gitflowTagPrefix = getTagPrefixFromGitflowPlugin()

        if (gitflowTagPrefix) {
            tagPrefix = gitflowTagPrefix
        }

        this.workingPath = "${project.buildDir.name}/$DEFAULT_WORKING_DIR"
        this.upload = project.copySpec {
            defaultTasksToUploadFrom.each { task ->
                from task
            }
        }
    }


    void user(final String user) {
        this.user = user
    }

    void apiKey(final String apiKey) {
        this.apiKey = apiKey
    }

    void repo(final String repo) {
        this.repo = repo
    }

    String getRepo() {
        repo ?: "$user/${project.name}"
    }

    void tagPrefix(final String tagPrefix) {
        this.tagPrefix = tagPrefix
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

    Collection<Task> getDefaultTasksToUploadFrom() {
        DEFAULT_TASKS_TO_UPLOAD_FROM
          .collect { project.tasks.findByName it }
          .findAll { it }
    }

    private String getTagPrefixFromGitflowPlugin() {
        def grgit = Grgit.open dir: project.projectDir.path
        def gitflowTagPrefix = grgit.repository.jgit.repository.config.getString SECTION_GITFLOW, SUBSECTION_PREFIX,
                                                                                 KEY_VERSIONTAG
        grgit.close()

        gitflowTagPrefix
    }

}
