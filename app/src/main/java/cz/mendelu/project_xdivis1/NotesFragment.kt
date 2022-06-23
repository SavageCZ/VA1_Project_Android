package cz.mendelu.project_xdivis1

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentNotesBinding
import cz.mendelu.project_xdivis1.databinding.FragmentSessionListBinding
import cz.mendelu.project_xdivis1.model.Session

class NotesFragment : BaseFragment<FragmentNotesBinding, NotesViewModel>
    (NotesViewModel::class) {
    override val bindingInflater: (LayoutInflater) -> FragmentNotesBinding
        get() =  FragmentNotesBinding::inflate

    override fun initViews() {

    }

    override fun onActivityCreated() {

    }
}