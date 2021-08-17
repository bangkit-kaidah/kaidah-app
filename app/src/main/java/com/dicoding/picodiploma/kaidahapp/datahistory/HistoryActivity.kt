package com.dicoding.picodiploma.kaidahapp.datahistory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityHistoryBinding
import com.dicoding.picodiploma.kaidahapp.datadetail.DataHistory
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.AdapterRetrofit2
import com.dicoding.picodiploma.kaidahapp.dataregulation.DataSerialized
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HISTORY = "extra_history"
    }

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<DataHistory> = ArrayList()
    private lateinit var adapterHIstory : AdapterHistory

    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        adapterHIstory = AdapterHistory(list)
        recyclerView = binding.rvHistory
        val f = intent.getIntExtra(EXTRA_HISTORY,0)
        binding.textpromp.visibility = View.GONE
        addDetailAPI(f)
        setContentView(binding.root)
    }

    private fun addDetailAPI(id: Int){
        RetrofitClient.instance.getDataHistory(id).enqueue(object : Callback<List<DetailSerialized>> {
            override fun onResponse(
                call: Call<List<DetailSerialized>>,
                response: Response<List<DetailSerialized>>
            ) {
                val data = response.body()
                recyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
                recyclerView.adapter = adapterHIstory
                if (data != null) {
                    for (i in data){
                        val a = i.related
                        if (a.isEmpty()){
                            binding.textpromp.visibility = View.VISIBLE
                        } else{
                            binding.textpromp.visibility = View.GONE
                            adapterHIstory.setterList(a)
                            addAdapterDetail()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<DetailSerialized>>, t: Throwable) {
            }

        })
    }
    private fun addAdapterDetail() {
        adapterHIstory.setOnItemClickCallback(object : AdapterHistory.OnItemClickCallback {
            override fun onItemClicked(data: DataHistory) {

            }
        })
    }
}