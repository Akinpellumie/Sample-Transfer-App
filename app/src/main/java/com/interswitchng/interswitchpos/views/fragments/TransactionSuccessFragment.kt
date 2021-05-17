package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.databinding.FragmentPayBillsMainBinding
import com.interswitchng.interswitchpos.databinding.FragmentTransactionSuccessBinding
import com.interswitchng.interswitchpos.databinding.FragmentTransactionsLandingBinding
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import kotlinx.android.synthetic.main.fragment_transactions_landing.*
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionSuccessFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentTransactionSuccessBinding
    //private lateinit var binding:

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

        // navigate to cable Tv fragment
//        binding.cableCard.setOnClickListener {
//            val action = PayBillMainLandingFragmentDirections.actionBillToCableFragment()
//            findNavController().navigate(action)
//        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionSuccessFragment()
    }
}