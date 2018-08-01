package com.excelian.fxserver.service

import com.excelian.fxserver.domain.BankAccount
import com.excelian.fxserver.domain.Transaction
import com.excelian.fxserver.repository.BankAccountRepository
import com.excelian.fxserver.repository.TransactionRepository
import com.excelian.fxserver.util.validate
import com.excelian.fxserver.util.validateNotNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

/**
 * Created by dtsimbal on 8/1/18.
 */
@Service
@Transactional
class TransferService(
    private val bankAccountRepository: BankAccountRepository,
    private val transactionRepository: TransactionRepository,
    private val forexService: ForexService
) {

    fun transfer(from: String, to: String, amount: BigDecimal): String {
        val bankAccounts = bankAccountRepository.findAllByBsbIn(listOf(from, to))
        val bankAccountLookup = bankAccounts.associateBy { it.bsb }

        val fromAccount = bankAccountLookup[from]
        validateNotNull(fromAccount) { "Fail to find 'From' account by provided BSB:$from" }

        val toAccount = bankAccountLookup[to]
        validateNotNull(toAccount) { "Fail to find 'To' account by provided BSB:$to" }

        return transfer(fromAccount!!, toAccount!!, amount)
    }

    fun transferInternational(from: String, to: String, amount: BigDecimal): String {
        val bankAccounts = bankAccountRepository.findAllByBicIn(listOf(from, to))
        val bankAccountLookup = bankAccounts.associateBy { it.bic }

        val fromAccount = bankAccountLookup[from]
        validateNotNull(fromAccount) { "Fail to find 'From' account by provided BIC:$from" }

        val toAccount = bankAccountLookup[to]
        validateNotNull(toAccount) { "Fail to find 'To' account by provided BIC:$to" }

        return transfer(fromAccount!!, toAccount!!, amount)
    }

    private fun transfer(from: BankAccount, to: BankAccount, amount: BigDecimal): String {
        val fromCurrency = from.currency
        val toCurrency = to.currency
        val toAmount = forexService.convert(fromCurrency, toCurrency, amount)

        validate(from.amount >= amount) { "Invalid amount value. Value cannot be greater than account amount" }

        val transaction = Transaction()
            .from(from)
            .fromAmount(amount)
            .to(to)
            .toAmount(toAmount)
            .stateDescription("Newly created transfer from ${from.bsb} to ${to.bsb} $amount ${from.currency.symbol}")

        // Updates transaction with UUID
        val uuid = randomUUID()
        transaction.uuid(uuid)
        transactionRepository.save(transaction)

        return uuid
    }

    private fun randomUUID(): String = UUID.randomUUID().toString().replace("-", "")
}
