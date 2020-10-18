package br.emerson.pi4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SobreFragment : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val fragmento = inflater.inflate(R.layout.fragment_sobre, container, false)

        return fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance() = SobreFragment()
    }
}