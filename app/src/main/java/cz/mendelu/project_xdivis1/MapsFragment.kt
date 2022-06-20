package cz.mendelu.project_xdivis1

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentMapsBinding


class MapsFragment : BaseFragment<FragmentMapsBinding, MapsViewModel>(MapsViewModel::class) {

    private val arguments: MapsFragmentArgs by navArgs()

    private val callback = OnMapReadyCallback { googleMap ->

        var position: LatLng
        if (viewModel.latitude != null && viewModel.longitude != null) {
            position = LatLng(viewModel.latitude!!, viewModel.longitude!!)
        } else {
            position = LatLng(49.21230328880893, 16.610911391240876)
        }

        val markerOptions = MarkerOptions().position(position).draggable(true)
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override val bindingInflater: (LayoutInflater) -> FragmentMapsBinding
        get() = FragmentMapsBinding::inflate

    override fun initViews() {

        setHasOptionsMenu(true) // na tohle bacha!!
        if (arguments.latitude != 0.0f && arguments.longitude != 0.0f) {
            viewModel.latitude = arguments.latitude.toDouble()
            viewModel.longitude = arguments.longitude.toDouble()
        }
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

}

