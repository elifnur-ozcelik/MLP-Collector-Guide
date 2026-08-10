package com.elifnurozcelik.hw1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elifnurozcelik.hw1.data.CollectorEntity

sealed class CollectorItem {
    data class Normal(val name: String) : CollectorItem()
    object Twilight : CollectorItem()
}

class CollectorAdapter(
    private var collectors: List<CollectorEntity>,
    private val listener: CollectorClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_TWILIGHT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (collectors[position].favorite == "Twilight Sparkle") TYPE_TWILIGHT else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_TWILIGHT) {
            val view = inflater.inflate(R.layout.collector_twilight, parent, false)
            TwilightViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.collector_normal, parent, false)
            NormalViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val collector = collectors[position]
        when (holder) {
            is NormalViewHolder -> {
                holder.txtName.text = "${collector.name} ${collector.surname}"
                holder.itemView.setOnClickListener { listener.onCollectorClick(collector) }
            }
            is TwilightViewHolder -> {

                holder.itemView.setOnClickListener { listener.onCollectorClick(collector) }
            }
        }
    }
    override fun getItemCount(): Int = collectors.size
    class NormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtCollectorName)
    }
    class TwilightViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
