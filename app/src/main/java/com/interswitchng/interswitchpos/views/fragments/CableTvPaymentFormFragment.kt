package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentCableTvPaymentFormBinding
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CableTvPaymentFormFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentCableTvPaymentFormBinding;
    //private lateinit var binding:

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cable_tv_payment_form, container, false)

        return binding.root

    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_landing, container, false)
//        if (terminalInfo != null) {
//            viewmodel.getToken(terminalInfo!!)
//        }
////        binding.iswTransferCard.setOnClickListener {
//////            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
////            val action = HomeLandingFragmentDirections.actionHomeToAmountFragment2()
////            findNavController().navigate(action)
////        }
//
//        val level = IswTxnHandler().getBatterLevel(requireContext())
//       showSnack(binding.iswCashOutText, "Battery Level is: $level")
//        return binding.root
//    }

    companion object {
        @JvmStatic
        fun newInstance() = CableTvPaymentFormFragment()
    }
}