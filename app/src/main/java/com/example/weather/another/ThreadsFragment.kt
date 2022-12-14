package com.example.weather.another

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather.databinding.FragmentThreadsBinding

class ThreadsFragment : Fragment() {

    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            hardWork()
        }
    }


    private fun hardWork(){
        Thread.sleep(5000L)
    }




    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}