package sk.stuba.fei.i_mobv_projekt.ui.widget.contactlist

import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem

interface ContactEvents {
    fun onContactClick(contact: ContactItem)
    fun onContactDelete(contact: ContactItem)
}