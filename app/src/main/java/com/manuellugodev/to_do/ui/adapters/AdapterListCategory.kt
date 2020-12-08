package com.manuellugodev.to_do.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manuellugodev.to_do.R
import com.manuellugodev.to_do.room.Category
import com.manuellugodev.to_do.room.Task

class AdapterListCategory(private var listCategory: List<Category>): RecyclerView.Adapter<AdapterListCategory.ViewHolderCategory>() {

    fun updateDataAdapter(list: List<Category>){
        listCategory=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategory {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)

        return ViewHolderCategory(view)
    }

    override fun onBindViewHolder(holderCategory: ViewHolderCategory, position: Int) {

        holderCategory.txtTitleCategory.text=listCategory[position].nameCategory
    }

    override fun getItemCount(): Int = listCategory.size

    class ViewHolderCategory(itemView:View):RecyclerView.ViewHolder(itemView) {

        val txtTitleCategory:TextView
        init {

            txtTitleCategory=itemView.findViewById(R.id.txtNameCategory)

        }

    }


}