package com.interswitchng.interswitchpos.views.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentSendCashTransferSummaryBinding
import com.interswitchng.interswitchpos.utils.customdailog
import com.interswitchng.interswitchpos.utils.getAstraAmountWithCurrency
import com.interswitchng.interswitchpos.utils.getDateFormat
import com.interswitchng.interswitchpos.utils.getTimeFormat
import com.interswitchng.interswitchpos.views.services.request.CompleteSendCashTransfer
import com.interswitchng.interswitchpos.views.services.interfaces.ISendCashTransferCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.shared.utilities.getDate
import com.interswitchng.smartpos.shared.utilities.getTime
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class SendCashTransferSummaryFragment : Fragment(), ISendCashTransferCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentSendCashTransferSummaryBinding
    private val args by navArgs<SendCashTransferSummaryFragmentArgs>()
    private lateinit var loading: Dialog
    private val cashAmount by lazy { args.cashAmount }
    private val acctName by lazy { args.acctName }
    private val acctNum by lazy { args.acctNum }
    private val bankName by lazy { args.bankName }
    private val bankCode by lazy { args.bankCode }
    private val bankId by lazy { args.bankId }
    private val narration by lazy { args.narration }
    private val transId by lazy { args.transId }



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
        val myAcctname = acctName.substring(9)
        binding.transAcctName.text = myAcctname
        binding.transAcctNum.text = acctNum
        binding.transType.text = "Cash Transfer"
        binding.transAmount.text = cashAmount
        binding.transNarration.text = narration

        //proceed to summary page and call initiate send cash
        binding.completeTransferBtn.setOnClickListener {

            if(binding.pinEntry.text.length <= 6 && binding.pinEntry.text.isNotEmpty()){
                //call complete transaction
                binding.loader.visibility = View.VISIBLE
                //loading = customdailog(requireContext(), "Please wait while we process transaction")
                val transId = transId
                val userPin = binding.pinEntry.text

                //call complete transaction
                val completeSendCashTransfer = CompleteSendCashTransfer(this)
                completeSendCashTransfer.execute(transId, userPin.toString())
            }
            else{
                val text = "Oops!!, Enter Pin to continue"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
            }

        }

    }



    companion object {
        @JvmStatic
        fun newInstance() = SendCashTransferSummaryFragment()
    }

    override fun OnSendCashInitialize(tranxnInitiateData: TranxnInitiateModel?) {
       //do nothing
    }

    override fun OnSendCashComplete(completeTranxnData: CompleteTransactionModel?) {

        if(completeTranxnData?.status==200){
            //loading.dismiss()
            val amount = binding.transAmount.text
            val tranxnId = transId
            val transType = "TRANSFER"
            val channel = "CASH TRANSFER"
            val status = completeTranxnData?.status
            val transDate = completeTranxnData?.getData()?.completedAt
            val date = transDate?.let { getDateFormat(it) }
            val time = transDate?.let { getTimeFormat(it) }
            val action = SendCashTransferSummaryFragmentDirections.actionSendCashSummaryToSuccessFragment(
                    amount.toString(),tranxnId, transType, channel, status.toString(), date.toString(), time.toString()
            )
            findNavController().navigate(action)

            //hide loader
            binding.loader.visibility = View.GONE
        }
        else if(completeTranxnData?.status!=200){
            //hide loader
            binding.loader.visibility = View.GONE
            val text = completeTranxnData?.message.toString()
            val duration = Toast.LENGTH_LONG

            Toast.makeText(context, text, duration).show()
            return
        }

    }
}