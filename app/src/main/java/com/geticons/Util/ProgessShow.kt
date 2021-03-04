package com.geticons.Util

import android.app.ProgressDialog
import android.content.Context

object ProgessShow {
    var progressDialog : ProgressDialog?=null
    fun showProgress(
        context: Context,
        toLoad:Boolean
    ){
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Loading")
        if (toLoad){
            progressDialog!!.show()
        }else{
            progressDialog!!.dismiss()
        }

    }
}