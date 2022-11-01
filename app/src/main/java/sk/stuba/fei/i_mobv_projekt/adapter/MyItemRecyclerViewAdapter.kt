package sk.stuba.fei.i_mobv_projekt.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController

import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent.PlaceholderItem
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemBinding
import sk.stuba.fei.i_mobv_projekt.fragment.ItemFragmentDirections
import sk.stuba.fei.i_mobv_projekt.viewmodel.ItemViewModel

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private var values: MutableList<PlaceholderItem>
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if(item.data.tags.name.isNullOrEmpty())
            item.data.tags.name = "Unknown"

        holder.itemTitle.text = item.data.tags.name
        holder.itemSite.text = item.data.tags.website

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
            openDetailsFragment(item)
        }

//        binding.buttonDelete.setOnClickListener { remove(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.itemTitle
        val itemSite: TextView = binding.itemSite

        override fun toString(): String {
            return super.toString() + " '" + itemTitle.text + "'"
        }
    }

    fun refresh(_values: MutableList<PlaceholderItem>)
    {
        values = _values
        notifyDataSetChanged()
    }

    fun sort(descending: Boolean)
    {
        if (descending)
        {
            values.sortByDescending { it.data.tags.name }
        }
        else {
            values.sortBy { it.data.tags.name }
        }
        println("sorting " + values.size + " items")
    }

    private fun remove(item : PlaceholderItem)
    {
        values.remove(item)
        notifyDataSetChanged()
    }

    fun removeById(id : String)
    {
        val item = values.firstOrNull { it.data.id == id }
        values.remove(item)
        notifyDataSetChanged()
        println("Item with name: " + item?.data?.tags?.name + " has been removed!")
    }

    private fun openDetailsFragment(item : PlaceholderItem)
    {
        val data = item.data
        val fragmentDirections = ItemFragmentDirections.actionItemFragmentToPubInfoFragment(data.tags.name.toString(), data.lat, data.lon, data.tags.website, data.tags.amenity, data.tags.opening_hours, data.tags.phone, data.id)
        binding.root.findNavController().navigate(fragmentDirections)
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaceholderItem)
    }

}