package com.example.questionsApp

import NetworkResponse
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.example.questionsApp.models.AnswerToSubmit
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
import org.koin.test.KoinTest
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
@MediumTest
class MainViewModelTest : KoinTest {

    private lateinit var getQuestionsController: GetQuestionsController
    private lateinit var submitAnswerController: SubmitAnswerController
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var rule = RetryRule(5)

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(Application())
        getQuestionsController = GetQuestionsController()
        submitAnswerController = SubmitAnswerController()
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
    fun whenFetchingResultsAndPostThem() = runBlocking {
        val responseList = mainViewModel.fetchQuestions()
        mainViewModel.postQuestionsList(responseList)
        Assert.assertTrue(mainViewModel.questionResponseMutable.value?.isNotEmpty() == true)
    }

    @Test
    fun whenSubmitAnswerRetryUntilSuccess() = runBlocking {
        val mocQuestion = AnswerToSubmit(1, "some random shit")
        val request = SubmitAnswerRequest(mocQuestion)
        val response = submitAnswerController.doSuspendRequest<String?>(request)
        assertTrue { response is NetworkResponse.Success<*> }
    }


    @Test
    @Retry()
    fun whenSubmitAnswerRetryUntilFails() = runBlocking {
        val mocQuestion = AnswerToSubmit(1, "some random shit")
        val request = SubmitAnswerRequest(mocQuestion)
        val response = submitAnswerController.doSuspendRequest<String?>(request)
        assertTrue { response is NetworkResponse.Error }
    }


    @Test
    fun whenSubmitWithOutAnswer() = runBlocking {
        val mocQuestion = AnswerToSubmit(1, "")
        val request = SubmitAnswerRequest(mocQuestion)
        val response = submitAnswerController.doSuspendRequest<String?>(request)
        assertTrue { response is NetworkResponse.Error }
    }


}