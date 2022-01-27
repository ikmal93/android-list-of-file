package com.ikmal.androidlistoffile.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ikmal.androidlistoffile.model.FileEntity

class FileListDiffUtil(
    private val oldList: List<FileEntity>,
    private val newList: List<FileEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fileName == newList[newItemPosition].fileName &&
                oldList[oldItemPosition].fileSize == newList[newItemPosition].fileSize &&
                oldList[oldItemPosition].fileType == newList[newItemPosition].fileType
    }
}