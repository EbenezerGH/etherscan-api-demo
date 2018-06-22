package jfyg.etherscan.qrscanner

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import io.github.novacrypto.qrscanner.ScanQrActivity
import jfyg.etherscan.core.application.BaseActivity

const val REQUEST_SCAN = 1

open class QrScannerActivity : BaseActivity() {

    private lateinit var model: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //model = ViewModelProviders.of(this).get(EtherAccountViewModel(this.application)::class.java)
        //model.xPubs?.observe(this, Observer<List<Account>> { adapter.setWords(it) })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivityForResult(Intent(this, ScanQrActivity::class.java).apply {
                putExtra(ScanQrActivity.OPTION_SHOW_BARCODE_BOX, BuildConfig.DEBUG)
            }, REQUEST_SCAN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //super.onActivityResult(requestCode, resultCode, data)
        val xPub = data.extras?.getString(ScanQrActivity.BARCODE_DATA) ?: ""
        if (requestCode == REQUEST_SCAN) {
            if (resultCode == Activity.RESULT_OK) {
                getModel()
                model.insertXPubFromScanner(xPub)
            }
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun setModel(model: AndroidViewModel) {
        this.model = model
    }

    fun getModel() {
        model as AccountViewModel
    }

}