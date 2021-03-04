package com.geticons.UI.DetailPages

import android.Manifest
import android.annotation.TargetApi
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.geticons.R
import com.geticons.Util.ConstantHelper
import com.geticons.data.ResponseBean.IconDetailBean
import com.geticons.data.ResponseBean.IconDetailPremiumBean
import com.geticons.data.ViewMOdal.MainViewModel
import com.geticons.databinding.ActivityIconDetailActivtyBinding
import com.google.gson.Gson
import java.io.File

class iconDetailActivty : AppCompatActivity() {
    lateinit var binding: ActivityIconDetailActivtyBinding
    var iconid=0
    var isPremium=false
    var authorId=0
    var progressDialog : ProgressDialog?=null
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_icon_detail_activty)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading")
        progressDialog!!.setCancelable(false)
        if (intent.hasExtra(ConstantHelper.IsPremium)){
            isPremium=intent.getBooleanExtra(ConstantHelper.IsPremium,false)

        }
        if (intent.hasExtra(ConstantHelper.ICON_ID)){
            iconid=intent.getIntExtra(ConstantHelper.ICON_ID,0)
            Log.d("aparna",iconid.toString())
            getIconDetail()
        }
        initClickListeners()
    }

    private fun getIconDetail() {

        progressDialog!!.show()

        viewModel.getIconDetail(iconid).observe(this,{
            Log.d("Response",it.body().toString())
            progressDialog!!.dismiss()
            if (it.code()==200){

                when(isPremium){
                    true->{
                        try {
                            val resp= Gson().fromJson(it.body(),IconDetailPremiumBean::class.java)
                            val pos=resp.rasterSizes!!.size
                            Glide.with(this)
                                .load(resp.rasterSizes[pos-1]!!.formats!![0]!!.preview_url)
                                .into(binding.imIcon)
                            imageUrl= resp.rasterSizes[pos-1]!!.formats!![0]!!.preview_url.toString()

                            if (resp.iconset!=null){
                                binding.textView2.text=if (resp.iconset!!.name!=null) resp.iconset!!.name else ConstantHelper.NotApplicable
                                binding.textView3.text=if (resp.type!=null) resp.type else ConstantHelper.NotApplicable
    //                    binding.textView3.text=if (resp.iconset!!.type!=null) resp.iconset!!.type else ConstantHelper.NotApplicable
                                if (resp.isPremium!!){
                                    binding.badge.text="Premium"
                                    binding.badgeicon.visibility= View.VISIBLE
                                    binding.textView4.visibility= View.VISIBLE
                                    binding.im.visibility= View.VISIBLE
                                }
                                else{
                                    binding.badge.text="Free"
                                    binding.badgeicon.visibility= View.GONE
                                    binding.textView4.visibility= View.GONE
                                    binding.im.visibility= View.GONE
                                }
    //                            binding.price.visibility=View.GONE
    //                            binding.licensename.visibility=View.GONE
                                binding.price.text=if (resp.iconset.prices!=null) "$ "+ resp.prices!![0]!!.price else ConstantHelper.NotApplicable
                                binding.authorname.text=if (resp.iconset!!.author!=null) resp.iconset!!.author!!.name else ConstantHelper.NotApplicable

                                authorId= resp.iconset.author!!.userId!!
                                binding.licensename.text=if (resp.iconset!!.prices!=null) resp.iconset!!.prices!![0]?.license?.name else ConstantHelper.NotApplicable
                                binding.readme.text=if (resp.readme!=null) resp.readme else ConstantHelper.NotApplicable
                                url= resp.websiteurl!!
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    else->{
                        try {
                            val resp= Gson().fromJson(it.body(),IconDetailBean::class.java)
                            val pos=resp.rasterSizes!!.size
                            Glide.with(this)
                                .load(resp.rasterSizes[pos-1]!!.formats!![0]!!.preview_url)
                                .into(binding.imIcon)
//                            imageUrl= resp.rasterSizes[pos-1]!!.formats!![0]!!.preview_url.toString()

                            if (resp.iconset!=null){
                                binding.textView2.text=if (resp.iconset!!.name!=null) resp.iconset!!.name else ConstantHelper.NotApplicable
                                binding.textView3.text=if (resp.type!=null) resp.type else ConstantHelper.NotApplicable
                                binding.readme.text=if (resp.readme!=null) resp.readme else ConstantHelper.NotApplicable
    //                    binding.textView3.text=if (resp.iconset!!.type!=null) resp.iconset!!.type else ConstantHelper.NotApplicable
                                if (resp.isPremium!!){
                                    binding.badge.text="Premium"
                                    binding.badgeicon.visibility= View.VISIBLE
                                }
                                else{
                                    binding.badge.text="Free"
                                    binding.badgeicon.visibility= View.GONE
                                }
                                binding.price.visibility=View.GONE
                                binding.licensename.visibility=View.GONE
    //                            binding.price.text=if (resp.iconset.p!=null) "$ "+resp.prices[0]!!.price else ConstantHelper.NotApplicable
                                authorId= resp.iconset.author!!.userId!!
                                binding.authorname.text=if (resp.iconset!!.author!=null) resp.iconset!!.author!!.name else ConstantHelper.NotApplicable
    //                            binding.licensename.text=if (resp.iconset!!.li!=null) resp.iconset!!.license!!.name else ConstantHelper.NotApplicable
                                url= resp.websiteurl!!
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
/*


*/


            }
        })

    }
    var url=""

    private fun initClickListeners() {

        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.authorname.setOnClickListener {
            val intent=Intent(this,AuthorDetailActivity::class.java)
            intent.putExtra(ConstantHelper.AuthorID,authorId)
            startActivity(intent)
        }
        binding.website.setOnClickListener {
            val intent=Intent(this,WebViewActivity::class.java)
            intent.putExtra(ConstantHelper.URL,url)
            startActivity(intent)
        }
        binding.textView4.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askPermissions()
            } else {
                downloadImage(imageUrl)
            }
        }

        binding.im.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askPermissions()
            } else {
                downloadImage(imageUrl)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun askPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AlertDialog.Builder(this)
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                        finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)


            }
        } else {
            downloadImage(imageUrl)
        }
    }


    var imageUrl=""

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    downloadImage(imageUrl)
                }
                return
            }

            else -> {

            }
        }
    }

    var msg: String? = ""
    var lastMsg = ""

    private fun downloadImage(url: String) {

        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(url, directory, status)
                if (msg != lastMsg) {
                    this.runOnUiThread {
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }).start()
    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully"
            else -> "There's nothing to download"
        }
        return msg
    }
}