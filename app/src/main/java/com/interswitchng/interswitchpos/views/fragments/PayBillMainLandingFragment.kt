package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentPayBillsMainBinding
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PayBillMainLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentPayBillsMainBinding
    //private lateinit var binding:

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_pay_bills_main, container, false)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_pay_bills_main, container, false)

        // navigate to airtime payment fragment
        binding.airtimeCard.setOnClickListener {
            val action = PayBillMainLandingFragmentDirections.actionBillToAirtimeFragment()
            findNavController().navigate(action)
        }

        // navigate to cable Tv fragment
        binding.cableCard.setOnClickListener {
            val action = PayBillMainLandingFragmentDirections.actionBillToCableFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PayBillMainLandingFragment()
    }
}