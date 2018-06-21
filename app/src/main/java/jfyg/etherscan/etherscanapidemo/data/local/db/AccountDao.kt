package jfyg.etherscan.etherscanapidemo.data.local.db


import DELETE_ALL_FROM_ACCOUNT_TABLE
import SELECT_ALL_FROM_ACCOUNT_TABLE
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface AccountDao {
    @get:Query(SELECT_ALL_FROM_ACCOUNT_TABLE)
    val getXPubs: LiveData<List<Account>>

    @Insert(onConflict = REPLACE)
    fun insert(xPub: Account)

    @Query(DELETE_ALL_FROM_ACCOUNT_TABLE)
    fun deleteAll()
}