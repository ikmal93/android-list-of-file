package com.ikmal.androidlistoffile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ikmal.androidlistoffile.databinding.ItemFileListBinding
import com.ikmal.androidlistoffile.model.FileEntity

class FileListAdapter : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    private var items = emptyList<FileEntity>()

    fun setItems(items: List<FileEntity>) {
        val dataDiffUtil = FileListDiffUtil(this.items, items)
        val dataDiffResult = DiffUtil.calculateDiff(dataDiffUtil)
        this.items = items
        dataDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFileListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val binding: ItemFileListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FileEntity) {
            binding.apply {

            }
        }
    }
}