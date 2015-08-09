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
package com.github.amkay.gradle.github.release.task

import com.github.amkay.gradle.github.release.dsl.GithubReleaseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction
import org.kohsuke.github.GHRelease
import org.kohsuke.github.GitHub

import java.nio.file.Files

/**
 * TODO
 *
 * @author Max Käufer
 */
class ReleaseTask extends DefaultTask {

    static final         String NAME   = 'publishGithubRelease'
    private static final Logger LOGGER = Logging.getLogger ReleaseTask


    @TaskAction
    void release() {
        def extension = project.extensions[ GithubReleaseExtension.NAME ] as GithubReleaseExtension

        def github = GitHub.connectUsingOAuth extension.apiKey
        def repository = github.getRepository extension.repository

        def releases = repository.listReleases()
        def release = releases.find { it.tagName == "${extension.tagPrefix}${project.version}".toString() } as GHRelease

        extension.workingDir.eachFile { file ->
            LOGGER.lifecycle "Uploading ${file.name} to GitHub"

            def mimeType = Files.probeContentType file.toPath()

            release.uploadAsset file, mimeType
        }
    }

    @Override
    String getGroup() {
        'publishing'
    }

    @Override
    String getDescription() {
        'Publishes artifacts to GitHub.'
    }

}
