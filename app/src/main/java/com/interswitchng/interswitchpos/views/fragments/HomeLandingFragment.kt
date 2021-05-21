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
import com.interswitchng.interswitchpos.utils.getAmountWithCurrency
import com.interswitchng.interswitchpos.utils.getAstraAmountWithCurrency
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.services.AgentTransactionFlowService
import com.interswitchng.interswitchpos.views.services.BankListService
import com.interswitchng.interswitchpos.views.services.Constants
import com.interswitchng.interswitchpos.views.services.callback.IFlowCallBack
import com.interswitchng.interswitchpos.views.services.model.home.FlowModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import kotlinx.android.synthetic.main.fragment_home_landing.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeLandingFragment : Fragment(), IFlowCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentHomeLandingBinding
//    private val args by navArgs<HomeLandingFragmentArgs>()
//    private val userFirstname by lazy { args.userFirstname }
    private val agentTransactionFlowService = AgentTransactionFlowService(this)

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

        //get inflow and outflow amount
        agentTransactionFlowService.execute()

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

    override fun getFlowData(flow: FlowModel?) {
        //binding the text to the view
        val inflowAmt = flow?.data?.inflow.toString().let { getAstraAmountWithCurrency(it) }
        binding.inflowBalance.text = inflowAmt

        val outflowAmt = flow?.data?.outflow.toString().let { getAstraAmountWithCurrency(it) }
        binding.outflowBalance.text = outflowAmt
    }
}