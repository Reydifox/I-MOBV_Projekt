package sk.stuba.fei.i_mobv_projekt.ui.widget.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.helpers.autoNotify
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem
import kotlin.properties.Delegates

class ContactAdapter(val events: ContactEvents? = null) :
    RecyclerView.Adapter<ContactAdapter.ContactItemViewHolder>() {
    var items: List<ContactItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.uid.compareTo(n.uid) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(items[position], events)
    }

    class ContactItemViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item,
            parent,
            false)
    ) : RecyclerView.ViewHolder(itemView){

        fun bind(item: ContactItem, events: ContactEvents?) {
            itemView.findViewById<TextView>(R.id.name).text = item.name
            itemView.findViewById<TextView>(R.id.bar_name).text = item.bar_name

            itemView.setOnClickListener { events?.onContactClick(item) }
            itemView.findViewById<ImageView>(R.id.deleteContact).setOnClickListener {
                events?.onContactDelete(item)
            }
        }
    }
}