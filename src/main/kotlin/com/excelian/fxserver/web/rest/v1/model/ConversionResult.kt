package com.excelian.fxserver.web.rest.v1.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import javax.validation.constraints.NotNull

/**
 * Created by dtsimbal on 7/24/18.
 */
data class ConversionResult(

    @JsonProperty("success")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    var success: Boolean,

    @JsonProperty("value")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    var value: BigDecimal

)

