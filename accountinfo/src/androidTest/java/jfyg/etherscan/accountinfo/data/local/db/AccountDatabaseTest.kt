package jfyg.etherscan.accountinfo.data.local.db


import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import jfyg.etherscan.accountinfo.data.local.db.Account
import jfyg.etherscan.accountinfo.data.local.db.AccountDao
import jfyg.etherscan.accountinfo.data.local.db.AccountDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import jfyg.etherscan.testutils.blockingObserve

@RunWith(AndroidJUnit4::class)
class AccountDatabaseTest {
    private lateinit var dao: AccountDao
    private lateinit var db: AccountDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AccountDatabase::class.java).build()
        dao = db.accountDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_xPub_and_balance_into_account_table() {
        val testAccount = Account(
                "0x281055afc982d96fab65b3a49cac8b878184cb16",
                1538422.843560898194846506)

        dao.insert(testAccount)
        val dbItems = dao.getXPubs

        assertEquals(1, dbItems.blockingObserve()?.size)
    }

    @Test
    fun read_xPub_and_balance_from_account_table() {
        val testAccount = Account(
                "0x281055afc982d96fab65b3a49cac8b878184cb16",
                1538422.843560898194846506)

        dao.insert(testAccount)
        val dbItem = dao.getXPubs.blockingObserve()?.get(0)
        val xPubBalance = dbItem?.accountBalance
        val xPubAddress = dbItem?.xPub

        assertEquals(xPubBalance, testAccount.accountBalance)
        assertEquals(xPubAddress, testAccount.xPub)
    }

    @Test
    fun get_all_xPub_addresses_from_account_table() {
        val testAccount = Account(
                "0x281055afc982d96fab65b3a49cac8b878184cb16",
                1538422.843560898194846506)

        val testAccount2 = Account(
                "0x4b46ce3569aefa1acc1009290c8e043747172d89",
                1510.606605753037298732)

        val testAccount3 = Account(
                "0x6f46cf5569aefa1acc1009290c8e043747172d89",
                1510.606603753037298732)

        dao.insert(testAccount)
        dao.insert(testAccount2)
        dao.insert(testAccount3)
        val dbItems = dao.getXPubs

        assertEquals(dbItems.blockingObserve()?.size, 3)
    }

    @Test
    fun ensure_adding_same_account_data_replaces_previous() {
        val testAccount = Account(
                "0x281055afc982d96fab65b3a49cac8b878184cb16",
                1538422.843560898194846506)

        dao.insert(testAccount)
        dao.insert(testAccount)
        dao.insert(testAccount)

        val dbItems = dao.getXPubs
        assertEquals(dbItems.blockingObserve()?.size, 1)
    }

    @Test
    fun delete_all_data_from_account_table() {
        val testAccount = Account(
                "0x281055afc982d96fab65b3a49cac8b878184cb16",
                1538422.843560898194846506)

        val testAccount2 = Account(
                "0x6f46cf5569aefa1acc1009290c8e043747172d89",
                1510065.606605753037298732)

        dao.insert(testAccount)
        dao.insert(testAccount2)
        var dbItems = dao.getXPubs
        val size = dbItems.blockingObserve()?.size

        assertEquals(2, size)

        dao.deleteAll()

        dbItems = dao.getXPubs
        val sizeAfterDelete = dbItems.blockingObserve()?.size
        assertEquals(0, sizeAfterDelete)
    }

}