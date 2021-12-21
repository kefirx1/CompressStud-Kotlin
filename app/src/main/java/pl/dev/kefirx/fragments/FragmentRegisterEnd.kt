package pl.dev.kefirx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register_end.*
import pl.dev.kefirx.R

class FragmentRegisterEnd : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register_end, container, false)
    }

    override fun onStart() {
        super.onStart()

        val personalInformation: ArrayList<String> = arguments?.getStringArrayList("info") as ArrayList<String>
        println(personalInformation)

        endRegisterButton.setOnClickListener{
            this.activity?.finish()
        }
    }



}