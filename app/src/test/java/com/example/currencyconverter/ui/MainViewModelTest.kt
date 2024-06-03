package com.example.currencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import com.example.currencyconverter.data.repository.CurrencyRepo
import com.example.currencyconverter.util.DispatcherProvider
import com.example.currencyconverter.util.TestDefaultDispatcher
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repoTest: CurrencyRepo
    private lateinit var mainViewModelTest: MainViewModel

    @Mock
    private lateinit var currencyResponseObserver: Observer<Resource<CurrencyListResponse>>
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun start() {
        dispatcherProvider = TestDefaultDispatcher()
        Dispatchers.setMain(Dispatchers.Unconfined)
        mainViewModelTest = MainViewModel(repoTest, dispatcherProvider)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainViewModelTest.currencyListResponse.removeObserver(currencyResponseObserver)
    }

    @Test
    fun `when fetching results ok then return a response successfully`() {
        val emptyResponse = CurrencyListResponse()
        runBlocking {
            mainViewModelTest.currencyListResponse.observeForever(currencyResponseObserver)
            doReturn(flowOf(Resource.Success(emptyResponse))).`when`(repoTest)
                .getLatestCurrencyResponse()
            mainViewModelTest.getCurrencyListResponse()
            assertEquals(
                Resource.Success(emptyResponse),
                mainViewModelTest.currencyListResponse.value
            )
        }
    }


    @Test
    fun `when fetching results fails then return an error`() {
        runBlocking {
            mainViewModelTest.currencyListResponse.observeForever(currencyResponseObserver)
            doReturn(flowOf(Resource.Error<CurrencyListResponse>("Something went wrong"))).`when`(repoTest)
                .getLatestCurrencyResponse()
            mainViewModelTest.getCurrencyListResponse()
            assertEquals(
                Resource.Error<CurrencyListResponse>("Something went wrong"),
                mainViewModelTest.currencyListResponse.value
            )
        }
    }
}