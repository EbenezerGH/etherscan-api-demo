package jfyg.etherscan.account

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import jfyg.etherscan.account.data.local.db.Account
import jfyg.etherscan.account.data.local.db.AccountRepository

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AccountRepository = AccountRepository(application)
    val xPubs: LiveData<List<Account>>?

    init {
        xPubs = repository.getXPubs()
    }

    fun insert(account: Account) {
        repository.insert(account)
    }

    fun retrieveXPubAndBalance(xPub: String) {
        repository.retrieveXPubAndBalance(xPub)
    }
}
