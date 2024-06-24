package com.aos.goodideacard.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentBackgroundBinding
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.enums.BackgroundType
import com.aos.goodideacard.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundFragment : BaseFragment() {
    private var _binding: FragmentBackgroundBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var appDataStoreManager: AppDataStoreManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBackgroundBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            appDataStoreManager.background.collect { code ->
                try {
                    when(code) {
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->  binding.themeOsSetting.isChecked = true //시스템 설정 모드
                        AppCompatDelegate.MODE_NIGHT_YES -> binding.themeDarkMode.isChecked = true //다크 모드
                        AppCompatDelegate.MODE_NIGHT_NO -> binding.themeLightMode.isChecked = true //라이트 모드
                    }
                    onRadioButtonClickedListener()
                } catch (e: Exception) {
                    //binding NPE 발생 : Fragment 라이프 사이클과 LiveData LifeCycleOwner 와 미세한 싱크 차이로 인해 간헐적으로 발생하는 것으로 추정
                    Timber.e("Exception:RadioButtonCheck:${e.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onRadioButtonClickedListener() {
        binding.themeRadioGroup.setOnCheckedChangeListener { /*radioGroup*/_, radioButtonId ->
            val appThem = when(radioButtonId) {
                R.id.theme_os_setting -> BackgroundType.SYSTEM //시스템 설정 모드
                R.id.theme_dark_mode -> BackgroundType.DARK //다크 모드
                R.id.theme_light_mode -> BackgroundType.DAY //라이트 모드
                else -> BackgroundType.SYSTEM
            }

            CoroutineScope(Dispatchers.IO).launch {
                appDataStoreManager.saveBackground(appThem)
            }
        }
    }
}