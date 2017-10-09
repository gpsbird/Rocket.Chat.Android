package chat.rocket.android.server.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import chat.rocket.android.R
import chat.rocket.android.server.presentation.ServersPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class ServersActivity : AppCompatActivity(), ServersPresenter.View {

    @Inject lateinit var presenter: ServersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servers)
        AndroidInjection.inject(this)
    }
}
