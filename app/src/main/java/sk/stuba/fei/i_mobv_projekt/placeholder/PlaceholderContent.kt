package sk.stuba.fei.i_mobv_projekt.placeholder

import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.parser.PubModel
import sk.stuba.fei.i_mobv_projekt.parser.PubTags
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
//        for (i in 1..COUNT) {
//            addItem(createPlaceholderItem(i))
//        }
    }

    fun parseData(data: PubExtension)
    {
        for (model in data.elements) {
            addItem(PlaceholderItem(model))
        }
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.data.id, item)
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
        val tags = PubTags("", "test", "", "", "extemeshop.com", "+42112345699")
        val pub = PubModel(position.toString(), "", "", tags)
        return PlaceholderItem(pub)
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val data: PubModel) {
        override fun toString(): String = data.tags.name
    }
}