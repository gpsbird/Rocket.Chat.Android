package chat.rocket.android.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import chat.rocket.android.server.ui.ServersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ServersActivityModule::class))
    abstract fun bindServersActivity(): ServersActivity
}
