package com.example.badmintonrankers.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.badmintonrankers.R
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.databinding.FragmentMemberBinding
import com.example.badmintonrankers.databinding.NewMemberSheetBinding
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

        fab.setOnClickListener{
            val dialog = BottomSheetDialog(requireContext())
            _addMemberBinding = NewMemberSheetBinding.inflate(layoutInflater)


            addMemberBinding.saveButton.setOnClickListener{
                val input = addMemberBinding.nameEditText.text
                val player = Players(
                    displayName = input.toString(),
                    mmr = 1500,
                    rank = "Bronze",
                    wr = 100,
                    matches = 0,
                    win = 0,
                    lose = 0,
                    peakMmr = 1500,
                    lowestMmr = 1500,
                )

                viewModel.addPlayer(player)
            }
            dialog.setCancelable(true)
            dialog.setContentView(addMemberBinding.root)
            dialog.show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.addPlayerSuccess.collect{
                            success ->
                        if(success == true){
                            Toast.makeText(requireContext(), "Member Added!", Toast.LENGTH_SHORT).show()
                            addMemberBinding.nameEditText.text.clear()
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