package com.CH2PS211.insightmate.ui.moneydetector

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.CH2PS211.insightmate.CameraActivity
import com.CH2PS211.insightmate.CameraActivity.Companion.CAMERAX_RESULT
import com.CH2PS211.insightmate.R
import com.CH2PS211.insightmate.ViewModelFactory
import com.CH2PS211.insightmate.data.ResultState
import com.CH2PS211.insightmate.databinding.FragmentMoneyDetectorBinding
import com.CH2PS211.insightmate.util.getImageUri
import com.CH2PS211.insightmate.util.reduceFileImage
import com.CH2PS211.insightmate.util.uriToFile

class MoneyDetectorFragment : Fragment() {

    private var _binding: FragmentMoneyDetectorBinding? = null
    private val binding get() = _binding!!

    private val moneyDetectorViewModel by viewModels<MoneyDetectorViewModel> {
        ViewModelFactory.getInstance()
    }

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoneyDetectorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cameraButton.setOnClickListener { startCamera() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }

        binding.textViewHasil.visibility = View.GONE

        return root
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
            binding.textViewHasil.visibility = View.GONE
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            moneyDetectorViewModel.uploadImage(imageFile)?.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showToast("Hasilnya adalah ${result.data.featureNominal.prediksiNominal}")
                            showLoading(false)
                            showResult(result.data.featureNominal.prediksiNominal)

                            binding.textViewHasil.visibility = View.VISIBLE
                        }

                        is ResultState.Error -> {
                            if (result.error == "no file") {
                                showToast("Tidak ada file yang diunggah")
                            } else {
                                showToast(result.error)
                            }
                            showLoading(false)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(result: String) {
        binding.textViewHasil.text = "Hasilnya adalah $result"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}