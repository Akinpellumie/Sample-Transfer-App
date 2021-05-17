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
import com.interswitchng.interswitchpos.databinding.FragmentSendCashLandingBinding
import com.interswitchng.interswitchpos.domain.models.SendCashTransferModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.models.PaymentModel
import org.koin.android.viewmodel.ext.android.viewModel


class SendCashLandingFragment : Fragment() {
    private lateinit var binding : FragmentSendCashLandingBinding
    private lateinit var  sendCashModel : SendCashTransferModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_send_cash_landing, container, false)
        setUpUI()
        return binding.root
    }

    private fun setUpUI() {
        val cashAmount = binding.amountEntry.text
        val acctNum = binding.acctNumEntry.text
        val acctName = binding.acctNameEntry.text
        val bankName = binding.bankNameEntry.text
        val narration = binding.narrationEntry.text

        //set cash model value
        sendCashModel.acctName = acctName.toString()
        sendCashModel.cashAmount = cashAmount.toString()
        sendCashModel.acctNumber = acctNum.toString()
        sendCashModel.bankName = bankName.toString()
        sendCashModel.narration = narration.toString()

        //proceed to summary page
        binding.initiateBtn.setOnClickListener {
            val action = SendCashLandingFragmentDirections.actionSendCashToTransactionSummaryFragment(sendCashModel)
            findNavController().navigate(action)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SendCashLandingFragment()
    }
}