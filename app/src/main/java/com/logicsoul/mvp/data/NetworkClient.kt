package com.logicsoul.mvp.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.logicsoul.mvp.BuildConfig
import com.logicsoul.mvp.data.api_services.UserService
import com.logicsoul.mvp.helper.EndpointManager
import com.logicsoul.mvp.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit class for remote api call
 * for different base url check  "https://blog.mindorks.com/getting-started-with-retrofit-using-kotlin-and-rxjava-android-networking-library"
 * https://futurestud.io/tutorials/retrofit-2-share-okhttp-client-and-converters-between-retrofit-instances
 */
class NetworkClient private constructor() {
    // other services can be added like below commented line
    //    private var loginService: LoginService
    //    private var signUpService: SignUpService
    private var userService: UserService

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    })
            .build()

        /*todo add interceptor logging here for okHttpClient or logging interceptor */

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(EndpointManager.getEndpointUrl(EndpointManager.Environment.DEVELOPMENT))
            // converter factory for rxjava
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

//        loginService = retrofit.create(LoginService::class.java)
//        signUpService = retrofit.create(SignUpService::class.java)
        userService = retrofit.create(UserService::class.java)
    }

    fun getUsers(): Call<List<User>> {
        return userService.getUsers()
    }


    /**
     * Retrofit single instance class with all services of the application
     */
    companion object {
        private var instance: NetworkClient? = null

        @Synchronized
        fun getInstance(): NetworkClient {

            if (instance == null) {
                instance = NetworkClient()
            }

            return instance as NetworkClient
        }
    }
}