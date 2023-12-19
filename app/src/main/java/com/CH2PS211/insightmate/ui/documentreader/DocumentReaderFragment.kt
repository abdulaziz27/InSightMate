package com.CH2PS211.insightmate.ui.documentreader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.CH2PS211.insightmate.databinding.FragmentDocumentReaderBinding

class DocumentReaderFragment : Fragment() {

    private var _binding: FragmentDocumentReaderBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val documentReaderViewModel =
            ViewModelProvider(this).get(DocumentReaderViewModel::class.java)

        _binding = FragmentDocumentReaderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        documentReaderViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}