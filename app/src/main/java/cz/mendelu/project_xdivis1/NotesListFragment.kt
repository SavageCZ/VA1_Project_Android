package cz.mendelu.project_xdivis1

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentNotesListBinding
import cz.mendelu.project_xdivis1.databinding.RowNoteListBinding
import cz.mendelu.project_xdivis1.model.Notes

class NotesListFragment : BaseFragment<FragmentNotesListBinding, NotesListViewModel>
    (NotesListViewModel::class) {


    override val bindingInflater: (LayoutInflater) -> FragmentNotesListBinding
        get() =  FragmentNotesListBinding::inflate

    override fun initViews() {


        binding.fabNote.setOnClickListener{
            findNavController().navigate(NotesListFragmentDirections.actionNotesFragmentToAddNotesFragment())
        }
    }

    override fun onActivityCreated() {

    }
}