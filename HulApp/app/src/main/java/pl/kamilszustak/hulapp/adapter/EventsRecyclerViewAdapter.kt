package pl.kamilszustak.hulapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.events_list_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.model.Event

class EventsRecyclerViewAdapter : RecyclerView.Adapter<EventsRecyclerViewAdapter.TaskViewHolder>() {

    private var events: MutableList<Event> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val event = events[position]

        holder.eventTitleTextView.text = event.title
        holder.eventDescriptionTextView.text = event.description
    }

    fun updateEventsList(newEvents: List<Event>) {
        val callback = EventDiffCallback(events, newEvents)
        val result = DiffUtil.calculateDiff(callback)

        events.apply {
            clear()
            addAll(newEvents)
        }

        result.dispatchUpdatesTo(this)
    }

    fun getEventAt(position: Int): Event {
        return events[position]
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitleTextView: TextView = view.eventTitleTextView
        val eventDescriptionTextView: TextView = view.eventDescriptionTextView
        val eventImageImageView: ImageView = view.eventImageImageView

        interface TaskViewClickListener {

        }
    }

    class EventDiffCallback(
        private val oldEvents: List<Event>,
        private val newEvents: List<Event>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldEvents[oldItemPosition].id == newEvents[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldEvents[oldItemPosition] == newEvents[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldEvents.size
        }

        override fun getNewListSize(): Int {
            return newEvents.size
        }
    }
}