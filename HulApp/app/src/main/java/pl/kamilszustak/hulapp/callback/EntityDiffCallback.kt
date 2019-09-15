package pl.kamilszustak.hulapp.callback

import androidx.recyclerview.widget.DiffUtil
import pl.kamilszustak.hulapp.model.ApplicationEntity

class EntityDiffCallback<T: ApplicationEntity>(
    private val oldItems: List<T>,
    private val newItems: List<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }
}