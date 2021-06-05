package com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityDetailJdhinBinding
import com.dicoding.picodiploma.kaidahapp.dataregulation.AdapterRetrofit2
import com.dicoding.picodiploma.kaidahapp.dataregulation.DataSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.SpecialSerialized
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJdhinActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_JDHIN = "extra_JDHIN"
    }

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<DataSerialized> = ArrayList()
    private lateinit var adapterp : AdapterRetrofit2

    private lateinit var binding: ActivityDetailJdhinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJdhinBinding.inflate(layoutInflater)
        adapterp = AdapterRetrofit2(list)
        recyclerView = binding.rvJdhin
        val gitId = intent.getIntExtra(EXTRA_JDHIN, 0)
        getDataFromAPI(gitId)
        setContentView(binding.root)
    }

    private fun getDataFromAPI(id: Int){
        DataClient.InstanceApi.getRegulationJdhin(id).enqueue(object : Callback<SpecialSerialized>{
            override fun onResponse(
                call: Call<SpecialSerialized>,
                response: Response<SpecialSerialized>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(this@DetailJdhinActivity)
                recyclerView.adapter = adapterp
                response.body()?.data?.let { adapterp.setterList(it) }
                adapterp.setOnItemClickCallback(object : AdapterRetrofit2.OnItemClickCallback {
                    override fun onItemClicked(data: DataSerialized) {
                    }
                })
            }

            override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}