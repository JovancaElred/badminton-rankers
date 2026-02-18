package com.example.badmintonrankers.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.badmintonrankers.R
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.databinding.FragmentMemberBinding
import com.example.badmintonrankers.databinding.NewMemberSheetBinding
import com.example.badmintonrankers.view.adapter.PlayerAdapter
import com.example.badmintonrankers.view.viewmodel.MemberViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Member.newInstance] factory method to
 * create an instance of this fragment.
 */
class Member : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MemberViewModel by activityViewModels()

    private var _binding: FragmentMemberBinding? = null
    private val binding get() = _binding!!

    private var _addMemberBinding: NewMemberSheetBinding? = null
    private val addMemberBinding get() = _addMemberBinding!!

    private lateinit var adapter: PlayerAdapter

    private var dialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: View = view.findViewById(R.id.fab)

        adapter = PlayerAdapter{ player -> onClickPlayer(player)}
        binding.memberRecycler.adapter = adapter
        binding.memberRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.isLoading.collect{
                            loading ->
                        if(loading){
                            binding.loadingBar.visibility = View.VISIBLE
                            binding.loadingText.visibility = View.VISIBLE
                        } else{
                            binding.loadingBar.visibility = View.GONE
                            binding.loadingText.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.data.collect { list ->
                        if (list.isNotEmpty()) {
                            adapter.submitList(list)
                            binding.memberRecycler.visibility = View.VISIBLE
                            binding.cardSearch.visibility = View.VISIBLE
                            binding.noDataText.visibility = View.GONE
                            binding.noDataLottie.visibility = View.GONE
                        } else if (!viewModel.isLoading.value) {
                            binding.memberRecycler.visibility = View.GONE
                            binding.cardSearch.visibility = View.GONE
                            binding.noDataText.visibility = View.VISIBLE
                            binding.noDataLottie.visibility = View.VISIBLE
                        }
                    }

                }

                launch {
                    viewModel.error.collect { message ->
                        if (message != null) {
                            binding.noDataText.visibility = View.VISIBLE
                            binding.noDataLottie.visibility = View.VISIBLE
                            binding.memberRecycler.visibility = View.GONE
                            binding.cardSearch.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.addPlayerSuccess.collect{
                            success ->
                        Toast.makeText(requireContext(), "Member Added!", Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                        _addMemberBinding?.nameEditText?.text?.clear()
                    }
                }
            }
        }

        fab.setOnClickListener{
            dialog = BottomSheetDialog(requireContext())
            _addMemberBinding = NewMemberSheetBinding.inflate(layoutInflater)

            dialog?.setOnDismissListener {
                dialog = null
                _addMemberBinding = null
            }

            addMemberBinding.saveButton.setOnClickListener{
                val input = addMemberBinding.nameEditText.text
                val player = Players(
                    displayName = input.toString(),
                    mmr = 1500,
                    rank = "Silver",
                    wr = 100,
                    matches = 0,
                    win = 0,
                    lose = 0,
                    peakMmr = 1500,
                    lowestMmr = 1500,
                    rankImage = "silver",
                    rankImageLarge = "silver"
                )

                viewModel.addPlayer(player)
            }
            dialog?.setCancelable(true)
            dialog?.setContentView(addMemberBinding.root)
            dialog?.show()
        }

    }

    fun onClickPlayer(player: Players){
        print("a")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Member.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Member().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}