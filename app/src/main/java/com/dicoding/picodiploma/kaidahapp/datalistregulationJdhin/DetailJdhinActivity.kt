package com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityDetailJdhinBinding
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.AdapterRetrofit2
import com.dicoding.picodiploma.kaidahapp.dataregulation.DataSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.PageRegulationActivity
import com.dicoding.picodiploma.kaidahapp.dataregulation.SpecialSerialized
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
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
    private lateinit var layoutmanagerrv: LinearLayoutManager

    //projectnext page
    private var page = 1
    private var totalPage = 50
    private var isLoading = false

    private lateinit var binding: ActivityDetailJdhinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailJdhinBinding.inflate(layoutInflater)
        layoutmanagerrv = LinearLayoutManager(this)
        adapterp = AdapterRetrofit2(list)
        recyclerView = binding.rvJdihn

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
                recyclerView.layoutManager = layoutmanagerrv
                recyclerView.adapter = adapterp
                response.body()?.data?.let { adapterp.setterList(it) }
                b()
                adapterp.setOnItemClickCallback(object : AdapterRetrofit2.OnItemClickCallback {
                    override fun onItemClicked(data: DataSerialized) {
                        showSelectedRecyclerView(data)
                    }
                })
            }

            override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
            }

        })
    }

    private fun getNextSearchJdhin(isOnRefreshSearch: Boolean) {
        isLoading = true
        if (isOnRefreshSearch) binding.progressBar.visibility = View.VISIBLE
        val gitId = intent.getIntExtra(EXTRA_JDHIN, 0)
        DataClient.InstanceApi.getDataJDHINSecondary(gitId,page)
            .enqueue(object : Callback<SpecialSerialized>{
                override fun onResponse(
                    call: Call<SpecialSerialized>,
                    response: Response<SpecialSerialized>
                ) {
                    val listrest = response.body()?.data
                    listrest?.let { adapterp.setterList(it) }
                    b()
                    binding.progressBar.visibility = View.GONE
                    isLoading = false
                }

                override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                }
            })
    }

    private fun b(){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleCount = layoutmanagerrv.childCount
                val pastVisibleItem = layoutmanagerrv.findFirstCompletelyVisibleItemPosition()
                val total = adapterp.itemCount

                if (!isLoading && page < totalPage) {
                    if (visibleCount + pastVisibleItem <= total) {
                        page++
                        getNextSearchJdhin(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun showSelectedRecyclerView(data: DataSerialized){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, data.id)
        startActivity(intent)
    }

}