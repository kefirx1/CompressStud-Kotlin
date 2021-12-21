package pl.dev.kefirx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register_start.*
import pl.dev.kefirx.*


class FragmentRegisterStart : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_start, container, false)
    }

    override fun onStart() {
        super.onStart()

        startRegisterButton.setOnClickListener{
            findNavController().navigate(
                FragmentRegisterStartDirections.actionFragmentRegisterStartToFragmentRegisterStep1())
        }


    }


}