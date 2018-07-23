package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import org.assertj.core.api.Assertions.assertThatExceptionOfType
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
            Currency().symbol("USD").name("US dollar").rate(BigDecimal.valueOf(1.0)),
            Currency().symbol("GBP").name("GB pound").rate(BigDecimal.valueOf(1.31)),
            Currency().symbol("EUR").name("Euro").rate(BigDecimal.valueOf(1.17)))

        @JvmStatic
        val currencyRepository = Mockito.mock(CurrencyRepository::class.java)!!

        @JvmStatic
        val currencyService = CurrencyService(currencyRepository)

        init {
            `when`(currencyRepository.findAll()).thenReturn(currencies)
        }
    }

    @Test
    fun `test currency conversion to USD`() {
        val amount = BigDecimal(1)
        val expected = BigDecimal.valueOf(1.31)
        val result = currencyService.apiV1ConvertGet("GBP", "USD", amount)

        assertEquals(expected, result.body.value)
    }

    @Test
    fun `test currency conversion to non-USD`() {
        val amount = BigDecimal(8)
        val expected = BigDecimal.valueOf(8.96)
        val result = currencyService.apiV1ConvertGet("GBP", "EUR", amount)

        assertEquals(expected, result.body.value)
    }

    @Test
    fun `test latest rates filtering`() {
        val expected = "GBP"
        val other = "EUR"

        val symbols = listOf(expected)
        val result = currencyService.apiV1LatestGet(symbols)
        val actual = result.body.value as Map<String, BigDecimal>

        assertTrue(actual.keys.contains(expected))
        assertFalse(actual.keys.contains(other))
    }

    @Test
    fun `test currency conversion argument validation`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { currencyService.apiV1ConvertGet("1.0", "2.0", BigDecimal.valueOf(1.0)) }
    }

}