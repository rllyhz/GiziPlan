package id.rllyhz.giziplan

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import id.rllyhz.giziplan.di.AppModule

class GiziPlanApplication : Application() {
    var appModule: AppModule? = null

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        appModule = AppModule(this)
    }
}