package sk.stuba.fei.i_mobv_projekt.ui.widget.nearbyBars

import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.NearbyBar

interface NearbyBarsEvents {
    fun onBarClick(nearbyBar: NearbyBar)
}