package chat.rocket.android.server.infraestructure

import chat.rocket.android.server.domain.ServersRepository
import chat.rocket.android.server.domain.model.Server
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RoomServersRepository @Inject constructor(private val dao: ServerDao,
                                                private val mapper: ServerEntityMapper) : ServersRepository {

    override fun servers(): Single<List<Server>> {
        return dao.getServers()
                .flatMapObservable { list -> Observable.fromIterable(list) }
                .map { t: ServerEntity ->  mapper.translate(t) }
                .toList()
    }

    override fun saveServer(server: Server): Completable {
        val serverEntity = ServerEntity(server.id, server.name, server.url, server.avatar)
        return Completable.create({
            dao.insertServer(serverEntity)
            it.onComplete()
        })
    }

    override fun updateServer(server: Server): Completable {
        TODO("not implemented")
    }
}
