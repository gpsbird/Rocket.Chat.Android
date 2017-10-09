package chat.rocket.android.dagger.module

import chat.rocket.android.dagger.scope.PerActivity
import chat.rocket.android.server.presentation.ServersPresenter
import chat.rocket.android.server.ui.ServersActivity
import dagger.Module
import dagger.Provides

@Module
open class ServersActivityModule {

    @PerActivity
    @Provides
    internal fun provideServersView(activity: ServersActivity) : ServersPresenter.View {
        return activity
    }

    @PerActivity
    @Provides
    internal fun provideServersPresenter(): ServersPresenter {
        return ServersPresenter()
    }
}
