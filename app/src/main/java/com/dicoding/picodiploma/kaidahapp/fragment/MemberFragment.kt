package com.dicoding.picodiploma.kaidahapp.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentMemberBinding
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin.DetailJdhinActivity
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberFragment : Fragment() {

    private lateinit var _binding: FragmentMemberBinding
    private val binding get() = _binding
    private lateinit var adapter: JdihnMemberAdapter
    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<JdhinSerialized> = ArrayList()
    private lateinit var sharedPreference: SharedPreference
    private var isSignedin : Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMemberBinding.inflate(inflater, container, false)

        sharedPreference = SharedPreference(requireContext())

        adapter = JdihnMemberAdapter(list)
        recyclerView = binding.rvJdihnMember

        isSignedin = sharedPreference.getValueBoolien("signed", false)

        if (isSignedin == true) {
            addDataFromAPIUser()
        }
        if (isSignedin == false) {
            addDataFromAPI()
        }

        binding.edCariMember.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchJdhin(binding.edCariMember.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (isSignedin) {
            addDataFromAPIUser()
        }
        else {
            addDataFromAPI()
        }
    }

    private fun addDataFromAPI(){
        RetrofitClient.instance.getDataJdhin().enqueue(object : Callback<ArrayList<JdhinSerialized>> {
            override fun onResponse(
                    call: Call<ArrayList<JdhinSerialized>>,
                    response: Response<ArrayList<JdhinSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
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

    private fun addDataFromAPIUser(){
        RetrofitClient.instanceUser.getDataJdhin().enqueue(object : Callback<ArrayList<JdhinSerialized>> {
            override fun onResponse(
                call: Call<ArrayList<JdhinSerialized>>,
                response: Response<ArrayList<JdhinSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                response.body()?.let { adapter.setterList(it) }
                adapter.setOnItemClickCallback(object : JdihnMemberAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: JdhinSerialized) {
                        showSelectedDateUser(data)
                    }
                })
            }
            override fun onFailure(call: Call<ArrayList<JdhinSerialized>>, t: Throwable) {
            }
        })
    }

    private fun showSelectedDate(data: JdhinSerialized) {
        val intent = Intent(requireActivity(), DetailJdhinActivity::class.java)
        intent.putExtra(DetailJdhinActivity.EXTRA_JDHIN, data.id)
        startActivity(intent)
    }

    private fun showSelectedDateUser(data: JdhinSerialized) {
        val intent = Intent(requireActivity(), DetailJdhinActivity::class.java)
        intent.putExtra(DetailJdhinActivity.EXTRA_JDHIN, data.id)
        intent.putExtra(DetailJdhinActivity.EXTRA_FOLLOWED_JDHIN, data.is_following)
        Log.d("token1", data.is_following.toString())
        startActivity(intent)
    }

    private fun getSearchJdhin(username: String){
        RetrofitClient.instance.getSearchJdhin(username).enqueue(object : Callback<ArrayList<JdhinSerialized>> {
            override fun onResponse(
                    call: Call<ArrayList<JdhinSerialized>>,
                    response: Response<ArrayList<JdhinSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
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

}