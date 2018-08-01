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

    fun transferInternational(fromBic: String, from: String, toBic: String, to: String, amount: BigDecimal): String {
        val bankAccounts = bankAccountRepository.findAllByBicInAndNumberIn(listOf(fromBic, toBic), listOf(from, to))
        val bankAccountLookup = bankAccounts.associateBy { it.bic + it.number }

        val fromAccount = bankAccountLookup[fromBic + from]
        validateNotNull(fromAccount) { "Fail to find 'From' account by provided BIC:$fromBic, #:$from" }

        val toAccount = bankAccountLookup[toBic + to]
        validateNotNull(toAccount) { "Fail to find 'To' account by provided BIC:$toBic, #:$to" }

        return transfer(fromAccount!!, toAccount!!, amount)
    }

    fun transferLocal(fromBsb: String, from: String, toBsb: String, to: String, amount: BigDecimal): String {
        val bankAccounts = bankAccountRepository.findAllByBsbInAndNumberIn(listOf(fromBsb, toBsb), listOf(from, to))
        val bankAccountLookup = bankAccounts.associateBy { it.bsb + it.number }

        val fromAccount = bankAccountLookup[fromBsb + from]
        validateNotNull(fromAccount) { "Fail to find 'From' account by provided BSB:$fromBsb, #:$from" }

        val toAccount = bankAccountLookup[toBsb + to]
        validateNotNull(toAccount) { "Fail to find 'To' account by provided BSB:$toBsb, #:$to" }

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
