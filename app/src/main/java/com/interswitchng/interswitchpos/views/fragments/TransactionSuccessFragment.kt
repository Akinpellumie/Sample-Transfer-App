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
import com.interswitchng.interswitchpos.databinding.FragmentTransactionSuccessBinding
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionSuccessFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionSuccessBinding
    //private lateinit var binding:
    private val args by navArgs<TransactionSuccessFragmentArgs>()
    private val cashAmount by lazy { args.amount }
    private val channel by lazy { args.channel }
    private val transType by lazy { args.transactionType }
    private val transId by lazy { args.transId }
    private val status by lazy { args.status }

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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_transaction_success, container, false)

        // navigate to airtime payment fragment
//        binding.filterIcon.setOnClickListener {
//            cardFilterView.visibility = View.VISIBLE
//        }

         //navigate to Home fragment
        binding.buttonBackToHome.setOnClickListener {
//            val action = TransactionSuccessFragmentDirections.actionTransactionSuccessFragmentToHomelandingfragment()
//            findNavController().navigate(action)
            findNavController().popBackStack(R.id.home, false)
        }

        //navigate to print page
        binding.btnPrintReceipt.setOnClickListener {
            val action = TransactionSuccessFragmentDirections.actionTransSummaryToAstraPrintReceiptFragment(
                    cashAmount, transId, transType, channel, status
            )
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionSuccessFragment()
    }
}