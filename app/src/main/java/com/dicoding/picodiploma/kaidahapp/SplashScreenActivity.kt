package com.dicoding.picodiploma.kaidahapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.dicoding.picodiploma.kaidahapp.adapter.TopSubjectAdapter
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivitySplashScreenBinding
import com.dicoding.picodiploma.kaidahapp.entity.FeaturedSubjectsResponse
import com.dicoding.picodiploma.kaidahapp.entity.RegulationParam
import com.dicoding.picodiploma.kaidahapp.entity.TopRegulationsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {
    private var timeOut:Long = 3000
    private lateinit var adapterTop: TopSubjectAdapter
    private lateinit var binding: ActivitySplashScreenBinding

    companion object {
        val dataTopRegulation: ArrayList<RegulationParam> = arrayListOf()
        val dataFeaturedSubjects: ArrayList<FeaturedSubjectsResponse> = arrayListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDataFeaturedSubjects()
        inputDataTopRegulations()
        loadSplashScreen()

    }

    private fun inputDataFeaturedSubjects(){
        var respond: String? = null
        RetrofitClient.instance.topSubject().enqueue(object: Callback<ArrayList<FeaturedSubjectsResponse>> {
            override fun onResponse(call: Call<ArrayList<FeaturedSubjectsResponse>>, response: Response<ArrayList<FeaturedSubjectsResponse>>) {
                respond = response.body()?.toString()
                if (!respond.isNullOrEmpty()){
                    for( i in 0 until response.body()?.size!!){
                        //get all data in the same way
                        response.body()?.get(i)?.let { dataFeaturedSubjects.add(it) }
                        response.body()?.get(i)?.let { Log.e("namenya", ""+it.name) };
                    }
                    val param = FeaturedSubjectsResponse()
                    param.subject_id = -1
                    param.name = "Tampilkan Semua"
                    param.total_documents = 0
                    dataFeaturedSubjects.add(param)
                    Log.d("test1", respond.toString())
                    Log.d("test2", dataFeaturedSubjects.toString())
                } else {
                    Log.d("TestResponse2", respond.toString())
                }
            }
            override fun onFailure(call: Call<ArrayList<FeaturedSubjectsResponse>>, t: Throwable) {
                Toast.makeText(this@SplashScreenActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun inputDataTopRegulations(){
        var respond: String? = null
        RetrofitClient.instance.topRegulations().enqueue(object: Callback<TopRegulationsResponse> {
            override fun onResponse(call: Call<TopRegulationsResponse>, response: Response<TopRegulationsResponse>) {
                respond = response.body().toString()
                if (!respond.isNullOrEmpty()){
                    for( i in 0 until response.body()?.data?.size!! - 2){
                        //get all data in the same way
                        response.body()?.data?.get(i)?.let { dataTopRegulation.add(it) }
                        response.body()?.data?.get(i)?.let { Log.e("namenya", ""+it.judul_dokumen) };
                    }
                    val param = RegulationParam()
                    param.id = -1
                    param.judul_dokumen = "Tampilkan Semua"
                    dataTopRegulation.add(param)
                    Log.d("test", respond.toString())
                    Log.d("test", dataTopRegulation.toString())
                } else {
                    Log.d("TestResponse2", respond.toString())
                }
            }
            override fun onFailure(call: Call<TopRegulationsResponse>, t: Throwable) {
                Toast.makeText(this@SplashScreenActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },timeOut)
    }

}