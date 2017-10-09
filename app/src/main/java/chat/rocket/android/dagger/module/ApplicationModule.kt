package chat.rocket.android.dagger.module

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import chat.rocket.android.BuildConfig
import chat.rocket.android.app.RocketChatDatabase
import chat.rocket.android.dagger.qualifier.IOScheduler
import chat.rocket.android.dagger.qualifier.UiScheduler
import chat.rocket.android.server.infraestructure.ServerDao
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRocketChatDatabase(context: Application): RocketChatDatabase {
        return Room.databaseBuilder(context,
                RocketChatDatabase::class.java, "rocketchat-db")
                .addCallback(SERVER_CREATION_CALLBACK)
                .build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideServerDao(database: RocketChatDatabase): ServerDao {
        return database.serverDao()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptorLevel() : HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
            HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideHttpLoggingInterceptor(level: HttpLoggingInterceptor.Level) : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(level)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptors: Set<Interceptor>): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.interceptors().addAll(interceptors)
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .pingInterval(15, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @IOScheduler
    fun provideSchedule() : Scheduler {
        return Schedulers.io()
    }

    @Provides
    @UiScheduler
    fun provideUiScheduler() : Scheduler {
        return AndroidSchedulers.mainThread()
    }

    // TODO - improve DB creation
    internal var SERVER_CREATION_CALLBACK: RoomDatabase.Callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            val values = ContentValues()
            values.put("name", "Rocket Chat")
            values.put("host", "https://demo.rocket.chat")
            values.put("avatar", "https://demo.rocket.chat/images/logo/android-chrome-192x192.pngu")
            db.insert("server", SQLiteDatabase.CONFLICT_FAIL, values)
        }
    }
}
