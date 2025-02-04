package com.example.todoapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.databinding.FragmentTaskListBinding
import java.util.Locale

class SettingsFragment: Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("Settings", 0)
        val editor = sharedPreferences.edit()

        val languages = resources.getStringArray(R.array.languages_array)
        val languageAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, languages)
        binding.autoCompleteTVLanguages.setAdapter(languageAdapter)

        val savedLanguage = sharedPreferences.getString("Selected_Language", languages[0])
        binding.autoCompleteTVLanguages.setText(savedLanguage, false)

        binding.autoCompleteTVLanguages.setOnItemClickListener { adapterView, view, position, l ->
            val selectedLanguage = languages[position]
            editor.putString("Selected_Language", selectedLanguage)
            editor.apply()
            setLocale(selectedLanguage)
        }

        val modes = resources.getStringArray(R.array.modes_array)
        val modeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, modes)
        binding.autoCompleteTVModes.setAdapter(modeAdapter)

        val savedMode = sharedPreferences.getString("Selected_Mode", modes[0])
        binding.autoCompleteTVModes.setText(savedMode, false)

        binding.autoCompleteTVModes.setOnItemClickListener { adapterView, view, position, l ->

            val selectedMode = modes[position]
            editor.putString("Selected_Mode", selectedMode)
            editor.apply()
            setAppMode(selectedMode)
        }

    }

    private fun setLocale(language: String) {
        val locale = Locale(languageCode(language))
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)
        requireActivity().recreate()
    }

    private fun languageCode(language: String): String {
        return when (language) {
            "English" -> "en"
            "Arabic" -> "ar"
            "French" -> "fr"
            "Spanish" -> "es"
            "German" -> "de"
            else -> "en"
        }
    }

    private fun setAppMode(mode: String) {
        when (mode) {
            "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "System Default" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}