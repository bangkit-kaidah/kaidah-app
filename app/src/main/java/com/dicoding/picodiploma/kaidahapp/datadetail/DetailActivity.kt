package com.dicoding.picodiploma.kaidahapp.datadetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.dicoding.picodiploma.kaidahapp.MainActivity
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.kaidahapp.datahistory.HistoryActivity
import com.dicoding.picodiploma.kaidahapp.dataregulation.PageRegulationActivity
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
import com.dicoding.picodiploma.kaidahapp.retrofit.DataClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_DATA = "extra_data"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.Toolbar)
        binding.textToolbar.text = getString(R.string.title_detail)
        val gitId = intent.getIntExtra(EXTRA_DATA, 0)
        addDetailAPI(gitId)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    //get Detail Data
    private fun addDetailAPI(id: Int){
        DataClient.InstanceApi.getDetail(id).enqueue(object : Callback<List<DetailSerialized>>{
            override fun onResponse(
                call: Call<List<DetailSerialized>>,
                response: Response<List<DetailSerialized>>
            ) {
                val data = response.body()
                data?.let { getDetailAPI(it) }

            }

            override fun onFailure(call: Call<List<DetailSerialized>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getDetailAPI(id: List<DetailSerialized>){
        for (i in id){
            binding.apply {
                title.text = i.titleDocument
                placeDetermination.text = i.placeDetermination
                dateDetermination.text = i.dateDetermination
                number.text = i.numberRegulation
                dateRelease.text = i.dateRelease
                source.text = i.link
                kindRegulation.text = i.kindRegulation
                sourceDetail.text = i.sourceDetail
                type.text = i.type.name
                status.text = i.status.name
                fabutton.setOnClickListener {
                    val a = Intent(Intent.ACTION_VIEW, Uri.parse(i.sourceDetail.toString()))
                    startActivity(a)
                }
                shareToolbar.setOnClickListener {
                    val shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT, "Data Yang Anda Butuhkan ${i.sourceDetail}")
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }

                history.setOnClickListener {
                    val e = Intent(this@DetailActivity, HistoryActivity::class.java)
                    e.putExtra(HistoryActivity.EXTRA_HISTORY, i.id)
                    startActivity(e)
                }
            }
        }
    }


    //untuk option menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.transate) {
            val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(languageIntent)
        }
        else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

}