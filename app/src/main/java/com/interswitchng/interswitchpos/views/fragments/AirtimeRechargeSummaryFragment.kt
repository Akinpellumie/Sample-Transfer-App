package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentAirtimeRechargeSummaryBinding
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import org.koin.android.viewmodel.ext.android.viewModel

class AirtimeRechargeSummaryFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    //private lateinit var binding:
    private lateinit var binding: FragmentAirtimeRechargeSummaryBinding;
    private val args by navArgs<AirtimeRechargeSummaryFragmentArgs>()
    private val transactionId by lazy { args.tranxnId }
    private val phoneNumber by lazy {args.phoneNumber}
    private val rechargeAmount by lazy {args.amount}

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_airtime_recharge_summary, container, false)

        binding.amountTv.text = rechargeAmount;
        binding.phoneTv.text = phoneNumber;
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
        fun newInstance() = AirtimeRechargeSummaryFragment()
    }
}