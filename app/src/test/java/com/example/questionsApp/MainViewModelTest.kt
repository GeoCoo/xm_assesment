package com.example.questionsApp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.network.NetworkResponse
import com.example.questionsApp.network.ResponseStatus
import com.example.questionsApp.network.controllers.GetQuestionsController
import com.example.questionsApp.network.controllers.SubmitAnswerController
import com.example.questionsApp.network.requests.QuestionsRequest
import com.example.questionsApp.network.requests.SubmitAnswerRequest
import com.example.questionsApp.rules.CoroutineTestRule
import com.example.questionsApp.rules.Retry
import com.example.questionsApp.rules.RetryRule
import com.example.questionsApp.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@RunWith(JUnit4::class)
@MediumTest
class MainViewModelTest {

    private lateinit var getQuestionsController: GetQuestionsController
    private lateinit var submitAnswerController: SubmitAnswerController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockAnswer: AnswerToSubmit
    private var counter: Int? = null

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule = RetryRule(6)

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(Application())
        getQuestionsController = GetQuestionsController()
        submitAnswerController = SubmitAnswerController()
        mockAnswer = AnswerToSubmit(1, "some random shit")
        counter = 0

    }


    @Test
    fun whenFetchingResultsSuccess() = runBlocking {
        val response = getQuestionsController.doSuspendRequest<ArrayList<Any>?>(QuestionsRequest())
        assertNotNull(response)
        Assert.assertTrue(response is NetworkResponse.Success<*>)
    }

    @Test
    fun whenFetchingResultsList() = runBlocking {
        val response = mainViewModel.fetchQuestions()
        assertNotNull(response)
        assertTrue { response is ArrayList<Any> }
    }


    @Test
    @Retry
    fun whenFetchingResultsAndPostThem() = runBlocking {
        val responseList = mainViewModel.fetchQuestions()
        mainViewModel.postQuestionsList(responseList)
        Assert.assertTrue(mainViewModel.questionResponseMutable.value?.isNotEmpty() == true)
    }

    @Test
    @Retry
    fun whenSubmitAnswerRetryUntilSuccess() = runBlocking {
        val request = SubmitAnswerRequest(mockAnswer)
        val response = submitAnswerController.doSuspendRequest<String?>(request)
        assertTrue { response is NetworkResponse.Success<*> }
    }

    @Test
    @Retry
    fun whenSubmitAnswerRetryUntilFails() = runBlocking {
        val request = SubmitAnswerRequest(mockAnswer)
        val response = submitAnswerController.doSuspendRequest<String?>(request)
        assertNotNull(response)
        assertTrue { response is NetworkResponse.Error }
    }

    @Test
    @Retry
    fun whenPostAnswerToSubmit() = runBlocking {
        mainViewModel.postSubmittedAnswer(mockAnswer)
        assertNotNull(mainViewModel.submittedAnswerMutable.value)
        assertTrue { mainViewModel.submittedAnswerMutable.value?.answer?.isNotEmpty() == true }
    }

    @Test
    @Retry
    fun whenPostSuccessfulSubmit() = runBlocking {
        val response = mainViewModel.submitAnswer(mockAnswer)
        val responseStatus = mainViewModel.postSubmissionResponse(response)
        assertNotNull(responseStatus)
        assertTrue { mainViewModel.submissionResponseMutable.value == ResponseStatus.OK.code.toString() }
    }

    @Test
    fun whenPostSuccessfulSubmitCounting() = runBlocking {
        val response = mainViewModel.submitAnswer(mockAnswer)
        mainViewModel.postSubmissionResponse(response)
        mainViewModel.addToSubmissionCounter()
        assertTrue { mainViewModel.submissionCounterMutable.value == counter!! + 1 }
    }
}


