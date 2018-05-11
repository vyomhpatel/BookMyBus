package b12app.vyom.com.bookmybus.utils.dagger2

import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.utils.Trie
import dagger.Module
import dagger.Provides

@Module
class TrieModule {
    @Provides
    fun getTrie(): Trie<City.CityBean> {
       return Trie<City.CityBean>()
    }
}