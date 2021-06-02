package com.dicoding.picodiploma.kaidahapp.dataregulation

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityPageRegulationBinding
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageRegulationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA = "extra"
    }

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<DataSerialized> = ArrayList()
    private lateinit var adapterp : AdapterRetrofit2

    private lateinit var binding: ActivityPageRegulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageRegulationBinding.inflate(layoutInflater)
        setSupportActionBar(binding.tolbar3)
        recyclerView = binding.rvPageRegulation1
        adapterp = AdapterRetrofit2(list)
        val gitId = intent.getIntExtra(EXTRA, 0)
        addData(gitId)
        setContentView(binding.root)
    }

    private fun addData(id: Int){
        DataClient.InstanceApi.getDataSpecial(id).enqueue(object : Callback<SpecialSerialized>{
            override fun onResponse(
                call: Call<SpecialSerialized>,
                response: Response<SpecialSerialized>
            ) {
                if (response.isSuccessful) {
                    val date = response.body()?.data
                    recyclerView.layoutManager = LinearLayoutManager(this@PageRegulationActivity)
                    recyclerView.adapter = adapterp
                    date?.let { adapterp.setterList(it) }
                    adapterp.setOnItemClickCallback(object : AdapterRetrofit2.OnItemClickCallback {
                        override fun onItemClicked(data: DataSerialized) {
                            showSelectedDate(data)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
            }
        })
    }

    private fun showSelectedDate(date: DataSerialized) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, date.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.search_menu, menu)
//        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val search = menu.findItem(R.id.search)
//        val searchV = search?.actionView as SearchView
//
//        searchV.setSearchableInfo(manager.getSearchableInfo(componentName))
//        searchV.queryHint = "Masukkan Data"
//        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchV.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
        return true
    }
}