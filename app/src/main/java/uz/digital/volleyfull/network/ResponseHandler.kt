package uz.digital.volleyfull.network

interface ResponseHandler {
    fun onResponse(string: String)
    fun onError(string: String?)
}