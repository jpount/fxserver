package com.excelian.fxserver.web.rest.v1

import com.excelian.fxserver.service.ForexService
import com.excelian.fxserver.web.rest.v1.model.DictionaryResult
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
 * Created by dtsimbal on 7/24/18.
 */
@Api(value = "fx", description = "The FX API", authorizations = [Authorization(value = "BearerAuth")])
@RestController
@RequestMapping("/api/fx/v1")
class ForexApi(
    private val forexService: ForexService
) {
    @ApiOperation(value = "",
        nickname = "convert",
        notes = "Converts any amount from one currency to another",
        response = Result::class,
        tags = [])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "A converted currency result", response = Result::class),
        ApiResponse(code = 500, message = "General Error")])
    @RequestMapping(value = ["/convert"], produces = ["application/json"], method = [RequestMethod.GET])
    fun convert(
        @Valid
        @NotNull
        @ApiParam(required = true, value = "The three-letter currency code of the currency you would like to convert from",
            examples = Example(ExampleProperty(value = "USD")))
        @RequestParam(required = true, value = "from")
        from: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "The three-letter currency code of the currency you would like to convert to",
            examples = Example(ExampleProperty(value = "EUR")))
        @RequestParam(required = true, value = "to")
        to: String,

        @Valid
        @NotNull
        @ApiParam(required = true, value = "The amount to be converted",
            examples = Example(ExampleProperty(value = "120.0")))
        @RequestParam(required = true, value = "amount")
        amount: BigDecimal

    ): ResponseEntity<Result<BigDecimal>> {
        return ResponseEntity.ok(Result(true, forexService.convert(from, to, amount)))
    }

    @ApiOperation(value = "",
        nickname = "latest",
        notes = "Latest currency rates for most currencies (can be restricted with symbols parameter)",
        response = DictionaryResult::class,
        tags = [])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "A list of currency rates", response = DictionaryResult::class),
        ApiResponse(code = 500, message = "General Error")])
    @RequestMapping(value = ["/latest"], produces = ["application/json"], method = [RequestMethod.GET])
    fun latest(
        @Valid
        @ApiParam(value = "List of comma-separated currency codes to limit output currencies",
            examples = Example(ExampleProperty(value = "USD,EUR")))
        @RequestParam(value = "symbols", required = false) symbols: List<*>?): ResponseEntity<DictionaryResult<String, BigDecimal>> {
        return ResponseEntity.ok(DictionaryResult.success(forexService.latest(symbols)))
    }

    @ApiOperation(value = "",
        nickname = "symbols",
        notes = "Returns als available currencies if not restricted",
        response = DictionaryResult::class,
        tags = [])
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "A list of available currencies", response = DictionaryResult::class),
        ApiResponse(code = 500, message = "General Error")])
    @RequestMapping(value = ["/symbols"], produces = ["application/json"], method = [RequestMethod.GET])
    fun symbols(): ResponseEntity<DictionaryResult<String, String>> {
        return ResponseEntity.ok(DictionaryResult.success(forexService.symbols()))
    }

}
