package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentTransactionsLandingBinding
import com.interswitchng.interswitchpos.utils.adapters.TransactionsRecyclerAdapter
import com.interswitchng.interswitchpos.views.services.request.TransactionRecordService
import com.interswitchng.interswitchpos.views.services.callback.IRecordCallback
import com.interswitchng.interswitchpos.views.services.callback.ISingleTransactionSelectionCallBack
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.Datum
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.TransactionRecord
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import kotlinx.android.synthetic.main.fragment_transactions_landing.*
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionsLandingFragment : Fragment(), IRecordCallback, ISingleTransactionSelectionCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionsLandingBinding
    //private lateinit var binding:
    private var transactionRecord : TransactionRecord ?= null


//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_transactions_landing, container, false)

        //call transaction history java class
        if(transactionRecord==null){
            val transactionRecordService = TransactionRecordService(this)
            transactionRecordService.execute()
        }
        else{
            val tra = TransactionsRecyclerAdapter(transactionRecord?.data, this)
            //set adapter on recycler
            binding.recentTransRecycler.adapter = tra
        }

        // open filter view
        binding.filterIcon.setOnClickListener {
            cardFilterView.visibility = View.VISIBLE
            binding.searchEntry.isEnabled = false
        }

        // close filter view
        binding.closePopUpBtn.setOnClickListener {
            binding.searchEntry.isEnabled = true
            cardFilterView.visibility = View.INVISIBLE
        }

        // navigate to cable Tv fragment
//        binding.cableCard.setOnClickListener {
//            val action = PayBillMainLandingFragmentDirections.actionBillToCableFragment()
//            findNavController().navigate(action)
//        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionsLandingFragment()
    }

    override fun getTransactions(record: TransactionRecord?) {
        transactionRecord = record
        //do nothing for now
        //instantiate adapter wit record in param
        val tra = TransactionsRecyclerAdapter(record?.data, this)
        //set adapter on recycler
        binding.recentTransRecycler.adapter = tra
    }

    override fun onSelect(item: Datum?) {
        val items = item
        var iTransType = ""
        if(item?.transactionType.equals("BILLPAYMENTWITHCASH", ignoreCase = true)){
            iTransType = "BILL PAYMENT"
        }
        else  if(item?.transactionType.equals("CASHTRANSFER", ignoreCase = true)){
            iTransType = "CASH TRANSFER"
        }
        else  if(item?.transactionType.equals("CARDWITHDRAWAL", ignoreCase = true)){
            iTransType = "CARD WITHDRAWAL"
        }
        else{
            iTransType = item?.transactionType.toString()
        }
        val singleAmount = item?.amount
        val transType = iTransType
        val transId = item?.id
        val status = item?.status
        val channel = item?.beneficiaryTerminal?.categoryName

        val action = TransactionsLandingFragmentDirections.actionAirtimeSummaryToSingleTransFragment(
                singleAmount.toString(),transId.toString(),transType,status.toString(), channel.toString()
        )
            findNavController().navigate(action)
    }


}