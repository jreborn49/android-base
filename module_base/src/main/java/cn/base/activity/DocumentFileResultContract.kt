package cn.base.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import cn.lib.base.ex.openDocumentIntent

class DocumentFileResultContract : ActivityResultContract<Unit, Uri?>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return openDocumentIntent()
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) intent?.data else null
    }

}