package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.dev.kefirx.R
import pl.dev.kefirx.databinding.FragmentRegisterStep3Binding

class FragmentRegisterStep3 : Fragment() {

    private lateinit var binding: FragmentRegisterStep3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.nextStep4Button.setOnClickListener{

            val likeMusic = binding.musicSwitch.isChecked

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(likeMusic.toString())

            if(likeMusic){
                findNavController().navigate(
                    FragmentRegisterStep3Directions.actionFragmentRegisterStep3ToFragmentRegisterStep4().actionId,
                    bundleOf("info" to personalInformation))
            }else{
                personalInformation.add("")
                findNavController().navigate(
                    FragmentRegisterStep3Directions.actionFragmentRegisterStep3ToFragmentRegisterEnd().actionId,
                    bundleOf("info" to personalInformation))
            }


        }

    }
}