package com.makaroni.chocho.legacy.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makaroni.chocho.data.model.CollectionItem
import com.makaroni.chocho.databinding.ItemCollectionBinding

class CollectionAdapter : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private var items: List<CollectionItem> = emptyList()

    fun setList(newList: List<CollectionItem>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = items.getOrNull(position) ?: return
        holder.onBind(item)
    }

    override fun getItemCount() = items.size

    fun getItem(position: Int):CollectionItem?{
        return items.getOrNull(position)
    }

    class CollectionViewHolder(private val binding: ItemCollectionBinding) : RecyclerView.ViewHolder(binding.root) {

        private var item: CollectionItem? = null

        fun onBind(item: CollectionItem) {
            this.item = item
        }


        companion object {
            fun newInstance(parent: ViewGroup): CollectionViewHolder {
                val binding = ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CollectionViewHolder(binding)
            }
        }

    }
}