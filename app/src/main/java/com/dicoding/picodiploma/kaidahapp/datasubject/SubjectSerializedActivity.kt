package com.dicoding.picodiploma.kaidahapp.datasubject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var binding: ActivitySubjectSerializedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectSerializedBinding.inflate(layoutInflater)
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
                recyclerView.layoutManager = LinearLayoutManager(this@SubjectSerializedActivity)
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

    private fun showSelectedRecyclerView(data: SubjectSerialized){
        val e = Intent(this, PageRegulationActivity::class.java)
        e.putExtra(PageRegulationActivity.EXTRA, data.id)
        startActivity(e)
    }
}