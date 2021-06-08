package com.dicoding.picodiploma.kaidahapp.datasubject

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
import com.dicoding.picodiploma.kaidahapp.dataregulation.PageRegulationActivity
import com.dicoding.picodiploma.kaidahapp.databinding.ActivitySubjectSerializedBinding
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectSerializedActivity : AppCompatActivity() {

    private lateinit var adapterSubject: AdapterSubject
    private val list: ArrayList<SubjectSerialized> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManagerrv : LinearLayoutManager

    private lateinit var binding: ActivitySubjectSerializedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectSerializedBinding.inflate(layoutInflater)
        setSupportActionBar(binding.tolbar4)
        layoutManagerrv = LinearLayoutManager(this)

        addDataFromAPI()
        adapterSubject = AdapterSubject(list)
        recyclerView = binding.rvSubject
        setContentView(binding.root)
    }

    private fun addDataFromAPI(){
        DataClient.InstanceApi.getDataSubject().enqueue(object : Callback<ArrayList<SubjectSerialized>>{
            override fun onResponse(
                call: Call<ArrayList<SubjectSerialized>>,
                response: Response<ArrayList<SubjectSerialized>>
            ) {
                recyclerView.layoutManager = layoutManagerrv
                recyclerView.adapter = adapterSubject
                response.body()?.let { adapterSubject.setterList(it) }
                adapterSubject.setOnItemClickCallback(object : AdapterSubject.OnItemClickCallback{
                    override fun onItemCLicked(data: SubjectSerialized) {
                        showSelectedRecyclerView(data)
                    }
                })
                //adapterSubject.setterlist(response.body)
            }

            override fun onFailure(call: Call<ArrayList<SubjectSerialized>>, t: Throwable) {
            }

        })
    }

    private fun showSelectedRecyclerView(data: SubjectSerialized){
        val e = Intent(this, PageRegulationActivity::class.java)
        e.putExtra(PageRegulationActivity.EXTRA, data.id)
        startActivity(e)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.search)
        val searchV = search?.actionView as SearchView

        searchV.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchV.queryHint = "Masukkan Data"
        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                getSearchAPI(query)
                searchV.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getSearchAPI(newText)
                return false
            }
        })
        return true
    }

    private fun getSearchAPI(username: String){
        DataClient.InstanceApi.getSearchSubject(username).enqueue(object :Callback<ArrayList<SubjectSerialized>>{
            override fun onResponse(
                call: Call<ArrayList<SubjectSerialized>>,
                response: Response<ArrayList<SubjectSerialized>>
            ) {
                recyclerView.layoutManager = layoutManagerrv
                recyclerView.adapter = adapterSubject
                response.body()?.let { adapterSubject.setterList(it) }
                adapterSubject.setOnItemClickCallback(object : AdapterSubject.OnItemClickCallback{
                    override fun onItemCLicked(data: SubjectSerialized) {
                        showSelectedRecyclerView(data)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<SubjectSerialized>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}