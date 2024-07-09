package id.rllyhz.giziplan

import android.app.Application
import id.rllyhz.giziplan.di.AppModule

class GiziPlanApplication : Application() {
    var appModule: AppModule? = null

    override fun onCreate() {
        super.onCreate()

        appModule = AppModule(this)
    }
}