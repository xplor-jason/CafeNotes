package xyz.cloudcoffee.atlas.screens.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.cloudcoffee.atlas.api.common.CloudObject
import xyz.cloudcoffee.atlas.databinding.ItemNotesBinding
import xyz.cloudcoffee.atlas.framework.Atlas
import xyz.cloudcoffee.atlas.model.NoteModel

class NotesAdapter(val notesViewModel: NotesViewModel) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>()
{
    var notesList : ArrayList<CloudObject<NoteModel>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflateNotes = ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NoteViewHolder(inflateNotes.root, inflateNotes)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val bindItem = notesList.get(position)
        holder.bindItem(bindItem)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
    fun setList(list : ArrayList<CloudObject<NoteModel>>){
        notesList = list
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView : View, val vbind : ItemNotesBinding) : RecyclerView.ViewHolder(itemView) {
        init {
            vbind.btnDelete.setOnClickListener {
                val removeNote = notesList.get(adapterPosition)
                removeNote.value {
                    Atlas.out("Removing: ${it.title}")
                    notesViewModel.removeNote(removeNote){
                        Atlas.out("Removed Note Success: ${it.title}")
                    }
                }
            }
        }

        fun bindItem(model : CloudObject<NoteModel>){
            model.value {
                vbind.textTitle.text =  it.title
                vbind.textNote.text =   it.note
            }
        }

    }
}