package com.excelian.fxserver.web.rest.v1

import com.excelian.fxserver.FxserverApp
import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.service.CurrencyService
import com.excelian.fxserver.service.FxService
import com.excelian.fxserver.web.rest.TestUtil
import com.excelian.fxserver.web.rest.TestUtil.createFormattingConversionService
import com.excelian.fxserver.web.rest.errors.ExceptionTranslator
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * Test FxApi methods
 *
 * Created by dtsimbal on 7/27/18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [FxserverApp::class])
class FxApiIntTest {

    @Autowired
    private lateinit var currencyService: CurrencyService

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Autowired
    private lateinit var fxService: FxService

    // Local props below
    private lateinit var restFxMockMvc: MockMvc

    private lateinit var currency1: Currency

    private lateinit var currency2: Currency

    private lateinit var currency3: Currency

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val fxApi = FxApi(fxService)
        this.restFxMockMvc = MockMvcBuilders.standaloneSetup(fxApi)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        //USD;US dollar;1.168196
        currency1 = Currency()
            .symbol("ONE")
            .name("Test 1")
            .rate(BigDecimal.valueOf(2.0))
        currencyService.save(currency1)

        //EUR;Euro;1.000000
        currency2 = Currency()
            .symbol("TWO")
            .name("Test 2")
            .rate(BigDecimal.ONE)
        currencyService.save(currency2)

        currency3 = Currency()
            .symbol("TRE")
            .name("Test 3")
            .rate(BigDecimal.valueOf(0.5))
        currencyService.save(currency3)
    }

    @Test
    @Transactional
    fun `test currency conversion is accurate`() {
        // Builds a request
        val convertRequest = get("/api/fx/v1/convert").apply {
            param("from", currency1.symbol)
            param("to", currency2.symbol)
            param("amount", 120.0.toString())
            accept(TestUtil.APPLICATION_JSON_UTF8)
        }

        // Get the conversion
        restFxMockMvc.perform(convertRequest)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(`is`(true)))
            .andExpect(jsonPath("$.value").value(`is`(60.0)))

    }

    @Test
    @Transactional
    fun `test latest currency rates gets filtered when list of symbols is provided`() {
        // Builds a request
        val latestRatesRequest = get("/api/fx/v1/latest").apply {
            param("symbols", listOf(currency1.symbol, currency2.symbol).joinToString())
        }

        restFxMockMvc.perform(latestRatesRequest)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(`is`(true)))
            .andExpect(jsonPath("$.value").value(hasKey(currency1.symbol)))
            .andExpect(jsonPath("$.value").value(hasKey(currency2.symbol)))
            .andExpect(jsonPath("$.value").value(not(hasKey(currency3.symbol))))

        //currency2.symbol to currency2.rate
    }

    @Test
    @Transactional
    fun `test currency symbols returns accurate result`() {
        // Builds a request
        val symbolsRequest = get("/api/fx/v1/symbols")

        restFxMockMvc.perform(symbolsRequest)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.value").value(hasKey(currency1.symbol)))
            .andExpect(jsonPath("$.value").value(hasKey(currency2.symbol)))
            .andExpect(jsonPath("$.value").value(hasKey(currency3.symbol)))
    }

}
