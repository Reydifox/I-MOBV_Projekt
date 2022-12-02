package sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data

class Contact(
    val contact: String
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false

        if (contact != other.contact) return false

        return true
    }

    override fun hashCode(): Int {
        return 31 * contact.hashCode()
    }

    override fun toString(): String {
        return "Contact(name=$contact)"
    }
}