package uz.digital.volleyfull.network

import com.android.volley.Request.Method
import com.android.volley.toolbox.StringRequest
import uz.digital.volleyfull.MyApp
import uz.digital.volleyfull.model.Post

class VolleyInstance {
    companion object {
        const val BASE_URL = "http://jsonplaceholder.typicode.com"
    }

    fun get(responseHandler: ResponseHandler) {
        val getRequest = object : StringRequest(
            Method.GET,
            "$BASE_URL/posts",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.stackTraceToString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(getRequest)
    }

    fun getById(id: Int, responseHandler: ResponseHandler) {
        val getRequest = object : StringRequest(
            Method.GET,
            "$BASE_URL/posts/$id",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.stackTraceToString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(getRequest)
    }

    fun post(body: Post, responseHandler: ResponseHandler) {
        val postRequest = object : StringRequest(
            Method.POST,
            "$BASE_URL/posts",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String, String>()
                hashMap["id"] = body.id.toString()
                hashMap["userId"] = body.userId.toString()
                hashMap["title"] = body.title
                hashMap["body"] = body.body
                return hashMap
            }
        }
        MyApp.myApp?.addToRequestQueue(postRequest)
    }

    fun put(body: Post, responseHandler: ResponseHandler) {
        val putRequest = object : StringRequest(
            Method.PUT,
            "$BASE_URL/posts/${body.id}",
            {
                responseHandler.onResponse(it)
            }, {
                responseHandler.onError(it.toString())
            }
        ) {
//            override fun getParams(): MutableMap<String, String> {
//                val hashMap = HashMap<String, String>()
//                hashMap["id"] = body.id.toString()
//                hashMap["userId"] = body.userId.toString()
//                hashMap["title"] = body.title
//                hashMap["body"] = body.body
//                return hashMap
//            }
        }
        MyApp.myApp?.addToRequestQueue(putRequest)
    }
    fun delete(id: Int, responseHandler: ResponseHandler) {
        val deleteRequest = object : StringRequest(
            Method.DELETE,
            "$BASE_URL/posts/$id",
            {
                responseHandler.onResponse(it)
            }, {
                responseHandler.onError(it.toString())
            }
        ) {

        }
        MyApp.myApp?.addToRequestQueue(deleteRequest)
    }
}