package me.susieson.cryptocharts.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.susieson.cryptocharts.R
import me.susieson.cryptocharts.model.Cryptocurrency
import java.text.NumberFormat

class CryptoListAdapter(private val listener: OnItemClickListener, private val context: Context?) :
    PagedListAdapter<Cryptocurrency, CryptoListAdapter.CryptoViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cryptocurrency>() {
            override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cryptocurrency_item, parent, false)
        return CryptoViewHolder(view, listener, context)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val cryptocurrency: Cryptocurrency? = getItem(position)
        holder.bind(cryptocurrency)
    }

    class CryptoViewHolder(
        private val view: View,
        private val listener: OnItemClickListener,
        private val context: Context?
    ) :
        RecyclerView.ViewHolder(view) {

        fun bind(cryptocurrency: Cryptocurrency?) {
            if (cryptocurrency != null) {
                val cryptoName: TextView = view.findViewById(R.id.crypto_name)
                val cryptoPrice: TextView = view.findViewById(R.id.crypto_price)
                val cryptoFavourite: CheckBox = view.findViewById(R.id.crypto_favourite)
                cryptoName.text = cryptocurrency.coinName
                cryptoPrice.text =
                    if (cryptocurrency.price < 0.0) context?.getString(R.string.not_available)
                    else NumberFormat.getCurrencyInstance().format(cryptocurrency.price)
                cryptoFavourite.isChecked = cryptocurrency.favourite
                cryptoFavourite.setOnClickListener {
                    listener.onItemClick(
                        cryptocurrency.id,
                        !cryptocurrency.favourite
                    )
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Long, favourite: Boolean)
    }
}