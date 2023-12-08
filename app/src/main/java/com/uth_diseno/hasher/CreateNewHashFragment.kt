package com.uth_diseno.hasher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateNewHashFragment : BottomSheetDialogFragment() {

    interface OnItemAddedListener {
        fun onItemAddedListener(newItem: Hashers)
    }

    private var itemAddedListener: OnItemAddedListener? = null

    fun setOnItemAddedListener(listener: Hashers) {
        itemAddedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_new_hash, container, false)

        val button = view.findViewById<Button>(R.id.bsd_submit)

        button.setOnClickListener {
            val hash = Hashers(
                1,
                "Password",
                "Hello World",
                "hola",
                    "sdajasdw")
            itemAddedListener?.onItemAddedListener(hash)
            dismiss()
        }
        return view
    }
}