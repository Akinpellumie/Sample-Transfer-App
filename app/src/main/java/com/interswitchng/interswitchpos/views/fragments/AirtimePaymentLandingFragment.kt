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
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AirtimePaymentLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentBuyAirtimeMainBinding
    //private lateinit var binding:

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_buy_airtime_main, container, false)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_buy_airtime_main, container, false)

        // glo recharge clickEvent
        binding.gloCard.setOnClickListener {
            val rechargeType =  "Glo"
            val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }

        // mtn recharge clickEvent
        binding.mtnCard.setOnClickListener {
            val rechargeType = "Mtn"
            val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)
        }

        // 9mobile recharge clickEvent
        binding.etiCard.setOnClickListener {
            val rechargeType = "9Mobile"
            val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)
        }

        // Airtel recharge clickEvent
        binding.airtelCard.setOnClickListener {
            val rechargeType = "Airtel"
            val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AirtimePaymentLandingFragment()
    }
}