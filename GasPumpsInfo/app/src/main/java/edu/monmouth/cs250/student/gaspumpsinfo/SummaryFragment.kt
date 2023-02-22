package edu.monmouth.cs250.student.gaspumpsinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.monmouth.cs250.student.gaspumpsinfo.databinding.FragmentSummaryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var binding: FragmentSummaryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentSummaryBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allPumps = Pump.getAllPumps()
        val total = allPumps.count()

        val UPPERCUTOFF = 40.47368
        val LOWERCUTOFF = 39.95219
        val north = allPumps.filter { it.lat > UPPERCUTOFF }.count()
        val central = allPumps.filter { it.lat > LOWERCUTOFF && it.lat < UPPERCUTOFF }.count()
        val south = allPumps.filter { it.lat < LOWERCUTOFF }.count()

        val totalStations = view.findViewById<TextView>(R.id.total_stations)
        val northStations = view.findViewById<TextView>(R.id.north_stations)
        val centralStations = view.findViewById<TextView>(R.id.central_stations)
        val southStations = view.findViewById<TextView>(R.id.south_stations)

        totalStations.text = getString(R.string.total_gas_stations, total)
        northStations.text = getString(R.string.north_gas_stations, north)
        centralStations.text = getString(R.string.central_gas_stations, central)
        southStations.text = getString(R.string.south_gas_stations, south)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}