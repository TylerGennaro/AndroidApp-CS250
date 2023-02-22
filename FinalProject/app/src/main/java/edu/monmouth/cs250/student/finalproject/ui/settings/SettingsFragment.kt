package edu.monmouth.cs250.student.finalproject.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import edu.monmouth.cs250.student.finalproject.R
import edu.monmouth.cs250.student.finalproject.User
import edu.monmouth.cs250.student.finalproject.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var _binding: FragmentSettingsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = arrayOf("Blue", "Red", "Orange", "Purple", "Yellow", "Green", "Black", "Grey")

        val spinner = binding.spinnerColor
        val ctx = this.requireContext()
        val arrayAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, colors)
        spinner.adapter = arrayAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (colors[position] != User.textColor) {
                    Toast.makeText(ctx, getString(R.string.selected_item, colors[position]), Toast.LENGTH_SHORT).show()
                    User.textColor = colors[position]
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}