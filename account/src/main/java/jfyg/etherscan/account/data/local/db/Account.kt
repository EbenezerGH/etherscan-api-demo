package jfyg.etherscan.account.data.local.db

import ACCOUNT_BALANCE
import ACCOUNT_TABLE
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = ACCOUNT_TABLE)
data class Account(
        @PrimaryKey @NonNull var xPub: String,
        @ColumnInfo(name = ACCOUNT_BALANCE) var accountBalance: Double)