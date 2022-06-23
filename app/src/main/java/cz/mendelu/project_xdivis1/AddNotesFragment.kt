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

    private val arguments: AddNotesFragmentArgs by navArgs()

    private val usedExerciseList: MutableList<UsedExercise> = mutableListOf()

    // private val actualExercise: MutableList<UsedExercise> = usedExerciseList[arguments.planId.toInt()].usedExercises


    private lateinit var adapter: PlanAdapter
    private lateinit var layoutManager: LinearLayoutManager

    //diff
    inner class PlanDiffUtils(private val oldList: MutableList<UsedExercise>,
                              private val newList: MutableList<UsedExercise>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].usedExerciseName == newList[newItemPosition].usedExerciseName
        }
    }
    //adapter
    inner class PlanAdapter : RecyclerView.Adapter<PlanAdapter.ExerciseViewHolder>(){

        inner class ExerciseViewHolder(val binding: RowNoteListBinding): RecyclerView.ViewHolder(binding.root){}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder(
                RowNoteListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {

            holder.binding.noteName.text = usedExerciseList[position].usedExerciseName

            holder.binding.root.setOnClickListener {
                val action = AddNotesFragmentDirections.actionAddNotesFragmentToNotesFragment()
                // action.id = usedExerciseList.get(holder.adapterPosition).usedExercises[position].id!!
                //action.id = usedExerciseList.get(holder.adapterPosition).id!!
                //action.IdPlan = arguments.planId
                findNavController().navigate(action)
            }
        }
        override fun getItemCount(): Int = usedExerciseList.size
    }


    override val bindingInflater: (LayoutInflater) -> FragmentAddNotesBinding
        get() = FragmentAddNotesBinding::inflate


    override fun initViews() {

        layoutManager = LinearLayoutManager(requireContext())
        adapter = PlanAdapter()
        binding.usedExerciseList.layoutManager = layoutManager
        binding.usedExerciseList.adapter = adapter


        viewModel.id = if(arguments.idPlan != -1L) arguments.idPlan
        else null
        if(viewModel.id == null){
            fillLayout()
        }else{
            viewModel
                .getUsedExercise()
                .observe(viewLifecycleOwner, object : Observer<MutableList<UsedExercise>>
                {
                    override fun onChanged(t: MutableList<UsedExercise>?){
                        val diffCallback = PlanDiffUtils(usedExerciseList, t!!)
                        val diffResult = DiffUtil.calculateDiff(diffCallback)
                        diffResult.dispatchUpdatesTo(adapter)
                        usedExerciseList.clear()
                        usedExerciseList.addAll(t)
                    }

                })
            lifecycleScope.launch {
                viewModel.plan = viewModel.findPlanById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }
        setInteractionListeners()

    }
    private fun fillLayout(){
        if (viewModel.plan.noteName.isNotEmpty()){
            binding.noteName.text = viewModel.plan.noteName
        }
    }
    private fun setInteractionListeners(){
        binding.noteName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.plan.noteName = p0.toString()
            }

        })

        binding.saveNoteButton.setOnClickListener {
            if(binding.noteName.text.isNotEmpty()){
                binding.noteName.setError(null)
                lifecycleScope.launch {
                    viewModel.savePlan()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }
            }else{
                binding.noteName.setError("Cannot be empty")
            }
        }
    }




    override fun onActivityCreated() {
    }
}