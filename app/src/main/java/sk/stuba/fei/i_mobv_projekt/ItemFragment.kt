package sk.stuba.fei.i_mobv_projekt

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.annotation.RawRes
import androidx.navigation.findNavController
import com.google.gson.Gson
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemBinding
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentPubBinding
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentPubInfoBinding
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment(), MyItemRecyclerViewAdapter.OnItemClickListener {

    private var columnCount = 1
    private lateinit var _adapter : MyItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt("column-count")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list)

        // Set the adapter
        with(list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            val json : String = resources.getRawTextFile(R.raw.pubs)
            val data = Gson().fromJson(json, PubExtension::class.java)
            PlaceholderContent.parseData(data)
            adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            _adapter = adapter as MyItemRecyclerViewAdapter
        }

        // Add new item
        val buttonAdd = view.findViewById<Button>(R.id.button_add)
        buttonAdd.setOnClickListener {
            val fragmentDirections = ItemFragmentDirections.actionItemFragmentToSettingsFragment()
            view.findNavController().navigate(fragmentDirections)
        }

        // Sort ascending
        val buttonAsc = view.findViewById<Button>(R.id.button_ascending)
        buttonAsc.setOnClickListener {
            _adapter.sort(false)
            _adapter.notifyDataSetChanged()
        }

        // Sort descending
        val buttonDsc = view.findViewById<Button>(R.id.button_descending)
        buttonDsc.setOnClickListener {
            _adapter.sort(true)
            _adapter.notifyDataSetChanged()
        }
        return view
    }

    fun Resources.getRawTextFile(@RawRes id: Int) =
        openRawResource(id).bufferedReader().use { it.readText() }

    override fun onItemClick(item: PlaceholderContent.PlaceholderItem) {
        // TODO FIX
        Log.d("tag", "Item clicked: $item")
        println("ITEM CLICKED")
    }
}