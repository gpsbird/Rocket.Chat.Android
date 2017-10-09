package chat.rocket.android.server.domain

import chat.rocket.android.dagger.qualifier.IOScheduler
import chat.rocket.android.server.domain.model.Server
import io.reactivex.Flowable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetServersInteractor @Inject
constructor(private val repository: ServersRepository,
            @param:IOScheduler private val executionScheduler: Scheduler) {

    fun getServers() : Flowable<Server> {
        return repository.servers()
                .toFlowable()
                .flatMap { servers -> Flowable.fromIterable(servers) }
    }
}
