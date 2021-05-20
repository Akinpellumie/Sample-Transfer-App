package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentPayElectricityMainBinding
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ElectricityPaymentLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentPayElectricityMainBinding
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_pay_electricity_main, container, false)

        // glo recharge clickEvent
        binding.abujaElectric.setOnClickListener {
            val billerName =  "Abuja"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.ekoElectric.setOnClickListener {
            val billerName =  "Eko"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.enuguElectric.setOnClickListener {
            val billerName =  "Enugu"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.ibadanElectric.setOnClickListener {
            val billerName =  "Ibadan"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.ikejaElectric.setOnClickListener {
            val billerName =  "Ikeja"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.josElectric.setOnClickListener {
            val billerName =  "Jos"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.kadunaElectric.setOnClickListener {
            val billerName =  "Kaduna"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }
        binding.phElectric.setOnClickListener {
            val billerName =  "PortHarcourt"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }

        binding.kanoElectric.setOnClickListener {
            val billerName =  "Kano"
            val action = ElectricityPaymentLandingFragmentDirections.actionElectricityLandingToElectricityPaymentForm(billerName)
            //val action = AirtimePaymentLandingFragmentDirections.actionAirtimeMainToAirtimeFormFragment(rechargeType)
            findNavController().navigate(action)

        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ElectricityPaymentLandingFragment()
    }
}