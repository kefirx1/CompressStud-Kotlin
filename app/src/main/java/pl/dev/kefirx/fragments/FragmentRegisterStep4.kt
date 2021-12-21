package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register_end.*
import kotlinx.android.synthetic.main.fragment_register_step2.*
import kotlinx.android.synthetic.main.fragment_register_step4.*
import pl.dev.kefirx.R

class FragmentRegisterStep4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step4, container, false)
    }

    override fun onStart() {
        super.onStart()



        nextStep5Button.setOnClickListener{

            val favMusic = favMusicSpinner.selectedItem.toString()

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(favMusic)

            findNavController().navigate(
                FragmentRegisterStep4Directions.actionFragmentRegisterStep4ToFragmentRegisterEnd().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}