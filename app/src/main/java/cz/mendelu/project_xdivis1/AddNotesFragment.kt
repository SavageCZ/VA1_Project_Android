package cz.mendelu.project_xdivis1

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentAddNotesBinding
import cz.mendelu.project_xdivis1.databinding.FragmentNotesBinding
import cz.mendelu.project_xdivis1.databinding.FragmentNotesListBinding
import cz.mendelu.project_xdivis1.databinding.RowNoteListBinding
import cz.mendelu.project_xdivis1.model.UsedExercise
import kotlinx.coroutines.launch

class AddNotesFragment :
    BaseFragment<FragmentAddNotesBinding, AddNotesViewModel>(AddNotesViewModel::class) {


    override val bindingInflater: (LayoutInflater) -> FragmentAddNotesBinding
        get() = FragmentAddNotesBinding::inflate


    override fun initViews() {

    }



    override fun onActivityCreated() {
    }
}