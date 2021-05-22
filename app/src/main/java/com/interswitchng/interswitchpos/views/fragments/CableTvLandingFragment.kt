package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentBuyAirtimeMainBinding
import com.interswitchng.interswitchpos.databinding.FragmentCableTvPaymentBinding
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CableTvLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentCableTvPaymentBinding
    //private lateinit var binding:

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cable_tv_payment, container, false)

        // Dstv recharge clickEvent
        binding.dstvCard.setOnClickListener {
            val cableType = "Dstv"
            val action = CableTvLandingFragmentDirections.actionCableTvLandingToCableTvPaymentForm(cableType)
            findNavController().navigate(action)


        }

        // GoTv recharge clickEvent
        binding.gotvCard.setOnClickListener {
            val cableType = "Gotv"
            val action = CableTvLandingFragmentDirections.actionCableTvLandingToCableTvPaymentForm(cableType)
            findNavController().navigate(action)


        }

        // Startimes recharge clickEvent
        binding.startimesCard.setOnClickListener {
            val cableType = "Startimes"
            val action = CableTvLandingFragmentDirections.actionCableTvLandingToCableTvPaymentForm(cableType)
            findNavController().navigate(action)
        }
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = CableTvLandingFragment()
    }
}