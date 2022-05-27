package cz.mendelu.project_xdivis1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentSessionListBinding
import cz.mendelu.project_xdivis1.databinding.RowSessionListBinding
import cz.mendelu.project_xdivis1.model.Session
import kotlinx.coroutines.launch

class SessionListFragment :
    BaseFragment<FragmentSessionListBinding, SessionListViewModel>
        (SessionListViewModel::class) {

    private val sessionsList: MutableList<Session> = mutableListOf()
    private lateinit var adapter: TasksAdapter
    private lateinit var layoutManager: LinearLayoutManager


    inner class TaskDiffUtils(private val oldList: MutableList<Session>,
                              private val newList: MutableList<Session>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].text == newList[newItemPosition].text &&
                    return oldList[oldItemPosition].description == newList[newItemPosition].description
        }


    }


    inner class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>(){



        inner class TaskViewHolder(val binding: RowSessionListBinding)
            : RecyclerView.ViewHolder(binding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            return TaskViewHolder(
                RowSessionListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = sessionsList.get(position)
            holder.binding.taskName.text = task.text

            if(!task.description.isNullOrEmpty()){
                holder.binding.taskDescription.text = task.description
                holder.binding.taskDescription.visibility = View.VISIBLE
            }else{
                holder.binding.taskDescription.visibility = View.GONE
            }

            //holder.binding.checkbox.isChecked = task.done

            /*holder.binding.checkbox.setOnCheckedChangeListener(
                object : CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                        lifecycleScope.launch {
                            viewModel.updateTaskStatus(sessionsList.get(holder.adapterPosition).id!!, p1)
                        }

                    }


                }

             */

            //TOTO jsme dopisovali -> dej si na to bacha kokůtku!!
            holder.binding.root.setOnClickListener {

                val actions = SessionListFragmentDirections.actionListToAddSession()
                actions.id = sessionsList.get(holder.adapterPosition).id!!

                findNavController().navigate(actions)

            }

        }

        override fun getItemCount(): Int = sessionsList.size

    }

    override val bindingInflater: (LayoutInflater) -> FragmentSessionListBinding
        get() = FragmentSessionListBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = TasksAdapter()
        binding.sessionList.layoutManager = layoutManager
        binding.sessionList.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(SessionListFragmentDirections.actionListToAddSession())
        }

        viewModel
            .getAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Session>>{
                override fun onChanged(t: MutableList<Session>?) {
                    val diffCallback = TaskDiffUtils(sessionsList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    sessionsList.clear()
                    sessionsList.addAll(t!!)
                }

            })

    }

    override fun onActivityCreated() {

    }

}