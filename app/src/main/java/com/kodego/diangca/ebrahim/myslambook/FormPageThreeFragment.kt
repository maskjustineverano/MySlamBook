package com.kodego.diangca.ebrahim.myslambook

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageThreeBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook
import com.kodego.diangca.ebrahim.myslambook.model.SlamBookViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FormPageThreeFragment : Fragment() {

    private lateinit var binding: FragmentFormPageThreeBinding
    private lateinit var slamBook: SlamBook
    private lateinit var slamBookViewModel: SlamBookViewModel
    private var imageUri: Uri? = null

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Snackbar.make(binding.root, "Camera permission is required to take pictures.", Snackbar.LENGTH_SHORT).show()
            }
        }

    private val requestStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Snackbar.make(binding.root, "Storage permission is required to browse images.", Snackbar.LENGTH_SHORT).show()
            }
        }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.profileImageView.setImageURI(imageUri)
            slamBook.imageUri = imageUri.toString()
        }
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                imageUri = selectedImageUri
                binding.profileImageView.setImageURI(imageUri)
                slamBook.imageUri = imageUri.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            slamBook = ((arguments?.getParcelable("slamBook") as SlamBook?)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormPageThreeBinding.inflate(layoutInflater, container, false)
        slamBookViewModel = ViewModelProvider(this).get(SlamBookViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        binding.btnCamera.setOnClickListener { checkCameraPermission() }
        binding.btnBrowse.setOnClickListener { checkStoragePermission() }
        binding.btnBack.setOnClickListener { btnBackOnClickListener() }
        binding.btnSave.setOnClickListener { btnSaveOnClickListener() }

        if (slamBook.imageUri.isNotEmpty()) {
            imageUri = Uri.parse(slamBook.imageUri)
            binding.profileImageView.setImageURI(imageUri)
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun checkStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }
            else -> {
                requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also { file ->
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider",
                    file
                )
                imageUri = photoURI
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureLauncher.launch(takePictureIntent)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun btnSaveOnClickListener() {
        if (binding.defineLove.text.isNullOrEmpty() ||
            binding.defineFriendship.text.isNullOrEmpty() ||
            binding.memorableExperience.text.isNullOrEmpty() ||
            binding.describeMe.text.isNullOrEmpty() ||
            binding.adviceForMe.text.isNullOrEmpty()
        ) {
            Snackbar.make(binding.root, "Please fill out all fields.", Snackbar.LENGTH_SHORT).show()
            return
        }

        slamBook.defineLove = binding.defineLove.text.toString()
        slamBook.defineFriendship = binding.defineFriendship.text.toString()
        slamBook.memorableExperience = binding.memorableExperience.text.toString()
        slamBook.describeMe = binding.describeMe.text.toString()
        slamBook.adviceForMe = binding.adviceForMe.text.toString()
        slamBook.rating = binding.ratingBar.rating

        slamBookViewModel.insertSlamBook(slamBook)

        Snackbar.make(binding.root, "Slam book entry saved!", Snackbar.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun btnBackOnClickListener() {
        val bundle = Bundle()
        bundle.putParcelable("slamBook", slamBook)
        findNavController().navigate(R.id.action_formPageThreeFragment_to_formPageTwoFragment, bundle)
    }
}