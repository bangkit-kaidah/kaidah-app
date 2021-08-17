package com.dicoding.picodiploma.kaidahapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityFollowedMemberBinding
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin.DetailJdhinActivity
import com.dicoding.picodiploma.kaidahapp.entity.MemberRespone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowedMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFollowedMemberBinding
    private lateinit var adapter: JdihnMemberAdapter
    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<JdhinSerialized> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowedMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = JdihnMemberAdapter(list)
        recyclerView = binding.rvJdihnMember
        addDataFromAPI()

    }

    private fun addDataFromAPI(){
        RetrofitClient.instanceUser.userFollowed().enqueue(object : Callback<ArrayList<JdhinSerialized>> {
            override fun onResponse(call: Call<ArrayList<JdhinSerialized>>, response: Response<ArrayList<JdhinSerialized>>) {
                recyclerView.layoutManager = LinearLayoutManager(this@FollowedMemberActivity)
                recyclerView.adapter = adapter
                response.body()?.let { adapter.setterList(it) }
                adapter.setOnItemClickCallback(object : JdihnMemberAdapter.OnItemClickCallback {
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
        val intent = Intent(this@FollowedMemberActivity, DetailJdhinActivity::class.java)
        intent.putExtra(DetailJdhinActivity.EXTRA_JDHIN, data.id)
        intent.putExtra(DetailJdhinActivity.SHOW_FAB, false)
        startActivity(intent)
    }

}