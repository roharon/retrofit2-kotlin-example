package com.example.retrofit2_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.retrofit2_kotlin.Retrofit2.WeatherResponse
import com.example.retrofit2_kotlin.Retrofit2.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var apiCALL: Button = findViewById(R.id.callAPI)
        apiCALL.setOnClickListener{
            getAPI()
        }
    }

    fun getAPI(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call : Call<WeatherResponse>, response: Response<WeatherResponse>){
                if(response.code() == 200){
                    val weatherResponse = response.body()
                    Toast.makeText(this@MainActivity, weatherResponse!!.sys!!.country, Toast.LENGTH_LONG).show() }
            }

            override fun onFailure(calll: Call<WeatherResponse>, t: Throwable){
                Toast.makeText(this@MainActivity, "실패", Toast.LENGTH_LONG)
            }
        })
    }

    companion object {

        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "2e65127e909e178d0af311a81f39948c"
        var lat = "35"
        var lon = "139"
    }
}
