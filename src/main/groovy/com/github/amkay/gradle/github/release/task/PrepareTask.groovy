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
import org.gradle.api.tasks.TaskAction

/**
 * TODO
 *
 * @author Max Käufer
 */
class PrepareTask extends DefaultTask {

    static String NAME = 'prepareGithubRelease'

    @TaskAction
    void prepare() {
        def extension = project.extensions[ GithubReleaseExtension.NAME ] as GithubReleaseExtension

        project.copy {
            with extension.upload
            into extension.workingDir
        }
    }

    @Override
    String getDescription() {
        'Prepares the artifacts to upload to GitHub locally.'
    }
}
