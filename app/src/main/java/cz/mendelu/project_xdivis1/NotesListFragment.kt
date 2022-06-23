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
import cz.mendelu.project_xdivis1.model.Session

class NotesListFragment : BaseFragment<FragmentNotesListBinding, NotesListViewModel>
    (NotesListViewModel::class) {

    private val planList: MutableList<Notes> = mutableListOf()
    private lateinit var adapter: PlansAdapter
    private lateinit var layoutManager: LinearLayoutManager

    inner class PlanDiffUtils(private val oldList: MutableList<Notes>,
                              private val newList: MutableList<Notes>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].noteName == newList[newItemPosition].noteName
        }
    }
    //ADAPTER
    inner class PlansAdapter : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>(){

        inner class PlanViewHolder(val binding: RowNoteListBinding):
            RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
            return PlanViewHolder(
                RowNoteListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
            val plan = planList.get(position)
            holder.binding.noteName.text = plan.noteName

            holder.binding.root.setOnClickListener {
                val action = NotesListFragmentDirections.actionNotesFragmentToAddNotesFragment()
                action.idPlan = planList.get(holder.adapterPosition).id!!
                findNavController().navigate(action)
            }
        }

        override fun getItemCount(): Int = planList.size
    }

    override val bindingInflater: (LayoutInflater) -> FragmentNotesListBinding
        get() =  FragmentNotesListBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = PlansAdapter()
        binding.noteList.layoutManager = layoutManager
        binding.noteList.adapter = adapter

        binding.fabNote.setOnClickListener {
            findNavController().navigate(NotesListFragmentDirections.actionNotesFragmentToAddNotesFragment())
        }

        viewModel
            .getPlanAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Notes>>{
                override fun onChanged(t: MutableList<Notes>?) {
                    val diffCallback = PlanDiffUtils(planList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    planList.clear()
                    planList.addAll(t!!)
                }

            })


    }

    override fun onActivityCreated() {

    }
}