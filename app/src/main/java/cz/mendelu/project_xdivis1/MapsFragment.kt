package cz.mendelu.project_xdivis1

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import cz.mendelu.project_xdivis1.SessionListFragment.TasksAdapter
import cz.mendelu.project_xdivis1.architecture.BaseFragment
import cz.mendelu.project_xdivis1.databinding.FragmentMapsBinding
import cz.mendelu.project_xdivis1.di.repositoryModule
import cz.mendelu.project_xdivis1.model.Session


class MapsFragment : BaseFragment<FragmentMapsBinding, MapsViewModel>(MapsViewModel::class) {

    private val arguments: MapsFragmentArgs by navArgs()
    private val sessionsList: MutableList<Session> = mutableListOf()
    private lateinit var adapter: TasksAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var map: GoogleMap

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
                    //addMarkersToMap()
                    sessionsList.clear()
                    sessionsList.addAll(t!!)
                }

                /*
                private fun addMarkersToMap() {

                    val placeDetailsMap = mutableMapOf(
                        // Uses a coloured icon
                        "BRISBANE" to PlaceDetails(
                            position = places.getValue("BRISBANE"),
                            title = "Brisbane",
                            snippet = "Population: 2,074,200",
                            icon = BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        ),

                        // Uses a custom icon with the info window popping out of the center of the icon.
                        "SYDNEY" to PlaceDetails(
                            position = places.getValue("SYDNEY"),
                            title = "Sydney",
                            snippet = "Population: 4,627,300",
                            infoWindowAnchorX = 0.5f,
                            infoWindowAnchorY = 0.5f
                        ),

                        // Will create a draggable marker. Long press to drag.
                        "MELBOURNE" to PlaceDetails(
                            position = places.getValue("MELBOURNE"),
                            title = "Melbourne",
                            snippet = "Population: 4,137,400",
                            draggable = true
                        ),

                        // Use a vector drawable resource as a marker icon.
                        "ALICE_SPRINGS" to PlaceDetails(
                            position = places.getValue("ALICE_SPRINGS"),
                            title = "Alice Springs",
                        ),

                        // More markers for good measure
                        "PERTH" to PlaceDetails(
                            position = places.getValue("PERTH"),
                            title = "Perth",
                            snippet = "Population: 1,738,800"
                        ),

                        "ADELAIDE" to PlaceDetails(
                            position = places.getValue("ADELAIDE"),
                            title = "Adelaide",
                            snippet = "Population: 1,213,000"
                        )

                    )

                    // place markers for each of the defined locations
                    placeDetailsMap.keys.map {
                        with(placeDetailsMap.getValue(it)) {
                            map.addMarker(MarkerOptions()
                                .position(position)
                                .title(title)
                                .snippet(snippet)
                                .icon(icon)
                                .infoWindowAnchor(infoWindowAnchorX, infoWindowAnchorY)
                                .draggable(draggable)
                                .zIndex(zIndex))

                        }
                    }
                }
            })
    */
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
class PlaceDetails(
    val position: LatLng,
    val title: String = "Marker",
    val snippet: String? = null,
    val icon: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(),
    val infoWindowAnchorX: Float = 0.5F,
    val infoWindowAnchorY: Float = 0F,
    val draggable: Boolean = false,
    val zIndex: Float = 0F) {
}

    /** map to store place names and locations */
    private val places = mapOf(
        "BRISBANE" to LatLng(-27.47093, 153.0235),
        "MELBOURNE" to LatLng(-37.81319, 144.96298),
        "DARWIN" to LatLng(-12.4634, 130.8456),
        "SYDNEY" to LatLng(-33.87365, 151.20689),
        "ADELAIDE" to LatLng(-34.92873, 138.59995),
        "PERTH" to LatLng(-31.952854, 115.857342),
        "ALICE_SPRINGS" to LatLng(-24.6980, 133.8807)
    )


}



