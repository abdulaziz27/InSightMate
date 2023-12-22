package com.CH2PS211.insightmate.ui.documentreader

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
import com.CH2PS211.insightmate.R
import com.CH2PS211.insightmate.ViewModelFactory
import com.CH2PS211.insightmate.data.ResultState
import com.CH2PS211.insightmate.data.di.Injection
import com.CH2PS211.insightmate.databinding.FragmentDocumentReaderBinding
import com.CH2PS211.insightmate.util.getImageUri
import com.CH2PS211.insightmate.util.reduceFileImage
import com.CH2PS211.insightmate.util.uriToFile

class DocumentReaderFragment : Fragment() {

    private var _binding: FragmentDocumentReaderBinding? = null

    private val binding get() = _binding!!

    private val documentReaderViewModel by viewModels<DocumentReaderViewModel> {
        ViewModelFactory(
            moneyRepository = Injection.provideMoneyRepository(),
            colorRepository = Injection.provideColorRepository(),
            documentRepository = Injection.provideDocumentRepository()
        )
    }

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentReaderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cameraButton.contentDescription = getString(R.string.cameraDescription)
        binding.cameraXButton.contentDescription = getString(R.string.cameraXDescription)
        binding.galleryButton.contentDescription = getString(R.string.galleryDescription)
        binding.uploadButton.contentDescription = getString(R.string.uploadDescription)
        binding.previewImageView.contentDescription = getString(R.string.previewDescription)
        binding.textViewLabelHasil.contentDescription = getString(R.string.docPredictionResultDescription)

        binding.cameraButton.setOnClickListener { startCamera() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadDocumentImage() }

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
        if (it.resultCode == CameraActivity.CAMERAX_RESULT) {
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

    private fun uploadDocumentImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            documentReaderViewModel.uploadDocumentImage(imageFile)?.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showToast(getString(R.string.success_upload))
                            showLoading(false)
                            showResult(result.data.hasilTeks)

                            binding.textViewHasil.visibility = View.VISIBLE
                        }

                        is ResultState.Error -> {
                            if (result.error == "no file") {
                                showToast(getString(R.string.nofile_upload))
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
        binding.textViewHasil.text = "$result"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}