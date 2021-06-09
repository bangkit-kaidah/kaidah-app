package com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityDetailJdhinBinding
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.dataregulation.AdapterRetrofit2
import com.dicoding.picodiploma.kaidahapp.dataregulation.DataSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.SpecialSerialized
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJdhinActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_JDHIN = "extra_JDHIN"
        var EXTRA_FOLLOWED_JDHIN = "extra_JDHIN_follow"
        var status = false
    }

    private lateinit var sharedPreference: SharedPreference

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<DataSerialized> = ArrayList()
    private lateinit var adapterp : AdapterRetrofit2
    private lateinit var layoutmanagerrv: LinearLayoutManager
    private lateinit var fab: FloatingActionButton

    //projectnext page
    private var page = 1
    private var totalPage = 50
    private var isLoading = false
    private var statusFav = false

    private lateinit var binding: ActivityDetailJdhinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreference = SharedPreference(this)

        binding = ActivityDetailJdhinBinding.inflate(layoutInflater)
        layoutmanagerrv = LinearLayoutManager(this)
        adapterp = AdapterRetrofit2(list)
        recyclerView = binding.rvJdihn
        fab = binding.fabutton

        val gitId = intent.getIntExtra(EXTRA_JDHIN, 0)
        getDataFromAPI(gitId)

        val gitFollowed: Boolean = intent.getBooleanExtra(EXTRA_FOLLOWED_JDHIN, false)

        status = gitFollowed
        statusFav = status
        Log.d("asadsdasdasda", statusFav.toString())
        Log.d("asadsdasdasda", gitFollowed.toString())

        statusFavorite(statusFav)
        //tambahan
        val id = sharedPreference.getValueInt("roleId")
        if (id == 1){
            fab.visibility = View.GONE
        }
        fab.setOnClickListener {
            if (statusFav == false) {
                statusFavorite(true)
                postDataToApi(gitId)
            } else {
                statusFavorite(false)
                deleteDataAPI(gitId)
            }
        }

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

    private fun getNextJdhin(isOnRefresh: Boolean) {
        isLoading = true
        if (isOnRefresh) binding.progressBar.visibility = View.VISIBLE
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
                        getNextJdhin(false)
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

    //tambahan
    private fun statusFavorite(item: Boolean){
        if (item){
            fab.setImageResource(R.drawable.unfavorite)
        } else {
            fab.setImageResource(R.drawable.favorite)
        }
    }

    //tambahan
    private fun postDataToApi(id: Int){
        RetrofitClient.instanceUser.userFollow(id).enqueue(object : Callback<FollowResponse>{
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                response.errorBody()?.let { Log.d("saddsaasdda1", it.string()) }
                Log.d("saddsaasdda1", response.message())
                Log.d("saddsaasdda2", response.body().toString())
                Log.d("saddsaasdda3", response.body()?.message.toString())
                val a = response.body()?.message.toString()
                statusFav = true
                status = true
                Toast.makeText(applicationContext,a, Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    //tambahan
    private fun deleteDataAPI(id: Int){
        RetrofitClient.instanceUser.deleteUserFollow(id).enqueue(object : Callback<FollowResponse>{
            override fun onResponse(call: Call<FollowResponse>, response: Response<FollowResponse>) {
                Log.d("saddsaasdda4", response.message())
                Log.d("saddsaasdda5", response.body().toString())
                Log.d("saddsaasdda6", response.body()?.message.toString())
                statusFav = false
                status = false
                Toast.makeText(applicationContext, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}