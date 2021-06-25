package co.metalab.tech.interview.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.metalab.tech.interview.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_poke.view.*

class PokeItemAdapter(private val context: Context, private val onItemClickListener: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<PokeItemAdapter.PokeVH>() {

    var items: List<PokeItem> = emptyList()
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poke, parent, false)
        return PokeVH(context, itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PokeVH, position: Int) {
        holder.bind(position, onItemClickListener)
    }

    inner class PokeVH(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, onItemClickListener: ((Int) -> Unit)?) = with(itemView) {
            val item = items[position]
            pokeNumber.text = item.formattedId
            pokeName.text = item.name
            setOnClickListener { onItemClickListener?.invoke(item.pokeId) }

            Glide.with(this@PokeVH.context)
                .load(item.imageUrl)
                .into(pokePhoto)
        }
    }
}
