package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterStep4Binding

class FragmentRegisterStep4 : Fragment() {

    private lateinit var binding: FragmentRegisterStep4Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()



        binding.nextStep5Button.setOnClickListener{

            val favMusic = binding.favMusicSpinner.selectedItem.toString()

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(favMusic)

            findNavController().navigate(
                FragmentRegisterStep4Directions.actionFragmentRegisterStep4ToFragmentRegisterEnd().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}