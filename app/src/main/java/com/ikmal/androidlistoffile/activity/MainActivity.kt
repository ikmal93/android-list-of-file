package com.ikmal.androidlistoffile.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ikmal.androidlistoffile.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import android.content.pm.PackageInfo

import android.content.pm.PackageManager
import android.provider.OpenableColumns

import android.database.Cursor
import android.widget.ListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikmal.androidlistoffile.adapter.FileListAdapter
import com.ikmal.androidlistoffile.model.FileEntity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fileListAdapter by lazy {
        FileListAdapter()
    }
    private var initData = listOf(
        FileEntity(
            fileName = "How to be a good mentor",
            fileSize = "1000",
            fileType = ".pdf"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerview()
        setListener()
    }

    private fun initRecyclerview() {
        binding.apply {
            rvFileList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = fileListAdapter?.also {
                    it.setItems(initData)
                }
                itemAnimator = null
            }
        }
    }

    private fun setListener() {
        binding.apply {
            fabFileAdd.setOnClickListener {
                val intent = Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT)

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            val uri = data?.data
            val path = uri?.path
            val file = File(uri.toString())
            Log.d("HYKZQWX", "uri : $uri")
            Log.d("HYKZQWX", "path : $path")

            if (uri.toString().startsWith("content://")) {
                var cursor: Cursor? = null
                try {
                    cursor = contentResolver.query(uri!!, null, null, null, null)
                    val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)

                    var name = ""
                    var size = ""
                    cursor?.moveToFirst()
                    nameIndex?.let {
                        if (it >= 0) {
                            Log.d("HYKZQWX", "Name : ${cursor?.getString(it)}")
                            name = cursor?.getString(it)!!
                        }
                    }
                    sizeIndex?.let {
                        if (it >= 0) {
                            Log.d("HYKZQWX", "Size : ${cursor?.getLong(it)}")
                            size = cursor?.getLong(it).toString()
                        }
                    }
                    Log.d("HYKZQWX", "EXT : ${File(name).extension}")

                    fileListAdapter.also { adapter ->
                        adapter.setItems(
                            listOf(
                                FileEntity(
                                    fileName = name,
                                    fileSize = "$size",
                                    fileType = "${File(name).extension}"
                                )
                            )
                        )
                    }
                } finally {
                    cursor!!.close()
                }
            } else if (uri.toString().startsWith("file://")) {
                Log.d("HYKZQWX", "File Name : ${file.name}")
            }

        }
    }

}