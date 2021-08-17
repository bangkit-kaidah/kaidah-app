package com.dicoding.picodiploma.kaidahapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.adapter.SubjectAdaptor
import com.dicoding.picodiploma.kaidahapp.adapter.TopSubjectAdapter
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentSubjectBinding
import com.dicoding.picodiploma.kaidahapp.datajdhin.JdhinSerialized
import com.dicoding.picodiploma.kaidahapp.dataregulation.PageRegulationActivity
import com.dicoding.picodiploma.kaidahapp.datasubject.AdapterSubject
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectFragment : Fragment() {

    private lateinit var _binding: FragmentSubjectBinding
    private val binding get() = _binding
    private lateinit var adapter: SubjectAdaptor
    private val list: ArrayList<SubjectSerialized> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManagerrv : LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManagerrv = LinearLayoutManager(requireContext())

        addDataFromAPI()
        adapter = SubjectAdaptor(list)
        recyclerView = binding.rvSubject

        binding.edCariSubject.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchJdhin(binding.edCariSubject.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun addDataFromAPI(){
        RetrofitClient.instance.getDataSubject().enqueue(object : Callback<ArrayList<SubjectSerialized>> {
            override fun onResponse(
                    call: Call<ArrayList<SubjectSerialized>>,
                    response: Response<ArrayList<SubjectSerialized>>
            ) {
                recyclerView.layoutManager = layoutManagerrv
                recyclerView.adapter = adapter
                response.body()?.let { adapter.setterList(it) }
                adapter.setOnItemClickCallback(object : SubjectAdaptor.OnItemClickCallback{
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
        val e = Intent(requireActivity(), PageRegulationActivity::class.java)
        e.putExtra(PageRegulationActivity.EXTRA, data.id)
        startActivity(e)
    }

    private fun getSearchJdhin(username: String){
        RetrofitClient.instance.getSearchSubject(username).enqueue(object : Callback<ArrayList<SubjectSerialized>> {
            override fun onResponse(
                    call: Call<ArrayList<SubjectSerialized>>,
                    response: Response<ArrayList<SubjectSerialized>>
            ) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                response.body()?.let { adapter.setterList(it) }
                adapter.setOnItemClickCallback(object : SubjectAdaptor.OnItemClickCallback {
                    override fun onItemCLicked(data: SubjectSerialized) {
                        showSelectedRecyclerView(data)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<SubjectSerialized>>, t: Throwable) {
            }

        })
    }

}