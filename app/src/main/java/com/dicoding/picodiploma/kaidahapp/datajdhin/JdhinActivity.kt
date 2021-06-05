package com.dicoding.picodiploma.kaidahapp.datajdhin

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityJdhinBinding
import com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin.DetailJdhinActivity
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JdhinActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<JdhinSerialized> = ArrayList()
    private lateinit var adapterJdhin : AdapterJdhin

    private lateinit var binding: ActivityJdhinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJdhinBinding.inflate(layoutInflater)
        setSupportActionBar(binding.tolbar4)

        adapterJdhin = AdapterJdhin(list)
        recyclerView = binding.rvPageJdihn
        addDataFromAPI()

        setContentView(binding.root)
    }

    private fun addDataFromAPI(){
        DataClient.InstanceApi.getDataJdhin().enqueue(object : Callback<ArrayList<JdhinSerialized>>{
            override fun onResponse(
                call: Call<ArrayList<JdhinSerialized>>,
                response: Response<ArrayList<JdhinSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(this@JdhinActivity)
                recyclerView.adapter = adapterJdhin
                response.body()?.let { adapterJdhin.setterList(it) }
                adapterJdhin.setOnItemClickCallback(object : AdapterJdhin.OnItemClickCallback {
                    override fun onItemClicked(data: JdhinSerialized) {
                        showSelectedDate(data)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<JdhinSerialized>>, t: Throwable) {
            }

        })
    }

    private fun showSelectedDate(data: JdhinSerialized) {
        val intent = Intent(this, DetailJdhinActivity::class.java)
        intent.putExtra(DetailJdhinActivity.EXTRA_JDHIN, data.id)
        startActivity(intent)

    }

    //for search
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
//                getSearchJdhin(query)
//                searchV.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                getSearchJdhin(newText)
//                return false
//            }
//        })
        return true
    }


    private fun getSearchJdhin(username: String){
        DataClient.InstanceApi.getSearchJdhin(username).enqueue(object : Callback<ArrayList<JdhinSerialized>>{
            override fun onResponse(
                call: Call<ArrayList<JdhinSerialized>>,
                response: Response<ArrayList<JdhinSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(this@JdhinActivity)
                recyclerView.adapter = adapterJdhin
                response.body()?.let { adapterJdhin.setterList(it) }
                adapterJdhin.setOnItemClickCallback(object : AdapterJdhin.OnItemClickCallback {
                    override fun onItemClicked(data: JdhinSerialized) {
                        showSelectedDate(data)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<JdhinSerialized>>, t: Throwable) {
            }

        })
    }

}