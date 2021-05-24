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
import com.interswitchng.interswitchpos.databinding.FragmentTransactionHistoryReceiptBinding
import com.interswitchng.interswitchpos.utils.getAstraAmountWithCurrency
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionsHistoryReceiptFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionHistoryReceiptBinding
    //private lateinit var binding:
    private val args by navArgs<TransactionsHistoryReceiptFragmentArgs>()
    private val transactionId by lazy { args.transId }
    private val singleTransAmount by lazy { args.singleTransAmount }
    private val transType by lazy { args.transType }
    private val status by lazy { args.status }
    private val channel by lazy { args.channel }
    private val date by lazy { args.date }
    private val time by lazy { args.time }
    //private val transactionRecordService = TransactionRecordService(this)

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_transaction_history_receipt, container, false)

        binding.transTypeEntry.text = transType
        binding.statusEntry.text = status
        binding.amountEntryR.text = getAstraAmountWithCurrency(singleTransAmount)
        binding.transIdEntry.text = transactionId

        // navigate to print receipt fragment
        binding.printReceiptBtn.setOnClickListener {
            val action = TransactionsHistoryReceiptFragmentDirections.actionTransSummaryToAstraPrintReceiptFragment(
                    singleTransAmount,transactionId,transType,channel,status, date, time
            )
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionsHistoryReceiptFragment()
    }



}