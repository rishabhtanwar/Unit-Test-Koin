package com.example.currencyconverter.data

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.data.model.Resource
import com.example.currencyconverter.data.repository.CurrencyRepo
import com.example.currencyconverter.di.configureTestAppModules
import com.example.currencyconverter.util.MockWebServerBaseTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class CurrencyRepoTest : MockWebServerBaseTest(), KoinTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val repoTest: CurrencyRepo by inject()

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        super.setUp()
        val appContext = mock(Context::class.java)
        val sharedPrefs = mock(SharedPreferences::class.java)
        `when`(appContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)

        startKoin {
            modules(configureTestAppModules(getMockWebServerUrl()))
            androidContext(appContext)
        }
        loadKoinModules(
            module {
                single { sharedPrefs }
            }
        )
    }

    @After
    fun after() {
        stopKoin()
        super.tearDown()
    }

    @Test
    fun `given response ok when fetching results`() {
        runBlocking {
            mockHttpResponse("json/success_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = repoTest.getLatestCurrencyResponse()
            assertNotNull(apiResponse)
            val listSize:Int = ((apiResponse.drop(1).first().extractData?.rates?.size ?:0))

            assertTrue("size = $listSize",listSize>0)
        }
    }

    @Test
    fun `given response error when fetching results`() {
        runBlocking {
            mockHttpResponse("json/error_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = repoTest.getLatestCurrencyResponse()
            assertNotNull(apiResponse)
            val listSize:Int = ((apiResponse.drop(1).first().extractData?.rates?.size ?:0))

            assertTrue("size = $listSize",listSize==0)
        }
    }

}