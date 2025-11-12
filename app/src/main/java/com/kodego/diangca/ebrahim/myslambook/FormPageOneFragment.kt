package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageOneBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook
// Inalis ko ang 'java.text.SimpleDateFormat' dahil hindi na ito kailangan.

class FormPageOneFragment : Fragment() {

    private lateinit var binding: FragmentFormPageOneBinding
    private lateinit var slamBook: SlamBook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFormPageOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {

        slamBook = arguments?.getParcelable("slamBook") ?: SlamBook()


        if (slamBook.id != 0 || slamBook.nickName != null) {
            restoreField()
        }

        with(binding) {
            btnBack.setOnClickListener { btnBackOnClickListener() }
            btnNext.setOnClickListener { btnNextOnClickListener() }
            dateMonth.prompt = "Please enter Birth Month"
            dateDay.prompt = "Please enter Birth day"
            gender.prompt = "Please enter Gender"
            status.prompt = "Please enter Status"
        }
    }

    private fun restoreField() {
        binding.apply {

            nickName.setText(slamBook.nickName)
            friendCall.setText(slamBook.friendCall)
            likeToCall.setText(slamBook.likeToCall)


            slamBook.fullName?.let {
                val names = it.split(" ", limit = 2)
                if (names.isNotEmpty()) firstName.setText(names.getOrNull(0))
                if (names.size > 1) lastName.setText(names.getOrNull(1))
            }


            slamBook.birthDate?.let { dateString ->
                val dateParts = dateString.replace(",", "").split(" ")
                if (dateParts.size == 3) {
                    val month = dateParts[0]
                    val day = dateParts[1]
                    val year = dateParts[2]

                    val arrayMonth = resources.getStringArray(R.array.monthName)
                    val arrayDay = resources.getStringArray(R.array.monthDay)

                    dateMonth.setSelection(arrayMonth.indexOf(month))
                    dateDay.setSelection(arrayDay.indexOf(day))
                    dateYear.setText(year)
                }
            }

            slamBook.gender?.let {
                val arrayGender = resources.getStringArray(R.array.gender)
                gender.setSelection(arrayGender.indexOf(it))
            }

            slamBook.status?.let {
                val arrayStatus = resources.getStringArray(R.array.status)
                status.setSelection(arrayStatus.indexOf(it))
            }

            // Gumamit ng mga variable name na tumutugma sa SlamBook.kt
            emailAdd.setText(slamBook.emailAdd)
            contactNo.setText(slamBook.contactNo)
            address.setText(slamBook.address)
        }
    }

    private fun btnNextOnClickListener() {
        if (!validateInputs()) {
            Snackbar.make(binding.root, "Please fill up all fields", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Pagsamahin ang first at last name sa iisang 'fullName'
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        slamBook.fullName = "$firstName $lastName"

        // I-save ang lahat ng data sa slamBook object
        slamBook.nickName = binding.nickName.text.toString()
        slamBook.friendCall = binding.friendCall.text.toString()
        slamBook.likeToCall = binding.likeToCall.text.toString()
        slamBook.birthDate = "${binding.dateMonth.selectedItem} ${binding.dateDay.selectedItem}, ${binding.dateYear.text}"
        slamBook.gender = binding.gender.selectedItem.toString()
        slamBook.status = binding.status.selectedItem.toString()
        slamBook.emailAdd = binding.emailAdd.text.toString()
        slamBook.contactNo = binding.contactNo.text.toString()
        slamBook.address = binding.address.text.toString()

        Log.d("FORM 1", "Data saved for: ${slamBook.fullName}")


        val bundle = Bundle()
        bundle.putParcelable("slamBook", slamBook)
        findNavController().navigate(R.id.action_formPageOneFragment_to_formPageTwoFragment, bundle)
    }


    private fun validateInputs(): Boolean {
        var isValid = true

        with(binding) {
            if (nickName.text.isNullOrEmpty()) {
                nickName.error = "Required"; isValid = false
            }
            if (friendCall.text.isNullOrEmpty()) {
                friendCall.error = "Required"; isValid = false
            }
            if (likeToCall.text.isNullOrEmpty()) {
                likeToCall.error = "Required"; isValid = false
            }
            if (lastName.text.isNullOrEmpty()) {
                lastName.error = "Required"; isValid = false
            }
            if (firstName.text.isNullOrEmpty()) {
                firstName.error = "Required"; isValid = false
            }
            if (dateYear.text.isNullOrEmpty()) {
                dateYear.error = "Required"; isValid = false
            }
            if (emailAdd.text.isNullOrEmpty()) {
                emailAdd.error = "Required"; isValid = false
            }
            if (contactNo.text.isNullOrEmpty()) {
                contactNo.error = "Required"; isValid = false
            }
            if (address.text.isNullOrEmpty()) {
                address.error = "Required"; isValid = false
            }

            if (dateMonth.selectedItemPosition == 0 || dateDay.selectedItemPosition == 0 || gender.selectedItemPosition == 0 || status.selectedItemPosition == 0) {
                isValid = false
            }
        }
        return isValid
    }

    private fun btnBackOnClickListener() {
        val intent = Intent(requireContext(), MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
