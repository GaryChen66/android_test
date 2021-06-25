package co.metalab.tech.interview.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.metalab.tech.interview.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_poke_evolution.view.*

class PokeEvolutionAdapter(private val onItemClickListener: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<PokeEvolutionVH>() {

    var items: List<EvolutionItem> = emptyList()
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeEvolutionVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poke_evolution, parent, false)
        return PokeEvolutionVH(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PokeEvolutionVH, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }
}

class PokeEvolutionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: EvolutionItem, onItemClickListener: ((Int) -> Unit)?) = with(itemView) {
        evolutionTitle.text = when (item.evolutionRelation) {
            EvolutionRelation.FROM -> context.getString(R.string.evolves_from)
            EvolutionRelation.INTO -> context.getString(R.string.evolves_into)
        }

        val name = item.name
        val description = "$name ${item.trigger}"
        evolutionDescription.text = description

        Glide.with(this)
            .load(item.imageUrl)
            .into(evolutionImage)

        setOnClickListener { onItemClickListener?.invoke(item.pokeId) }
    }
}
