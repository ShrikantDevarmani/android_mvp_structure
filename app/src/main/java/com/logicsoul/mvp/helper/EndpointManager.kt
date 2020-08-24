package com.logicsoul.mvp.helper

/**
 * Manager for getting endpoints for Current application with different environments
 */

object EndpointManager {
    /**
     * Get the endpoint URL for given CURRENT_APPLICATION environment
     *
     * @param environment The CURRENT_APPLICATION environment
     * @return Endpoint URL
     */
    fun getEndpointUrl(environment: Environment): String {
        return environment.url
    }

    /**
     * Checks if URL matches a isApplication environment URL
     *
     * @param url URL to test
     * @return true if isApplication URL
     */
    fun isApplicationUrl(url: String): Boolean {
        for (environment in Environment.values()) { // check if URL matches environment URL
            if (url == environment.url) {
                return true
            }
        }
        return false
    }

    /**
     * Checks if URL is a custom URL i.e. does not match an CURRENT DOMAIN environment URL
     *
     * @param url URL to test
     * @return true if custom URL
     */
    fun isCustomUrl(url: String): Boolean {
        return !isApplicationUrl(url)
    }

    enum class Environment(var url: String) {
        /**
         * Development environment
         * todo change urls accordingly
         */
//        DEVELOPMENT("https://mobileapi.development.com"),
        DEVELOPMENT("https://jsonplaceholder.typicode.com"),

        /**
         * Test environment
         */
        TEST("https://mobileapi.test.com"),

        /**
         * Uat environment
         */
        UAT("https://mobileapi.uat.com"),

        /**
         * Production environment
         */
        PRODUCTION("https://mobileapi.production.com");
    }
}