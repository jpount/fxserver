package com.excelian.fxserver.service

import com.excelian.fxserver.domain.Currency
import com.excelian.fxserver.repository.CurrencyRepository
import com.excelian.fxserver.web.rest.errors.InvalidArgumentException
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.math.BigDecimal

/**
 * Created by dtsimbal on 7/23/18.
 */
class ForexServiceTest {

    companion object {
        @JvmStatic
        private val currencies = listOf(
            Currency().symbol("RUB").name("Russian ruble").rate(BigDecimal.valueOf(73.952580)),
            Currency().symbol("USD").name("US dollar").rate(BigDecimal.valueOf(1.168196)),
            Currency().symbol("EUR").name("Euro").rate(BigDecimal.valueOf(1.000000)))

        @JvmStatic
        private val currencyRepository = Mockito.mock(CurrencyRepository::class.java)!!

        @JvmStatic
        private val fxService = ForexService(currencyRepository)

        init {
            `when`(currencyRepository.findAll()).thenReturn(currencies)
        }
    }

    @Test
    fun `test currency conversion to base currency`() {
        val amount = BigDecimal(100)
        val expected = BigDecimal.valueOf(85.6)
        val result = fxService.convert("USD", "EUR", amount)

        assertEquals(expected, result)
    }

    @Test
    fun `test currency conversion to non-base currency`() {
        val amount = BigDecimal(100)
        val expected = BigDecimal.valueOf(1.5797)
        val result = fxService.convert("RUB", "USD", amount)

        assertEquals(expected, result)
    }

    @Test
    fun `test latest rates filtering`() {
        val expected = "USD"
        val other = "EUR"

        val symbols = listOf(expected)
        val result = fxService.latest(symbols)
        val actual = result

        assertTrue(actual.keys.contains(expected))
        assertFalse(actual.keys.contains(other))
    }

    @Test
    fun `test currency conversion argument validation`() {
        assertThatExceptionOfType(InvalidArgumentException::class.java)
            .isThrownBy { fxService.convert("1.0", "2.0", BigDecimal.valueOf(1.0)) }
    }

}
