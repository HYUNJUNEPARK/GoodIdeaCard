package com.aos.goodideacard.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentLanguageBinding
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.enums.Language
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.features.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LanguageFragment : BaseFragment() {
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var appDataStoreManager: AppDataStoreManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserSettingOnRadioButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkUserSettingOnRadioButton() = CoroutineScope(Dispatchers.IO).launch {
        val language = appDataStoreManager.getLanguage()

        withContext(Dispatchers.Main) {
            when(language) {
                Language.DEFAULT -> binding.languageBtnSystemLanguage.isChecked = true
                Language.KOREAN -> binding.languageBtnKorean.isChecked = true
                Language.ENGLISH -> binding.languageBtnEnglish.isChecked = true
            }
            onRadioButtonClicked()
        }
    }

    private fun onRadioButtonClicked() {
        binding.languageRadioGroup.setOnCheckedChangeListener { /*radioGroup*/_, id ->
            val language = when(id) {
                R.id.language_btn_system_language -> Language.DEFAULT
                R.id.language_btn_korean -> Language.KOREAN
                R.id.language_btn_english -> Language.ENGLISH
                else -> {
                    Timber.e("Exception:Not handling language btn id($id")
                    Language.DEFAULT
                }
            }

            CoroutineScope(Dispatchers.IO).launch {
                appDataStoreManager.saveLanguage(language)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "$language", Toast.LENGTH_SHORT).show()
                    (requireActivity() as MainActivity).changeLanguage(language)
                }
            }
        }
    }
}