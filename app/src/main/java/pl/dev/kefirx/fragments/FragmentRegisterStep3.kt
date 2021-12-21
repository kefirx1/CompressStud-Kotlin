package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register_step3.*
import pl.dev.kefirx.R

class FragmentRegisterStep3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step3, container, false)
    }

    override fun onStart() {
        super.onStart()

        nextStep4Button.setOnClickListener{

            val likeMusic = musicSwitch.isChecked.toString()

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(likeMusic)

            findNavController().navigate(
                FragmentRegisterStep3Directions.actionFragmentRegisterStep3ToFragmentRegisterStep4().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}