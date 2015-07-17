package com.github.amkay.gradle.github.release.dsl

/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    String apiKey

    void apiKey(final String apiKey) {
        this.apiKey = apiKey
    }

}
