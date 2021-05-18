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
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.databinding.FragmentLoginLandingBinding
import com.interswitchng.interswitchpos.databinding.FragmentSendCashTransferSummaryBinding
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import org.koin.android.viewmodel.ext.android.viewModel

class SendCashTransferSummaryFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentSendCashTransferSummaryBinding
    private val args by navArgs<SendCashTransferSummaryFragmentArgs>()
    private val cashAmount by lazy { args.cashAmount }
    private val acctName by lazy { args.acctName }
    private val acctNum by lazy { args.acctNum }
    private val bankName by lazy { args.bankName }
    private val narration by lazy { args.narration }



//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_cable_payment_summary, container, false)
//
//    }
//
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_send_cash_transfer_summary, container, false)
    setUpUI()
    return binding.root
}

    private fun setUpUI() {
        binding.transAcctName.text = acctName
        binding.transAcctNum.text = acctNum
        binding.transType.text = "Cash Transfer"
        binding.transAmount.text = cashAmount
        binding.transNarration.text = narration
    }


    companion object {
        @JvmStatic
        fun newInstance() = SendCashTransferSummaryFragment()
    }
}