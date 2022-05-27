package cz.mendelu.project_xdivis1

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentAddSessionBinding
import cz.mendelu.project_xdivis1.extensions.round
import cz.mendelu.project_xdivis1.utils.DateUtils
import kotlinx.coroutines.launch
import java.util.*

class AddSessionFragment : BaseFragment<FragmentAddSessionBinding, AddSessionViewModel>(AddSessionViewModel::class) {

    private val arguments: AddSessionFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddSessionBinding
        get() = FragmentAddSessionBinding::inflate

    override fun initViews() {

        //TOTO jsme psali nově
        viewModel.id = if (arguments.id != -1L) arguments.id else null

        if (viewModel.id == null){
            //pridavam task
            fillLayout()
        }else{
            lifecycleScope.launch {
                viewModel.session = viewModel.findTaskById()
            }.invokeOnCompletion {      // na tohle bacha
                fillLayout()
            }
        }

        setInteractionListeners()


        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Double>("latitude")
            ?.observe(viewLifecycleOwner, {
            viewModel.session.latitude = it
            findNavController().currentBackStackEntry?.savedStateHandle?.remove<Double>("latitude")
            setLocation()
        })

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Double>("longitude")
            ?.observe(viewLifecycleOwner, {
                viewModel.session.latitude = it
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<Double>("longitude")
                setLocation()
            })
    }

    override fun onActivityCreated() {
    }


    private fun fillLayout(){
        if (viewModel.session.text.isNotEmpty()){
            binding.sessionName.text = viewModel.session.text
        }
        if (!viewModel.session.description.isNullOrEmpty()){
            binding.sessionDescription.text = viewModel.session.description!!
        }

        setDate()
        setLocation()

    }

    private fun setDate(){

        viewModel.session.date?.let{
            binding.sessionDate.value = DateUtils.getDateString(it)
            binding.sessionDate.showClearButton()
        }?: kotlin.run {
            binding.sessionDate.value = "Not set"
            binding.sessionDate.hideClearButton()
        }

    }

    fun setLocation(){
        if(viewModel.session.longitude != null && viewModel.session.latitude != null){
            binding.sessionLocation.value = "${viewModel.session.latitude!!.round()}; ${viewModel.session.longitude!!.round()}"
            binding.sessionLocation.showClearButton()
        }else{
            binding.sessionLocation.value = "Not set"
            binding.sessionLocation.hideClearButton()
        }
    }

    private fun setInteractionListeners(){

        binding.saveButton.setOnClickListener {
            if (binding.sessionName.text.isNotEmpty()){
                binding.sessionName.setError(null)
                lifecycleScope.launch {
                    viewModel.saveSession()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }

            } else {
                binding.sessionName.setError("Cannot be empty")
                binding.sessionDescription.setError("Cannot be empty")
            }
        }

        binding.sessionDate.setOnClickListener {
            openDatePickerDialog()
        }

        binding.sessionName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.session.text = p0.toString()
            }
        })

        binding.sessionDescription.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.session.description = p0.toString()
            }
        })



        binding.sessionDate.setOnClearClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                viewModel.session.date = null
                setDate()
            }
        })

        binding.sessionLocation.setOnClickListener{
            val directions = AddSessionFragmentDirections.actionAddSessionToMap()
            if(viewModel.session.latitude != null && viewModel.session.longitude != null){
                directions.latitude = viewModel.session.latitude!!.toFloat()
                directions.longitude = viewModel.session.longitude!!.toFloat()
            }
            findNavController().navigate(directions)

        }

        binding.sessionLocation.setOnClearClickListener({
            viewModel.session.latitude = null
            viewModel.session.longitude = null
            setLocation()
        })

    }

    private fun openDatePickerDialog(){

        val calendar = Calendar.getInstance()
        viewModel.session.date?.let {
            calendar.timeInMillis = it
        }

        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                viewModel.session.date = DateUtils.getUnixTime(year, month, day)
                setDate()
            }
        }, y, m, d)

        dialog.show()
    }



}