package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.math.BigDecimal

/**
 * Created by dtsimbal on 7/23/18.
 */
class CurrencyServiceTest {

    companion object {
        @JvmStatic
        val currencies = listOf(
            Currency().symbol("GBP").name("GB pound").rate(BigDecimal.valueOf(1.0)),
            Currency().symbol("EUR").name("Euro").rate(BigDecimal.valueOf(0.5)))

        @JvmStatic
        val currencyRepository = Mockito.mock(CurrencyRepository::class.java)!!

        @JvmStatic
        val currencyService = CurrencyService(currencyRepository)

        init {
            `when`(currencyRepository.findAll()).thenReturn(currencies)
        }
    }

    @Test
    fun testConversionCalculation() {
        val amount = BigDecimal(1)
        val expected = BigDecimal.valueOf(2.0)
        val result = currencyService.convertGet("GBP", "EUR", amount)

        assertEquals(expected, result.body.value)
    }

    @Test
    fun testLatestRatesFiltering() {
        val expected = "GBP"
        val other = "EUR"

        val symbols = listOf(expected)
        val result = currencyService.latestGet(symbols)
        val actual = result.body.value as Map<String, BigDecimal>

        assertTrue(actual.keys.contains(expected))
        assertFalse(actual.keys.contains(other))
    }

}
