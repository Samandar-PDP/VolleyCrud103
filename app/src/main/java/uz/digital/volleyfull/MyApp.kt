package uz.digital.volleyfull

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MyApp: Application() {
    companion object {
        var myApp: MyApp? = null
    }
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }
    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = this.javaClass::getSimpleName.toString()
        requestQueue?.add(request)
    }
}
