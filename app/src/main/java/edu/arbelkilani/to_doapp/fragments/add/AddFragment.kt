package edu.arbelkilani.to_doapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.arbelkilani.to_doapp.R
import edu.arbelkilani.to_doapp.data.models.Priority
import edu.arbelkilani.to_doapp.data.models.ToDoData
import edu.arbelkilani.to_doapp.data.viewmodel.ToDoViewModel
import edu.arbelkilani.to_doapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        view.priority_spinner.onItemSelectedListener = sharedViewModel.listener
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun insertDataToDb() {
        val title = title_edit_text.text.toString()
        val priority = priority_spinner.selectedItem.toString()
        val description = description_edit_text.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val newData = ToDoData(0, title, sharedViewModel.parsePriority(priority), description)
            toDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Success !", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields !", Toast.LENGTH_SHORT)
                .show()
        }

    }
}