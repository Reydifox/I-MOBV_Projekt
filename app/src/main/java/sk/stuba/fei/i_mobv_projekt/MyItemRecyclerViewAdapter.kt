package sk.stuba.fei.i_mobv_projekt

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController

import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent.PlaceholderItem
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null
    private lateinit var binding: FragmentItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemTitle.text = item.data.tags.name
        holder.itemSite.text = item.data.tags.website

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
            openDetailsFragment(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.itemTitle
        val itemSite: TextView = binding.itemSite

        override fun toString(): String {
            return super.toString() + " '" + itemTitle.text + "'"
        }
    }

    private fun openDetailsFragment(item : PlaceholderItem)
    {
        val data = item.data
        val fragmentDirections = ItemFragmentDirections.actionItemFragmentToPubInfoFragment(data.tags.name, data.lat, data.lon, data.tags.website, data.tags.amenity, data.tags.opening_hours, data.tags.phone)
        binding.root.findNavController().navigate(fragmentDirections)
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaceholderItem)
    }

}