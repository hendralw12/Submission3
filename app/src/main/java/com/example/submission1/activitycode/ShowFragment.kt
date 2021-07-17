package com.example.submission1.activitycode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.core.data.Resource
import com.example.submission1.core.domain.model.Film
import com.example.submission1.core.ui.MainAdapter
import com.example.submission1.databinding.FragmentShowBinding
import com.example.submission1.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ShowFragment : Fragment() {
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
        showRecyclerList()
        viewModel.tv.observe(viewLifecycleOwner,{listMovie ->
            if (listMovie != null) {
                when (listMovie) {
                    is Resource.Loading -> {
                        Log.d("fragment", "Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        listMovie.data?.let { list.addAll(it) }
                    }

                    is Resource.Error -> {
                        Log.d("fragment", "Error")
                        binding.progressBar.visibility = View.GONE
                        listMovie.message ?: "Oops Something Wrong..."
                    }

                }
            }
            binding.listFollower.adapter?.notifyDataSetChanged()
        })
        binding.progressBar.visibility = View.INVISIBLE
    }


    private fun showRecyclerList() {
        binding.listFollower.layoutManager = LinearLayoutManager(activity)
        val listAdapter = MainAdapter(list, "Show", requireContext())
        binding.listFollower.adapter = listAdapter
        listAdapter.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("film", it)
            requireContext().startActivity(intent)
        }

    }
}