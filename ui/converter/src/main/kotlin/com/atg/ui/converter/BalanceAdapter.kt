package com.atg.ui.converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atg.converter.R

class BalanceAdapter :
    ListAdapter<BalanceAppModel, BalanceAdapter.BalanceHolder>(BalanceDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_balance, parent, false)
            .let(::BalanceHolder)

    override fun onBindViewHolder(holder: BalanceHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BalanceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(balance: BalanceAppModel) {
            (itemView as AppCompatTextView).setText("${balance.name} ${balance.balance}")
        }
    }

    private class BalanceDiffCallBack : DiffUtil.ItemCallback<BalanceAppModel>() {
        override fun areItemsTheSame(oldItem: BalanceAppModel, newItem: BalanceAppModel): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: BalanceAppModel, newItem: BalanceAppModel): Boolean =
            true
    }
}