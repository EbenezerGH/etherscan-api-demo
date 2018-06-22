package jfyg.etherscan.core.application

import android.app.Application
import jfyg.ApiKey

class CoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiKey.takeOff.setApiKey("93CPII4SWD12EG7RFWMKVGBV57W7M2HJ8F")
    }

}