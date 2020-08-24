package com.logicsoul.mvp.data.api_services

import com.logicsoul.mvp.model.User
import retrofit2.Call
import retrofit2.http.GET

/**
 * This class is for all the data-fetch related use cases shows
 */
interface UserService {
    @GET("/posts")
    fun getUsers(): Call<List<User>>
}