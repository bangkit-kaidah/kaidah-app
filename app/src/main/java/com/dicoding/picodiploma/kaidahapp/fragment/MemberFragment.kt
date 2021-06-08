package com.dicoding.picodiploma.kaidahapp.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentMemberBinding
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.datalistregulationJdhin.DetailJdhinActivity
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberFragment : Fragment() {

    private lateinit var _binding: FragmentMemberBinding
    private val binding get() = _binding
    private lateinit var adapter: JdihnMemberAdapter
    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<JdhinSerialized> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JdihnMemberAdapter(list)
        recyclerView = binding.rvJdihnMember
        addDataFromAPI()

        binding.edCariMember.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchJdhin(binding.edCariMember.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun addDataFromAPI(){
        DataClient.InstanceApi.getDataJdhin().enqueue(object : Callback<ArrayList<JdhinSerialized>> {
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

    private fun showSelectedDate(data: JdhinSerialized) {
        val intent = Intent(requireActivity(), DetailJdhinActivity::class.java)
        intent.putExtra(DetailJdhinActivity.EXTRA_JDHIN, data.id)
        startActivity(intent)

    }

    private fun getSearchJdhin(username: String){
        DataClient.InstanceApi.getSearchJdhin(username).enqueue(object : Callback<ArrayList<JdhinSerialized>> {
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