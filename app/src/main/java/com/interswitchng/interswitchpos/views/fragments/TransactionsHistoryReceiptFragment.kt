package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentTransactionReceiptBinding
import com.interswitchng.interswitchpos.databinding.FragmentTransactionsLandingBinding
import com.interswitchng.interswitchpos.utils.adapters.TransactionsRecyclerAdapter
import com.interswitchng.interswitchpos.views.services.LoginService
import com.interswitchng.interswitchpos.views.services.TransactionRecordService
import com.interswitchng.interswitchpos.views.services.callback.IRecordCallback
import com.interswitchng.interswitchpos.views.services.callback.ISingleTransactionSelectionCallBack
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.Datum
import com.interswitchng.interswitchpos.views.services.model.transactionrecord.TransactionRecord
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import kotlinx.android.synthetic.main.fragment_transactions_landing.*
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionsHistoryReceiptFragment : Fragment(), IRecordCallback, ISingleTransactionSelectionCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionReceiptBinding
    //private lateinit var binding:
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_transactions_landing, container, false)


        // navigate to cable Tv fragment
//        binding.cableCard.setOnClickListener {
//            val action = PayBillMainLandingFragmentDirections.actionBillToCableFragment()
//            findNavController().navigate(action)
//        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionsHistoryReceiptFragment()
    }

    override fun getTransactions(record: TransactionRecord?) {
        //do nothing for now
        //instantiate adapter wit record in param
        val tra = TransactionsRecyclerAdapter(record?.data, this)
        //set adapter on recycler
        //binding.recentTransRecycler.adapter = tra
    }

    override fun onSelect(item: Datum?) {
        val items = item
    }


}