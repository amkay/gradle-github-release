package com.github.amkay.gradle.github.release.dsl

/**
 * TODO
 *
 * @author Max Käufer
 */
class GithubReleaseExtension {

    static final String NAME = 'githubRelease'
   

    String user
    String apiKey


    void user(final String user) {
        this.user = user
    }

    void apiKey(final String apiKey) {
        this.apiKey = apiKey
    }

}
