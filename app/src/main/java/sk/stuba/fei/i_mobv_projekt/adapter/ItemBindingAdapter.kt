package sk.stuba.fei.i_mobv_projekt.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, pubExtension: PubExtension?){
    val adapter = recyclerView.adapter as MyItemListAdapter
    adapter.submitList(pubExtension?.elements)
}