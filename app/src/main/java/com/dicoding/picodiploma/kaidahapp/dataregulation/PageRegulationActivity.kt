package com.dicoding.picodiploma.kaidahapp.dataregulation

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityPageRegulationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageRegulationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA = "extra"
    }

    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<DataSerialized> = ArrayList()
    private lateinit var adapterp: AdapterRetrofit2
    private lateinit var layoutmanagerrv: LinearLayoutManager

    //projectnext page
    private var page = 1
    private var totalPage = 50
    private var isLoading = false
    private var hasNext = true
    private var currentQuery = ""

    private lateinit var binding: ActivityPageRegulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageRegulationBinding.inflate(layoutInflater)
        setSupportActionBar(binding.tolbar3)

        //inisialisasi
        layoutmanagerrv = LinearLayoutManager(this)
        recyclerView = binding.rvPageRegulation1
        adapterp = AdapterRetrofit2(list)

        //addDatafull from API
        val gitId = intent.getIntExtra(EXTRA, 0)
        if (gitId != -1) {
            addData(gitId)
            binding.progressBar.visibility = View.GONE
        } else {
            addDataAll()
            binding.progressBar.visibility = View.GONE
        }
        setContentView(binding.root)
        a()
    }

    private fun addDataAll() {
        RetrofitClient.instance.getDataAll().enqueue(object : Callback<SpecialSerialized> {
            override fun onResponse(
                    call: Call<SpecialSerialized>,
                    response: Response<SpecialSerialized>
            ) {
                if (response.isSuccessful) {
                    val date = response.body()?.data
                    setRecyclerView()
                    date?.let { adapterp.setterList(it) }
                    addAdapterDetail()
                    binding.progressBar.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
            }
        })
    }

    //add Data Full search and mandatory Data
    private fun addData(id: Int) {
        RetrofitClient.instance.getDataSpecial(id).enqueue(object : Callback<SpecialSerialized> {
            override fun onResponse(
                call: Call<SpecialSerialized>,
                response: Response<SpecialSerialized>
            ) {
                if (response.isSuccessful) {
                    val date = response.body()?.data
                    setRecyclerView()
                    date?.let { adapterp.setterList(it) }
                    addAdapterDetail()
                    binding.progressBar.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
            }
        })
    }

    private fun getSearchDocument(username: String) {
        RetrofitClient.instance.getSearchDocument(username)
            .enqueue(object : Callback<SpecialSerialized> {
                override fun onResponse(
                    call: Call<SpecialSerialized>,
                    response: Response<SpecialSerialized>
                ) {
                    if (response.isSuccessful) {
                        val date = response.body()?.data
                        list.clear()
                        date?.let { adapterp.setterList(it) }
                        addAdapterDetail()
                    }
                }

                override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                }

            })
    }
    //add Data Full search and mandatory Data

    //search override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.search)
        val searchV = search?.actionView as SearchView
        var searchEditText = searchV.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(resources.getColor(R.color.white))
        searchEditText.setHintTextColor(resources.getColor(R.color.white))

        searchV.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchV.queryHint = "Masukkan Data"
        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                list.clear()
                currentQuery = query
                getSearchDocument(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    list.clear()
                    isLoading = false
                    hasNext = true
                    currentQuery = ""
                    val gitId = intent.getIntExtra(EXTRA, 0)
                    if (gitId != -1) {
                        addData(gitId)
                    } else {
                        addDataAll()
                    }
                }
                return false
            }
        })
        return true
    }
    //search override

    //nextPage
    private fun getNextPage(isOnRefresh: Boolean) {
        isLoading = true
        if (isOnRefresh) binding.progressBar.visibility = View.VISIBLE
        val gitId = intent.getIntExtra(EXTRA, 0)
        if (gitId == -1) {
            RetrofitClient.instance.getDataAllSecondary(page)
                .enqueue(object : Callback<SpecialSerialized> {
                    override fun onResponse(
                        call: Call<SpecialSerialized>,
                        response: Response<SpecialSerialized>
                    ) {
                        val listRes = response.body()?.data
                        if (response.body()?.nextPage == null) {
                            hasNext = false
                        }
                        listRes?.let { adapterp.setterList(it) }
                        binding.progressBar.visibility = View.GONE
                        isLoading = false
                    }

                    override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                    }

                })
        }
        else {
            RetrofitClient.instance.getDataRegulationSecondary(gitId, page)
                .enqueue(object : Callback<SpecialSerialized> {
                    override fun onResponse(
                        call: Call<SpecialSerialized>,
                        response: Response<SpecialSerialized>
                    ) {
                        val listRes = response.body()?.data
                        if (response.body()?.nextPage == null) {
                            hasNext = false
                        }
                        listRes?.let { adapterp.setterList(it) }
                        binding.progressBar.visibility = View.GONE
                        isLoading = false
                    }

                    override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                    }

                })
        }
    }

    private fun getNextSearchPage(isOnRefreshSearch: Boolean, item: String) {
        isLoading = true
        if (isOnRefreshSearch) binding.progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.getDataSearchSecondary(item, page)
            .enqueue(object : Callback<SpecialSerialized>{
                override fun onResponse(
                    call: Call<SpecialSerialized>,
                    response: Response<SpecialSerialized>
                ) {
                    val listres = response.body()?.data
                    if (response.body()?.nextPage == null) {
                        hasNext = false
                    }
                    listres?.let { adapterp.setterList(it) }
                    binding.progressBar.visibility = View.GONE
                    isLoading = false

                }

                override fun onFailure(call: Call<SpecialSerialized>, t: Throwable) {
                }

            })

    }
    //nextPage


    //fungsi khusus

    private fun addAdapterDetail() {
        adapterp.setOnItemClickCallback(object : AdapterRetrofit2.OnItemClickCallback {
            override fun onItemClicked(data: DataSerialized) {
                showSelectedDate(data)
            }
        })
    }

    private fun showSelectedDate(date: DataSerialized) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, date.id)
        startActivity(intent)
    }

    private fun a() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && hasNext) {
                    page++
                    if(currentQuery == "") {
                        getNextPage(true)
                    }
                    else {
                        getNextSearchPage(true, currentQuery)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this@PageRegulationActivity)
        recyclerView.adapter = adapterp
    }
    //fungsi khusus
}
