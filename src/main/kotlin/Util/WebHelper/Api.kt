package Util.WebHelper

import io.reactivex.Observable
import retrofit2.http.*

interface Api {

    @POST("persons")
    fun createUser(@QueryMap fields: Map<String, String>): Observable<Int>

    @DELETE("persons/{personId}")
    fun delUser(@Path("personId") personId:Int):Observable<Int>

}