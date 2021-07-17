package com.example.submission1.activitycode

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1.R
import com.example.submission1.core.domain.model.Film
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var film: Film? = null
    private val mainViewModel: MainViewModel by viewModel()
    private var isFavorite : Boolean = false
    private var darifav : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        film = intent.getParcelableExtra("film")
        if(intent.hasExtra("darifav"))darifav=intent.getBooleanExtra("darifav",false)
        else darifav=false
        isFavorite=film!!.isFavorite
        Glide.with(this)
                .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${film!!.image}")
                .apply(RequestOptions().override(200, 150))
                .into(binding.imgItemPhoto)
        binding.tvJudul.text = film?.judul.toString()
        binding.tvIsi.text = film?.desc.toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnFav ->{
                isFavorite=!isFavorite
                if (isFavorite){
                    mainViewModel.setFavorite(film!!,isFavorite)
                    Toast.makeText(this,"Added to favorite", Toast.LENGTH_SHORT).show()
                }
                else{
                    mainViewModel.setFavorite(film!!,isFavorite)
                    Toast.makeText(this,"Removed from favorite", Toast.LENGTH_SHORT).show()
                }
//                countFavorite = film?.judul?.let { mainViewModel.getCountFavoriteById(it) }!!
            }
        }
        return super.onOptionsItemSelected(item)
    }
}