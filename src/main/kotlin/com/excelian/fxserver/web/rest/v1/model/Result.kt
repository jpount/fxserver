package com.excelian.fxserver.web.rest.v1.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

/**
 * Created by dtsimbal on 8/1/18.
 */
data class Result<V>(
    @JsonProperty("success")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    val success: Boolean,

    @JsonProperty("value")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    val value: V
) {
    companion object {
        @JvmStatic
        fun <V> success(value: V): Result<V> = Result(true, value)
    }
}

