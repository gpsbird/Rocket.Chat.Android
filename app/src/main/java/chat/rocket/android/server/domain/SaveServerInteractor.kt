package chat.rocket.android.server.domain

import chat.rocket.android.dagger.qualifier.IOScheduler
import chat.rocket.android.server.domain.model.Server
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class SaveServerInteractor @Inject
constructor(private val repository: ServersRepository,
            @param:IOScheduler private val executionScheduler: Scheduler) {

    fun saveServer(server: Server): Completable {
        return repository.saveServer(server).subscribeOn(executionScheduler)
    }
}
