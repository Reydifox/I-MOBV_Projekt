package sk.stuba.fei.i_mobv_projekt.fragment

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
import android.widget.Button
import androidx.annotation.RawRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.adapter.MyItemRecyclerViewAdapter
import sk.stuba.fei.i_mobv_projekt.api.Api
import sk.stuba.fei.i_mobv_projekt.api.PubRequest
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
lateinit var ItemAdapter : MyItemRecyclerViewAdapter
class ItemFragment : Fragment(), MyItemRecyclerViewAdapter.OnItemClickListener {

    private var columnCount = 1

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
            var json : String = resources.getRawTextFile(R.raw.pubs)
            lifecycleScope.launch {
                json = Api.retrofitService.getJsonString(
                    PubRequest(database = "mobvapp", dataSource = "Cluster0", collection = "bars")
                )
            }
            val data = Gson().fromJson(json, PubExtension::class.java)
            PlaceholderContent.parseData(data)
            adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            ItemAdapter = adapter as MyItemRecyclerViewAdapter
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
            ItemAdapter.sort(false)
            ItemAdapter.notifyDataSetChanged()
        }

        // Sort descending
        val buttonDsc = view.findViewById<Button>(R.id.button_descending)
        buttonDsc.setOnClickListener {
            ItemAdapter.sort(true)
            ItemAdapter.notifyDataSetChanged()
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