package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
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

class TransactionsLandingFragment : Fragment(), IRecordCallback, ISingleTransactionSelectionCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionsLandingBinding
    //private lateinit var binding:
    private val transactionRecordService = TransactionRecordService(this)

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
        transactionRecordService.execute()

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
        //do nothing for now
        //instantiate adapter wit record in param
        val tra = TransactionsRecyclerAdapter(record?.data, this)
        //set adapter on recycler
        binding.recentTransRecycler.adapter = tra
    }

    override fun onSelect(item: Datum?) {
        val items = item
        val singleAmount = item?.amount
        val transType = item?.transactionType
        val transId = item?.id
        val status = item?.status
        val channel = item?.transactionType

        val action = TransactionsLandingFragmentDirections.actionAirtimeSummaryToSingleTransFragment(
                singleAmount.toString(),transId.toString(),transType.toString(),status.toString(), channel.toString()
        )
            findNavController().navigate(action)
    }


}