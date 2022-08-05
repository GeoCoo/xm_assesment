package com.example.questionsApp

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.rules.CoroutineTestRule
import com.example.questionsApp.rules.Retry
import com.example.questionsApp.rules.RetryRule
import com.example.questionsApp.utils.ButtonStates
import com.example.questionsApp.utils.QuestionNavBtnState
import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class QuestionViewModelTest {
    private lateinit var viewModel: QuestionViewModel
    private lateinit var mainViewModel: MainViewModel
    private var counter: Int? = null
    private var mockSubmittedIdList: List<Int>? = null
    private lateinit var mockAnswer: AnswerToSubmit

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule = RetryRule(6)

    @Before
    fun setUp() {
        counter = 1
        viewModel = QuestionViewModel(Application())
        mainViewModel = MainViewModel(Application())
        mockAnswer = AnswerToSubmit(1, "some random shit")
    }

    @Test
    fun addCounterOnNext() {
        viewModel.getNextCount()
        assertNotNull(viewModel.counterMutable.value)
        assertTrue { viewModel.counterMutable.value == counter!! + 1 }
    }

    @Test
    fun removeCounterOnPrevious() {
        viewModel.getPreviousCount()
        assertNotNull(viewModel.counterMutable.value)
        assertTrue { viewModel.counterMutable.value == counter!! - 1 }
    }

    @Test
    fun checkNextBtnVisible() {
        val btnState = viewModel.setNavButtonVisibility(1, 10, QuestionNavBtnState.NEXT)
        assertNotNull(btnState)
        assertTrue { btnState == View.VISIBLE }
    }

    @Test
    fun checkNextBtnNotVisible() {
        val btnState = viewModel.setNavButtonVisibility(10, 10, QuestionNavBtnState.NEXT)
        assertNotNull(btnState)
        assertFalse { btnState == View.VISIBLE }
    }

    @Test
    fun checkPreviousBtnNotVisible() {
        val btnState = viewModel.setNavButtonVisibility(1, 10, QuestionNavBtnState.PREVIOUS)
        assertNotNull(btnState)
        assertFalse { btnState == View.VISIBLE }
    }

    @Test
    fun checkPreviousBtnVisible() {
        val btnState = viewModel.setNavButtonVisibility(10, 10, QuestionNavBtnState.PREVIOUS)
        assertNotNull(btnState)
        assertFalse { btnState == View.GONE }
    }

    @Test
    fun checkPreviousBtnIfCountOutOfRange() {
        val btnState = viewModel.setNavButtonVisibility(11, 10, QuestionNavBtnState.PREVIOUS)
        assertNotNull(btnState)
        assertFalse { btnState == View.VISIBLE }
    }

    @Test
    fun checkNextBtnIfCountOutOfRange() {
        val btnState = viewModel.setNavButtonVisibility(0, 10, QuestionNavBtnState.PREVIOUS)
        assertNotNull(btnState)
        assertFalse { btnState == View.VISIBLE }
    }

    @Test
    fun checkPostSubmittedId() {
        viewModel.postSubmittedId(1)
        assertNotNull(viewModel.submittedIdsMutable.value)
        assertTrue { viewModel.submittedIdsMutable.value == listOf(1) }
    }

    @Test
    fun checkListSubmittedId() {
        setMockList()
        val buttonState = viewModel.checkIfSubmitted(1)
        assertNotNull(viewModel.submittedIdsMutable.value)
        assertTrue { buttonState == ButtonStates.ALREADY_SUBMITTED }
    }

    @Test
    fun checkListIfNotSubmittedId() {
        setMockList()
        val buttonState = viewModel.checkIfSubmitted(3)
        assertNotNull(viewModel.submittedIdsMutable.value)
        assertFalse { buttonState == ButtonStates.ALREADY_SUBMITTED }
    }

    @Test
    @Retry
    fun checkSubmitteUntidSuccess() = runBlocking {
        val response = mainViewModel.submitAnswer(mockAnswer)
        mainViewModel.postSubmissionResponse(response)
        val status = mainViewModel.submissionResponseMutable.value
        assertNotNull(status)
        assertTrue { viewModel.checkSubmittedSuccess(status) == ButtonStates.SUBMITTED_SUCCESS }
    }

    @Test
    @Retry
    fun checkSubmittedUntilFails() = runBlocking {
        val response = mainViewModel.submitAnswer(mockAnswer)
        mainViewModel.postSubmissionResponse(response)
        val status = mainViewModel.submissionResponseMutable.value
        assertNotNull(status)
        assertFalse { viewModel.checkSubmittedSuccess(status) == ButtonStates.SUBMIT }
    }

    private fun setMockList() {
        mockSubmittedIdList = listOf(1, 2, 4)
        mockSubmittedIdList?.forEach {
            viewModel.postSubmittedId(it)
        }
    }
}