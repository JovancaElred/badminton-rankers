package com.example.badmintonrankers.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.badmintonrankers.R
import com.example.badmintonrankers.databinding.FragmentLeaderboardBinding
import com.example.badmintonrankers.databinding.FragmentMemberBinding
import com.example.badmintonrankers.view.adapter.LeaderboardAdapter
import com.example.badmintonrankers.view.viewmodel.LeaderboardViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Leaderboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Leaderboard : Fragment() {
    private val viewModel : LeaderboardViewModel by activityViewModels()

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeaderboardAdapter()
        binding.leaderboardRecycler.adapter = adapter
        binding.leaderboardRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.isLoading.collect{
                        loading ->
                        if (loading){
                            binding.leaderboardLoading.visibility = View.VISIBLE
                        } else{
                            binding.leaderboardLoading.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.data.collect{
                        list ->
                        if(list.isNotEmpty()){
                            adapter.submitList(list)
                            binding.leaderboardRecycler.visibility = View.VISIBLE
                            binding.noDataLottie.visibility = View.GONE
                            binding.noDataText.visibility = View.GONE
                        } else if(!viewModel.isLoading.value){
                            binding.leaderboardRecycler.visibility = View.GONE
                            binding.noDataLottie.visibility = View.VISIBLE
                            binding.noDataText.visibility = View.VISIBLE
                        }
                    }
                }

                launch {
                    viewModel.error.collect{
                        message ->
                        if(message != null){
                            binding.noDataLottie.visibility = View.VISIBLE
                            binding.noDataText.visibility = View.VISIBLE
                            binding.leaderboardRecycler.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Leaderboard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Leaderboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}