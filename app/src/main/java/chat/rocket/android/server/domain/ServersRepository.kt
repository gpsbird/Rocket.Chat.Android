package chat.rocket.android.server.domain

import chat.rocket.android.server.domain.model.Server
import io.reactivex.Completable
import io.reactivex.Single

interface ServersRepository {
    fun servers() : Single<List<Server>>

    fun saveServer(server: Server): Completable

    fun updateServer(server: Server): Completable
}