package sk.stuba.fei.i_mobv_projekt.ui.widget.barlist

import sk.stuba.fei.i_mobv_projekt.data.db.model.BarItem

interface BarsEvents {
    fun onBarClick(bar: BarItem)
}