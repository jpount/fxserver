package com.excelian.fxserver.web.rest.v1.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

/**
 * Created by dtsimbal on 7/24/18.
 */
data class DictionaryResult<K, V>(
    @JsonProperty("success")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    val success: Boolean,

    @JsonProperty("value")
    @ApiModelProperty(required = true, value = "")
    @NotNull
    val value: Map<K, V>

) {
    companion object {
        @JvmStatic
        fun <K, V> success(value: Map<K, V>): DictionaryResult<K, V> = DictionaryResult(true, value)
    }
}
