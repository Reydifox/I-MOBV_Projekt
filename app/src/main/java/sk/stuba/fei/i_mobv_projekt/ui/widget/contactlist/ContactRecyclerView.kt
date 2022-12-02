package sk.stuba.fei.i_mobv_projekt.ui.widget.contactlist

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem
import sk.stuba.fei.i_mobv_projekt.ui.fragments.ContactListFragmentDirections

class ContactRecyclerView : RecyclerView {
    private lateinit var contactAdapter: ContactAdapter
    /**
     * Default constructor
     *
     * @param context context for the activity
     */
    constructor(context: Context) : super(context) {
        init(context)
    }

    /**
     * Constructor for XML layout
     *
     * @param context activity context
     * @param attrs   xml attributes
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        contactAdapter = ContactAdapter(object : ContactEvents {
            override fun onContactClick(contact: ContactItem) {
                this@ContactRecyclerView.findNavController().navigate(
                    ContactListFragmentDirections.actionToDetail(contact.bar_id)
                )
            }
            override fun onContactDelete(contact: ContactItem) {
                println("DELETE")
            }
        })
        adapter = contactAdapter
    }
}

@BindingAdapter(value = ["contactItems"])
fun ContactRecyclerView.applyItems(
    contacts: List<ContactItem>?
) {
    (adapter as ContactAdapter).items = contacts ?: emptyList()
}