package com.excelian.fxserver.web.rest.v1

import com.excelian.fxserver.service.TransferService
import com.excelian.fxserver.web.rest.v1.model.Result
import io.swagger.annotations.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Created by dtsimbal on 30/7/18.
 */
@Api(value = "transfer", description = "The Transfer API", authorizations = [(Authorization(value = "BearerAuth"))])
@RestController
@RequestMapping("/api/transfer/v1")
class TransferApi(
    private val transferService: TransferService
) {

    @ApiOperation(value = "",
        nickname = "local",
        notes = "Creates a money transfer transaction in CREATED state with provided bank accounts data and amount",
        response = Result::class,
        tags = [])
    @ApiResponses(value = [
        (ApiResponse(code = 200, message = "Transaction UUID", response = Result::class)),
        (ApiResponse(code = 500, message = "General Error"))])
    @RequestMapping(value = ["/local"], produces = ["application/json"], method = [RequestMethod.GET])
    fun transfer(
        @Valid
        @NotNull
        @ApiParam(required = true, value = "6-digit BSB number to transfer money from",
            examples = Example(ExampleProperty(value = "062004")))
        @RequestParam(required = true, value = "fromBsb")
        fromBsb: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "Account number in the specified 'From' branch to transfer money from",
            examples = Example(ExampleProperty(value = "123456")))
        @RequestParam(required = true, value = "from")
        from: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "6-digit BSB number to transfer money to",
            examples = Example(ExampleProperty(value = "023005")))
        @RequestParam(required = true, value = "toBsb")
        toBsb: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "account number in the specified 'To' branch to transfer monesy to",
            examples = Example(ExampleProperty(value = "123456")))
        @RequestParam(required = true, value = "to")
        to: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "Amount of money to transfer",
            examples = Example(ExampleProperty(value = "123.45")))
        @RequestParam(required = true, value = "amount")
        amount: BigDecimal

    ): ResponseEntity<Result<String>> = ResponseEntity.ok(
        Result.success(transferService.transferLocal(fromBsb, from, toBsb, to, amount)))


    @ApiOperation(value = "",
        nickname = "international",
        notes = "Creates a money transfer transaction in CREATED state with provided bank accounts data and amount",
        response = Result::class,
        tags = [])
    @ApiResponses(value = [
        (ApiResponse(code = 200, message = "Transaction UUID", response = Result::class)),
        (ApiResponse(code = 500, message = "General Error"))])
    @RequestMapping(value = ["/international"], produces = ["application/json"], method = [RequestMethod.GET])
    fun transferInternational(
        @Valid
        @NotNull
        @ApiParam(required = true, value = "8-digit BIC number to transfer money from",
            examples = Example(ExampleProperty(value = "CTBAAU2S")))
        @RequestParam(required = true, value = "fromBic")
        fromBic: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "Account number in the specified 'From' branch to transfer money from",
            examples = Example(ExampleProperty(value = "123456")))
        @RequestParam(required = true, value = "from")
        from: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "8-digit BIC number to transfer money from",
            examples = Example(ExampleProperty(value = "CITIAUSX")))
        @RequestParam(required = true, value = "toBic")
        toBic: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "Account number in the specified 'To' branch to transfer money to",
            examples = Example(ExampleProperty(value = "123456")))
        @RequestParam(required = true, value = "to")
        to: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "Amount of money to transfer",
            examples = Example(ExampleProperty(value = "123.45")))
        @RequestParam(required = true, value = "amount")
        amount: BigDecimal

    ): ResponseEntity<Result<String>> = ResponseEntity.ok(
        Result.success(transferService.transferInternational(fromBic, from, toBic, to, amount)))

}
