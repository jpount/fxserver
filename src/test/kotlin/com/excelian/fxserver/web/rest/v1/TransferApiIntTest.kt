package com.excelian.fxserver.web.rest.v1

import com.excelian.fxserver.FxserverApp
import com.excelian.fxserver.domain.BankAccount
import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.service.BankAccountService
import com.excelian.fxserver.service.CurrencyService
import com.excelian.fxserver.service.TransferService
import com.excelian.fxserver.web.rest.TestUtil
import com.excelian.fxserver.web.rest.errors.ExceptionTranslator
import org.hamcrest.Matchers
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * Test TransferApi calls
 *
 * Created by dtsimbal on 8/1/18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [FxserverApp::class])
class TransferApiIntTest {

    @Autowired
    private lateinit var currencyService: CurrencyService

    @Autowired
    private lateinit var bankAccountService: BankAccountService

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Autowired
    private lateinit var transferService: TransferService

    // Local props below
    private lateinit var restFxMockMvc: MockMvc

    private lateinit var bankAccount1: BankAccount

    private lateinit var bankAccount2: BankAccount

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val fxApi = TransferApi(transferService)
        this.restFxMockMvc = MockMvcBuilders.standaloneSetup(fxApi)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(TestUtil.createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun setupTestData() {
        //USD;US dollar;1.168196
        val currency1 = Currency()
            .symbol("ONE")
            .name("Test 1")
            .rate(BigDecimal.valueOf(2.0))
        currencyService.save(currency1)

        //EUR;Euro;1.000000
        val currency2 = Currency()
            .symbol("TWO")
            .name("Test 2")
            .rate(BigDecimal.ONE)
        currencyService.save(currency2)

        bankAccount1 = BankAccount()
            .bsb("001001")
            .bic("ACCAUXX1")
            .number("123456")
            .currency(currency1)
            .amount(BigDecimal.valueOf(100.0))
        bankAccountService.save(bankAccount1)

        bankAccount2 = BankAccount()
            .bsb("002002")
            .bic("ACCAUXX2")
            .number("123456")
            .currency(currency2)
            .amount(BigDecimal.valueOf(100.0))
        bankAccountService.save(bankAccount2)
    }

    @Test
    @Transactional
    fun `test local transfer is created`() {
        // Builds a request
        val convertRequest = MockMvcRequestBuilders.get("/api/transfer/v1/local").apply {
            param("fromBsb", bankAccount1.bsb)
            param("from", bankAccount1.number)
            param("toBsb", bankAccount2.bsb)
            param("to", bankAccount2.number)
            param("amount", 10.0.toString())
            accept(TestUtil.APPLICATION_JSON_UTF8)
        }

        // Get the conversion
        restFxMockMvc.perform(convertRequest)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(Matchers.not(Matchers.isEmptyString())))

    }

    @Test
    @Transactional
    fun `test international transfer is created`() {
        // Builds a request
        val convertRequest = MockMvcRequestBuilders.get("/api/transfer/v1/international").apply {
            param("fromBic", bankAccount1.bic)
            param("from", bankAccount1.number)
            param("toBic", bankAccount2.bic)
            param("to", bankAccount2.number)
            param("amount", 10.0.toString())
            accept(TestUtil.APPLICATION_JSON_UTF8)
        }

        // Get the conversion
        restFxMockMvc.perform(convertRequest)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(Matchers.`is`(true)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(Matchers.not(Matchers.isEmptyString())))

    }

    @Test
    @Transactional
    fun `test system rejects a transfer if it's amount greater than amount of 'From' account`() {
        // Builds a request
        val convertRequest = MockMvcRequestBuilders.get("/api/transfer/v1/local").apply {
            param("fromBsb", bankAccount1.bsb)
            param("from", bankAccount1.number)
            param("toBsb", bankAccount2.bsb)
            param("to", bankAccount2.number)
            param("amount", Int.MAX_VALUE.toString())
            accept(TestUtil.APPLICATION_JSON_UTF8)
        }

        // Get the conversion
        restFxMockMvc.perform(convertRequest)
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

}
