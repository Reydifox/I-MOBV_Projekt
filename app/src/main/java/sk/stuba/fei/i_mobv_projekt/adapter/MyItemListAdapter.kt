package sk.stuba.fei.i_mobv_projekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemBinding
import sk.stuba.fei.i_mobv_projekt.parser.PubModel

class MyItemListAdapter(
    val clickListener: (Int) -> Unit
) : ListAdapter<PubModel, MyItemListAdapter.ItemViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<PubModel>() {
        override fun areItemsTheSame(oldItem: PubModel, newItem: PubModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PubModel, newItem: PubModel): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Creating an instance of the view which holds a single recycler view item.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ItemViewHolder(private val binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pubModel: PubModel) {
            binding.apply {
                itemTitle.text = pubModel.tags.name
                itemSite.text = pubModel.tags.website
            }
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                clickListener(position)
            }
        }
    }
}