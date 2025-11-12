package com.kodego.diangca.ebrahim.myslambook

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.preference.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.kodego.diangca.ebrahim.myslambook.adapter.SimpleStringAdapter
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageTwoBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook

class FormPageTwoFragment : Fragment() {

    private lateinit var binding: FragmentFormPageTwoBinding
    private lateinit var slamBook: SlamBook


    private val songs = ArrayList<String>()
    private val movies = ArrayList<String>()
    private val hobbies = ArrayList<String>()
    private val skills = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFormPageTwoBinding.inflate(inflater, container, false)

        slamBook = arguments?.getParcelable("slamBook") ?: SlamBook()
        Log.d("FormPageTwo", "SlamBook received for: ${slamBook.fullName}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        restoreFields()
    }

    private fun initComponents() {

        setupRecyclerView(binding.favSongList, songs)
        setupRecyclerView(binding.favMovieList, movies)
        setupRecyclerView(binding.hobbiesList, hobbies)
        setupRecyclerView(binding.skillList, skills)

        binding.skillRate.setSelection(0)


        binding.btnAddFavSong.setOnClickListener {
            addToList(songs, binding.songName, binding.favSongList, "Song")
        }
        binding.btnAddFavMov.setOnClickListener {
            addToList(movies, binding.movieName, binding.favMovieList, "Movie")
        }
        binding.btnAddHobbies.setOnClickListener {
            addToList(hobbies, binding.hobbies, binding.hobbiesList, "Hobby")
        }
        binding.btnAddSkill.setOnClickListener {
            addSkillToList()
        }

        binding.btnBack.setOnClickListener { btnBackOnClickListener() }
        binding.btnNext.setOnClickListener { btnNextOnClickListener() }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                btnBackOnClickListener()
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, dataList: ArrayList<String>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SimpleStringAdapter(dataList)
    }

    private fun addToList(list: ArrayList<String>, editText: TextInputEditText, recyclerView: RecyclerView, type: String) {
        val text = editText.text.toString().trim()
        if (text.isEmpty()) {
            Snackbar.make(binding.root, "Please enter a $type.", Snackbar.LENGTH_SHORT).show()
            return
        }
        list.add(text)
        recyclerView.adapter?.notifyItemInserted(list.size - 1)
        editText.text?.clear()
        hideKeyboard(editText)
    }

    private fun addSkillToList() {
        val skillText = binding.skill.text.toString().trim()
        val skillRatePosition = binding.skillRate.selectedItemPosition

        if (skillText.isNotEmpty() && skillRatePosition > 0) {
            val formattedSkill = "$skillText (${binding.skillRate.selectedItem})"
            skills.add(formattedSkill)
            binding.skillList.adapter?.notifyItemInserted(skills.size - 1)
            binding.skill.text?.clear()
            binding.skillRate.setSelection(0)
            hideKeyboard(binding.skill)
        } else {
            Snackbar.make(binding.root, "Please enter a skill and select a rate.", Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun restoreFields() {

        slamBook.favoriteSongs?.let { if (it.isNotEmpty()) songs.addAll(it) }
        slamBook.favoriteMovies?.let { if (it.isNotEmpty()) movies.addAll(it) }
        slamBook.hobbies?.let { if (it.isNotEmpty()) hobbies.addAll(it) }
        slamBook.skillsWithRate?.let { if (it.isNotEmpty()) skills.addAll(it) }

        // I-notify ang lahat ng adapters na nagbago ang data
        binding.favSongList.adapter?.notifyDataSetChanged()
        binding.favMovieList.adapter?.notifyDataSetChanged()
        binding.hobbiesList.adapter?.notifyDataSetChanged()
        binding.skillList.adapter?.notifyDataSetChanged()
    }

    private fun btnNextOnClickListener() {
        if (songs.isEmpty() || movies.isEmpty() || hobbies.isEmpty() || skills.isEmpty()) {
            Snackbar.make(binding.root, "Please add at least one item to each category.", Snackbar.LENGTH_SHORT).show()
            return
        }


        slamBook.favoriteSongs = songs
        slamBook.favoriteMovies = movies
        slamBook.hobbies = hobbies
        slamBook.skillsWithRate = skills

        Log.d("FormPageTwo", "Navigating to Page Three. Data: $slamBook")

        val bundle = Bundle()
        bundle.putParcelable("slamBook", slamBook)
        findNavController().navigate(R.id.action_formPageTwoFragment_to_formPageThreeFragment, bundle)
    }

    private fun btnBackOnClickListener() {

        slamBook.favoriteSongs = songs
        slamBook.favoriteMovies = movies
        slamBook.hobbies = hobbies
        slamBook.skillsWithRate = skills

        val bundle = Bundle()
        bundle.putParcelable("slamBook", slamBook)
        findNavController().navigate(R.id.action_formPageTwoFragment_to_formPageOneFragment, bundle)
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
