package com.example.submission1.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.activitycode.DetailActivity
import com.example.submission1.core.domain.model.Film
import com.example.submission1.core.ui.FilmAdapter
import com.example.submission1.databinding.FragmentShowBinding
import com.example.submission1.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ShowFavFragment : Fragment() {
    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
    private var list: ArrayList<Film> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        val adapters = FilmAdapter()
        adapters.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("film", selectedData)
            startActivity(intent)
        }
        viewModel.favTV.observe(viewLifecycleOwner, { listMovie ->
            adapters.setData(listMovie)
        })
        with(binding.listFollower) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapters
        }
        binding.progressBar.visibility = View.INVISIBLE
    }


}