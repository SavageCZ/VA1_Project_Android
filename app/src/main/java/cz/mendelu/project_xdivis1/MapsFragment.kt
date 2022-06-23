package cz.mendelu.project_xdivis1

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat.setContentDescription
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import cz.mendelu.project_xdivis1.SessionListFragment.TasksAdapter
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentMapsBinding
import cz.mendelu.project_xdivis1.databinding.FragmentAddSessionBinding
import cz.mendelu.project_xdivis1.databinding.RowSessionListBinding
import cz.mendelu.project_xdivis1.model.Session


class MapsFragment : BaseFragment<FragmentMapsBinding, MapsViewModel>(MapsViewModel::class) {

    private val arguments: MapsFragmentArgs by navArgs()
    private lateinit var map: GoogleMap
    private val sessionsList: MutableList<Session> = mutableListOf()
    private lateinit var adapter: TasksAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private val callback = OnMapReadyCallback { googleMap ->


        var position: LatLng
        if (viewModel.latitude != null && viewModel.longitude != null) {
            position = LatLng(viewModel.latitude!!, viewModel.longitude!!)
        } else {
            position = LatLng(49.21230328880893, 16.610911391240876)
        }

        val markerOptions = MarkerOptions().position(position).draggable(true).title("marker")
        val marker = googleMap.addMarker(markerOptions)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10.0f))


        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                viewModel.latitude = p0.position.latitude
                viewModel.longitude = p0.position.longitude
                viewModel.locationChanged = true

            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })


        googleMap.setOnMarkerClickListener (object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker): Boolean {
                findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToNotesFragment())

                return false
            }

        })



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override val bindingInflater: (LayoutInflater) -> FragmentMapsBinding
        get() = FragmentMapsBinding::inflate


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




    override fun initViews() {

        setHasOptionsMenu(true) // na tohle bacha!!
        if (arguments.latitude != 0.0f && arguments.longitude != 0.0f) {
            viewModel.latitude = arguments.latitude.toDouble()
            viewModel.longitude = arguments.longitude.toDouble()
        }


        viewModel
            .getAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Session>> {
                override fun onChanged(t: MutableList<Session>?) {
                    sessionsList.clear()
                    sessionsList.addAll(t!!)
                }
            })



    }

    override fun onActivityCreated() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_map, menu) // na tohle bacha
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //připravený kód pro více tlačítek v mapě
        return when (item.itemId) {
            R.id.action_save -> {
                findNavController().previousBackStackEntry
                    ?.savedStateHandle?.set("latitude", viewModel.latitude)
                findNavController().previousBackStackEntry
                    ?.savedStateHandle?.set("longitude", viewModel.longitude)
                finishCurrentFragment()

                //  do projektu - tlačítka
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
//on MarkerClick + přes observer ty markery....




}

