package com.example.cura

class Step2_1Adapter(itemList: List<check_item>, private val onItemCheckChangedCallback: () -> Unit) :
    BaseCheckRecyclerAdapter(itemList) {

    override fun onItemCheckChanged() {
        onItemCheckChangedCallback()
    }
}
