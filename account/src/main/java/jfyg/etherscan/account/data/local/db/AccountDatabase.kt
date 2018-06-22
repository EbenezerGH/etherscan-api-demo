package jfyg.etherscan.account.data.local.db

import ACCOUNT_DATABASE
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.NonNull
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

@Database(entities = [(Account::class)], version = 1)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        private var INSTANCE: AccountDatabase? = null

        internal fun getDatabase(context: Context): AccountDatabase? {
            if (INSTANCE == null) {
                synchronized(AccountDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AccountDatabase::class.java, ACCOUNT_DATABASE)
                                .addCallback(RoomDbCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    object RoomDbCallback : Callback() {
        override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
            super.onOpen(db)

            Single
                    .just(INSTANCE)
                    .map({ it.accountDao() })
                    .subscribeBy(
                            onSuccess = {
                                it.deleteAll()
                            },
                            onError = { /*todo throw exception or return nulls*/ }
                    )
        }
    }
}