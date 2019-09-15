package pl.kamilszustak.hulapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.events_list_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.callback.EntityDiffCallback
import pl.kamilszustak.hulapp.model.Event
import java.text.SimpleDateFormat

class EventsRecyclerViewAdapter(
    private val listener: EventViewHolder.OnEventClickListener
) : RecyclerView.Adapter<EventsRecyclerViewAdapter.EventViewHolder>() {

    private var events: MutableList<Event> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_list_item, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.eventTitleTextView.apply {
            text = event.title
            setOnClickListener {
                listener.onEventClick(event)
            }
        }

        holder.eventDateTextView.apply {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            text = dateFormat.format(event.date)
            setOnClickListener {
                listener.onEventClick(event)
            }
        }

        holder.eventPlaceNameTextView.apply {
            text = event.placeName
            setOnClickListener {
                listener.onEventClick(event)
            }
        }

        holder.eventAddressTextView.apply {
            text = event.address
            setOnClickListener {
                listener.onEventClick(event)
            }
        }

        holder.eventDescriptionTextView.apply {
            text = event.description
            setOnClickListener {
                listener.onEventClick(event)
            }
        }

    }

    fun updateEventsList(newEvents: List<Event>) {
        val callback = EntityDiffCallback(events, newEvents)
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

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitleTextView: TextView = view.eventTitleTextView
        val eventDateTextView: TextView = view.eventDateTextView
        val eventPlaceNameTextView: TextView = view.eventPlaceNameTextView
        val eventAddressTextView: TextView = view.eventAddressTextView
        val eventDescriptionTextView: TextView = view.eventDescriptionTextView

        interface OnEventClickListener {
            fun onEventClick(event: Event)
        }
    }
}