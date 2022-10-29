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
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.adapter.MyItemRecyclerViewAdapter
import sk.stuba.fei.i_mobv_projekt.api.Api
import sk.stuba.fei.i_mobv_projekt.api.PubRequest
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemBinding
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemListBinding
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.placeholder.PlaceholderContent
import sk.stuba.fei.i_mobv_projekt.viewmodel.ItemViewModel

/**
 * A fragment representing a list of Items.
 */
lateinit var ItemAdapter : MyItemRecyclerViewAdapter
class ItemFragment : Fragment(), MyItemRecyclerViewAdapter.OnItemClickListener {

    private var columnCount = 1
    private lateinit var binding: FragmentItemListBinding
    private val viewModel: ItemViewModel by viewModels()

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
        //val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        //val list = view.findViewById<RecyclerView>(R.id.list)

        binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.itemViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Set the adapter
        with(binding.list) {
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
        val buttonAdd = binding.buttonAdd//view.findViewById<Button>(R.id.button_add)
        buttonAdd.setOnClickListener {
            val fragmentDirections = ItemFragmentDirections.actionItemFragmentToSettingsFragment()
            //view.findNavController().navigate(fragmentDirections)
            binding.root.findNavController().navigate(fragmentDirections)
        }

        // Sort ascending
        val buttonAsc = binding.buttonAscending//view.findViewById<Button>(R.id.button_ascending)
        buttonAsc.setOnClickListener {
            ItemAdapter.sort(false)
            ItemAdapter.notifyDataSetChanged()
        }

        // Sort descending
        val buttonDsc = binding.buttonDescending//view.findViewById<Button>(R.id.button_descending)
        buttonDsc.setOnClickListener {
            ItemAdapter.sort(true)
            ItemAdapter.notifyDataSetChanged()
        }
        return binding.root
    }

    fun Resources.getRawTextFile(@RawRes id: Int) =
        openRawResource(id).bufferedReader().use { it.readText() }

    override fun onItemClick(item: PlaceholderContent.PlaceholderItem) {
        // TODO FIX
        Log.d("tag", "Item clicked: $item")
        println("ITEM CLICKED")
    }
}