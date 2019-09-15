package pl.kamilszustak.hulapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tracks_list_item.view.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.callback.EntityDiffCallback
import pl.kamilszustak.hulapp.model.Track
import java.text.SimpleDateFormat

class TracksRecyclerViewAdapter(
    private val listener: TrackViewHolder.OnTrackClickListener
) : RecyclerView.Adapter<TracksRecyclerViewAdapter.TrackViewHolder>() {

    private var tracks: MutableList<Track> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tracks_list_item, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]

        holder.trackEndDateTextView.apply {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            text = dateFormat.format(track.endDate)
            setOnClickListener {
                listener.onTrackClick(track)
            }
        }

        holder.trackLengthTextView.apply {
            text = track.length.toString()
            setOnClickListener {
                listener.onTrackClick(track)
            }
        }

        holder.trackDurationTextView.apply {
            val formattedTime = String.format(
                "%02d:%02d:%02d",
                track.duration / 3600,
                (track.duration % 3600) / 60,
                (track.duration % 60)
            )

            text = formattedTime
            setOnClickListener {
                listener.onTrackClick(track)
            }
        }
    }

    fun updateTracksList(newTracks: List<Track>) {
        val callback = EntityDiffCallback(tracks, newTracks)
        val result = DiffUtil.calculateDiff(callback)

        tracks.apply {
            clear()
            addAll(newTracks)
        }

        result.dispatchUpdatesTo(this)
    }

    fun getTrackAt(position: Int): Track {
        return tracks[position]
    }


    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackEndDateTextView: TextView = view.trackEndDateTextView
        val trackLengthTextView: TextView = view.trackLengthTextView
        val trackDurationTextView: TextView = view.trackDurationTextView

        interface OnTrackClickListener {
            fun onTrackClick(track: Track)
        }
    }
}