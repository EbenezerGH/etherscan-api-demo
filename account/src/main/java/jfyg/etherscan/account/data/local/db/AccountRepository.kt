package jfyg.etherscan.account.data.local.db


import android.app.Application
import android.arch.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import jfyg.data.account.Accounts
import jfyg.etherscan.account.data.local.db.Account
import jfyg.etherscan.account.data.local.db.AccountDao
import jfyg.etherscan.account.data.local.db.AccountDatabase

class AccountRepository internal constructor(application: Application) {
    private var accountDao: AccountDao? = null
    private var xPubs: LiveData<List<Account>>? = null

    init {
        val db = AccountDatabase.getDatabase(application)
        accountDao = db?.accountDao()
        xPubs = accountDao?.getXPubs
    }

    fun getXPubs(): LiveData<List<Account>>? {
        return xPubs
    }

    fun retrieveXPubAndBalance(xPub: String) {
        val account = Accounts()
        account.getBalance(xPub)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { insert(Account(xPub, it)) },
                        onError = { /*todo throw exception or return nulls*/ })
    }

    private fun insert(account: Account) =
            Single.just(accountDao).map({ it }).subscribeBy(
                    onSuccess = { it.insert(account) },
                    onError = { /*todo throw exception or return nulls*/ }
            )

}