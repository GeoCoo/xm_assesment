package com.example.questionsApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionsApp.R
import com.example.questionsApp.databinding.FragmentQuestionBinding
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.models.Question
import com.example.questionsApp.network.ResponseStatus
import com.example.questionsApp.ui.viewUtils.ButtonView
import com.example.questionsApp.ui.viewUtils.ConfirmationVIew
import com.example.questionsApp.ui.viewUtils.CustomRecyclerManager
import com.example.questionsApp.ui.viewUtils.RecyclerAdapter
import com.example.questionsApp.utils.BtnAction
import com.example.questionsApp.utils.QuestionNavBtnState
import com.example.questionsApp.utils.SubmissionConfirmation
import com.example.questionsApp.utils.convertToModel
import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QuestionFragment : Fragment(), ConfirmationVIew.ConfirmationViewClickListener, ButtonView.BtnClickListener {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var adapter: RecyclerAdapter
    private var questionsSize: Int? = 0
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: QuestionViewModel by sharedViewModel()
    private var questionList: List<Question>? = null
    private var answerToSubmit: AnswerToSubmit? = null
    private var submittedIdList: List<Int>? = null

    private var answerCallback: (AnswerToSubmit?) -> Unit = { answerToSubmit = it }

    override fun onBtnActionClick() {
        mainViewModel.postSubmittedAnswer(answerToSubmit)
        viewModel.removeView(binding.confirmationVIew)
    }

    override fun onConfirmActionClick(btnAction: BtnAction) {
        when (btnAction) {
            BtnAction.RETRY -> {
                mainViewModel.postSubmittedAnswer(answerToSubmit)
                viewModel.removeView(binding.confirmationVIew)
            }
            BtnAction.CLOSE -> {
                viewModel.removeView(binding.confirmationVIew)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            observeQuestions()
            checkSubmissionStatus()
            navigateBack()
            clickNextBtn()
            clickPreviousBtn()
            clickCount()
            setSuccessfulSubmissions()
            observeSubmittedIds()
            submit.btnClickListener = this@QuestionFragment
            confirmationVIew.confirmationClickListener = this@QuestionFragment
        }
    }

    private fun setSubmitBtn(id: Int?) {
        binding.apply {
            submit.bind(viewModel.checkIfSubmitted(id))
        }
    }

    private fun observeSubmittedIds() {
        viewModel.observeSubmittedIds(viewLifecycleOwner) { submittedIds ->
            submittedIdList = submittedIds
        }
    }

    private fun observeQuestions() {
        mainViewModel.observeResponse(viewLifecycleOwner) { questionsResponse ->
            questionList = questionsResponse.convertToModel()
            questionsSize = questionsResponse?.size
            binding.questionsTotal.text = questionsSize.toString()
            initRecyclerView(questionList)
        }
    }

    private fun checkSubmissionStatus() {
        mainViewModel.observeSubmissionResponse(viewLifecycleOwner) { response ->
            if (response == ResponseStatus.OK.code.toString()) {
                binding.confirmationVIew.bind(SubmissionConfirmation.SUCCESS)
                answerToSubmit?.id?.let { id -> viewModel.postSubmittedId(id) }
            } else binding.confirmationVIew.bind(SubmissionConfirmation.FAIL)
            binding.submit.bind(viewModel.checkSubmittedSuccess(response))

        }
    }

    private fun setSuccessfulSubmissions() {
        binding.apply {
            val answersCounter: LiveData<Int> = mainViewModel.submissionCounting()
            answersCounter.observe(viewLifecycleOwner) { countAnswers ->
                total.text = resources.getString(R.string.submission_total, countAnswers.toString())
            }
        }
    }

    private fun clickCount() {
        binding.apply {
            val count: LiveData<Int> = viewModel.getCountTotal()
            count.observe(viewLifecycleOwner) { countSteps ->
                currentQuestionNumber.text = "$countSteps"
                handleBtnVisibility(countSteps, questionsSize)
                questionsRecycler.scrollToPosition(countSteps - 1)
                questionsRecycler.adapter?.notifyItemChanged(countSteps - 1)
                setSubmitBtn(countSteps)
            }
        }
    }

    private fun initRecyclerView(questionList: List<Question>?) {
        binding.apply {
            adapter = RecyclerAdapter(questionList, answerCallback)
            questionsRecycler.adapter = adapter
            val manager = object : CustomRecyclerManager(requireActivity()) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
            manager.orientation = LinearLayoutManager.HORIZONTAL
            questionsRecycler.layoutManager = manager
        }
    }

    private fun clickPreviousBtn() {
        binding.previousBtn.setOnClickListener {
            viewModel.getPreviousCount()
            viewModel.removeView(binding.confirmationVIew)
        }
    }

    private fun clickNextBtn() {
        binding.nextBtn.setOnClickListener {
            viewModel.getNextCount()
            viewModel.removeView(binding.confirmationVIew)
        }
    }

    private fun navigateBack() {
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_go_to_splash)
        }
    }

    private fun handleBtnVisibility(count: Int, size: Int?) {
        binding.apply {
            previousBtn.visibility = viewModel.setNavButtonVisibility(count, size, QuestionNavBtnState.PREVIOUS)
            nextBtn.visibility = viewModel.setNavButtonVisibility(count, size, QuestionNavBtnState.NEXT)
        }
    }
}
