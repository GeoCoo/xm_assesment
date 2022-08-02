package com.example.questionsApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.questionsApp.R
import com.example.questionsApp.databinding.FragmentWelcomeBinding
import com.example.questionsApp.ui.view.ButtonView

class WelcomeFragment : Fragment(), ButtonView.BtnClickListener {

    private lateinit var binding: FragmentWelcomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            startBtn.bind("Start Survey")
            startBtn.btnClickListener = this@WelcomeFragment

        }
    }

    override fun onBtnActionClick() {
        findNavController().navigate(R.id.action_go_to_survey)
    }

}