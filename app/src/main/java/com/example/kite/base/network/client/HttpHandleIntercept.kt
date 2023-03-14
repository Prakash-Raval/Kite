import com.example.kite.base.network.ApiClient
import com.example.kite.base.network.client.HttpCommonMethod
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response


class HttpHandleIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().headers(getJsonHeader()).build()
        val response: Response?

        response = chain.proceed(request)
        if (response.code == 401) {
            return ApiClient.generateCustomResponse(
                401, "",
                chain.request()
            )!!

        } else if (response.code == 500) {
            return ApiClient.generateCustomResponse(
                500, "",
                chain.request()
            )!!


        }

        return response
    }

    private fun getJsonHeader(): Headers {
        val builder = Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("Accept", "application/json")
        builder.add("Accept-Language", HttpCommonMethod.getLanguageCode())
        builder.add("is-mobile", "1")
        builder.add("lang-code", "en")

        return builder.build()
    }
}