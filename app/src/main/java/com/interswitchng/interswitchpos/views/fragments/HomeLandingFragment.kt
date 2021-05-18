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
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.services.Constants
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import org.koin.android.viewmodel.ext.android.viewModel

class HomeLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentHomeLandingBinding
//    private val args by navArgs<HomeLandingFragmentArgs>()
//    private val userFirstname by lazy { args.userFirstname }

    private val terminalInfo by lazy {
        IswTxnHandler().getTerminalInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home_landing, container, false)
        if (terminalInfo != null) {
            viewmodel.getToken(terminalInfo!!)
        }

        //bind loginData to homePage UI
        binding.userFirstname.text =   Constants.loggedInAgentFirstname
        //navigate to Amount fragment
        binding.iswTransferCard.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = HomeLandingFragmentDirections.actionHomeToAmountFragment2()
            findNavController().navigate(action)
        }

        //navigate to profile fragment
        binding.leftContent.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = HomeLandingFragmentDirections.actionHomeToProfileFragment()
            findNavController().navigate(action)
        }

        //navigate to sendCash fragment
        binding.astraSendCash.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = HomeLandingFragmentDirections.actionHomeToSendcashFragment()
            findNavController().navigate(action)
        }

        //navigate to billPay fragment
        binding.billPayLayout.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = HomeLandingFragmentDirections.actionHomeToPaybillsFragment()
            findNavController().navigate(action)
        }

        //navigate to transaction page fragment
        binding.transactionLayout.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = HomeLandingFragmentDirections.actionHomeToTransactionPageFragment()
            findNavController().navigate(action)
        }

        val level = IswTxnHandler().getBatterLevel(requireContext())
       showSnack(binding.iswCashOutText, "Battery Level is: $level")
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeLandingFragment()
    }
}