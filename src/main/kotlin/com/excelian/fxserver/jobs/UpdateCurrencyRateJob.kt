package com.excelian.fxserver.jobs

import com.excelian.fxserver.service.UpdateCurrencyRateService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.scheduling.quartz.JobDetailFactoryBean
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

/**
 * Periodically updates currency rates in the system
 *
 * Created by dtsimbal on 7/24/18.
 */
@Component
@DisallowConcurrentExecution
class UpdateCurrencyRateJob : QuartzJobBean() {

    companion object {
        @JvmStatic
        private val TRIGGER_CRON_EXP = "0 0 * * * ?"

        @JvmStatic
        private val TRIGGER_NAME = "updateCurrencyRateTrigger"

        @JvmStatic
        private val JOB_NAME = "updateCurrencyRateJob"

        @JvmStatic
        private val log = LoggerFactory.getLogger(UpdateCurrencyRateJob::class.java)
    }

    @Autowired
    lateinit var updateCurrencyRateService: UpdateCurrencyRateService

    override fun executeInternal(executionContext: JobExecutionContext?) {
        log.info("Runnig ${UpdateCurrencyRateJob}...")
        updateCurrencyRateService.updateCurrencyRates()
    }

    @Bean
    fun jobDetail(): JobDetailFactoryBean {
        return JobDetailFactoryBean().apply {
            setJobClass(UpdateCurrencyRateJob::class.java)
            setDurability(true)
            setName(JOB_NAME)
        }
    }

    @Bean
    fun jobTrigger(jobDetail: JobDetail): CronTriggerFactoryBean {
        return CronTriggerFactoryBean().apply {
            setJobDetail(jobDetail)
            setCronExpression(TRIGGER_CRON_EXP)
            setName(TRIGGER_NAME)
        }
    }

}
