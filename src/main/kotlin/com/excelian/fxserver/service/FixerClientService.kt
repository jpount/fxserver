package com.excelian.fxserver.service

import com.excelian.fxserver.util.withCompletableFuture
import io.vertx.core.Vertx
import io.vertx.ext.web.client.HttpRequest
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.codec.BodyCodec
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct

/**
 * Provides limited Fixer.io API implementation
 *
 * Created by dtsimbal on 7/25/18.
 */
@Service
class FixerClientService {

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(FixerClientService::class.java)
        private const val DEFAULT_HOST = "http://data.fixer.io"
    }

    @Value("\${io.fixer.api.accessKey}")
    private lateinit var accessKey: String

    private lateinit var latestRatesRequest: HttpRequest<LatestRatesFixerResponse>

    private lateinit var symbolsRequest: HttpRequest<SymbolsFixerResponse>

    @PostConstruct
    fun init() {
        val options = WebClientOptions().apply {
            isKeepAlive = false
        }

        val client = WebClient.create(Vertx.vertx(), options)

        latestRatesRequest = client.buildRequest("/api/latest", accessKey,
            LatestRatesFixerResponse::class.java)

        symbolsRequest = client.buildRequest("/api/symbols", accessKey,
            SymbolsFixerResponse::class.java)
    }

    /**
     * Performs /api/latest call
     */
    fun fetchLatestRates(): CompletableFuture<LatestRatesFixerResponse> = sendRequest(latestRatesRequest)

    /**
     * Performs /api/latest call
     */
    fun fetchSymbols(): CompletableFuture<SymbolsFixerResponse> = sendRequest(symbolsRequest)

    /**
     * Builds HTTP request for Vertx WebClient
     */
    private fun <T : FixerResponse> WebClient.buildRequest(path: String, accessKey: String, clazz: Class<T>): HttpRequest<T> =
        getAbs("$DEFAULT_HOST$path")
            .addQueryParam("access_key", accessKey)
            .`as`(BodyCodec.json(clazz))

    /**
     * Wraps Fixer.io interaction into CompletableFuture
     */
    private fun <T : FixerResponse> sendRequest(request: HttpRequest<T>): CompletableFuture<T> =
        withCompletableFuture<T> { future ->
            request.send {
                if (it.succeeded()) {
                    future.complete(it.result().body())
                } else {
                    future.completeExceptionally(it.cause())
                }
            }
        }

}


class LatestRatesFixerResponse : FixerResponse {
    override var success: Boolean? = false
    override var timestamp: Long? = 0

    lateinit var rates: Map<String, BigDecimal>
    lateinit var date: String
    lateinit var base: String
}

class SymbolsFixerResponse : FixerResponse {
    override var success: Boolean? = false
    override var timestamp: Long? = 0

    lateinit var symbols: Map<String, String>
}

interface FixerResponse {
    val success: Boolean?
    val timestamp: Long?
}

