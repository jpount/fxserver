package com.excelian.fxserver.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * A static plain application context provider. Just a helper class
 *
 * Created by dtsimbal on 7/25/18.
 */
@Component
class Holder : ApplicationContextAware {

    companion object {
        @Volatile
        @JvmStatic
        lateinit var context: ApplicationContext
    }

    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        if (applicationContext != null) {
            context = applicationContext
        }
    }

}
