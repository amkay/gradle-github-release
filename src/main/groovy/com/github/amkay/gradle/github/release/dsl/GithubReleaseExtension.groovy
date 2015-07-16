package com.github.amkay.gradle.github.release.dsl

/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    String githubApiKey

    void githubApiKey(final String githubApiKey) {
        this.githubApiKey = githubApiKey
    }

}
