package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register_step1.*
import kotlinx.android.synthetic.main.fragment_register_step1.nextStep2Button
import kotlinx.android.synthetic.main.fragment_register_step2.*
import pl.dev.kefirx.R

class FragmentRegisterStep2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_step2, container, false)
    }

    override fun onStart() {
        super.onStart()


        nextStep3Button.setOnClickListener{

            val levelOfEdu = levelSpinner.selectedItem.toString()

            val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
            personalInformation.add(levelOfEdu)

            findNavController().navigate(
                FragmentRegisterStep2Directions.actionFragmentRegisterStep2ToFragmentRegisterStep3().actionId,
                bundleOf("info" to personalInformation))
        }

    }
}