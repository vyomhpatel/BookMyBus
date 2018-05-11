package b12app.vyom.com.bookmybus.utils.dagger2

import android.content.Context
import b12app.vyom.com.bookmybus.view.home.HomeActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules= arrayOf(TrieModule::class))
interface MyComponent{
    fun inject(homeActivity: HomeActivity)

}