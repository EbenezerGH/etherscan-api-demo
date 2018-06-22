package jfyg.etherscan.account

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import jfyg.etherscan.account.data.local.db.Account
import jfyg.etherscan.qrscanner.QrScannerActivity

class AccountActivity : QrScannerActivity() {

    private lateinit var model: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(AccountViewModel(this.application)::class.java)
        model.xPubs?.observe(this, Observer<List<Account>> { adapter.setWords(it) })
        setModel(model)
    }

}