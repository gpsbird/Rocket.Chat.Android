package chat.rocket.android

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

import chat.rocket.android.app.RocketChatDatabase

class Test {

    internal var SERVER_CREATION_CALLBACK: RoomDatabase.Callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            val values = ContentValues()
            values.put("name", "Rocket Chat")
            values.put("host", "https://demo.rocket.chat")
            values.put("avatar", "https://demo.rocket.chat/images/logo/logo.png")
            db.insert("server", SQLiteDatabase.CONFLICT_FAIL, values)
        }
    }

    fun provideDatabase(context: Application): RocketChatDatabase {
        return Room.databaseBuilder(context, RocketChatDatabase::class.java, "rocketchat-db")
                .addCallback(SERVER_CREATION_CALLBACK).build()
    }
}
