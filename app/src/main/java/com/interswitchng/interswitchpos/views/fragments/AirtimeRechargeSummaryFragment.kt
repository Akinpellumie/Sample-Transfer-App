package com.interswitchng.interswitchpos.views.fragments

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
import com.interswitchng.interswitchpos.databinding.FragmentAirtimeRechargeSummaryBinding
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.services.AirtimeRechargeRequests
import com.interswitchng.interswitchpos.views.services.CompleteBillPayTransaction
import com.interswitchng.interswitchpos.views.services.interfaces.ICompleteBillCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import org.koin.android.viewmodel.ext.android.viewModel

class AirtimeRechargeSummaryFragment : Fragment(), ICompleteBillCallBack {

    private val viewmodel : AppViewModel by viewModel()
    //private lateinit var binding:
    private lateinit var binding: FragmentAirtimeRechargeSummaryBinding;
    private val args by navArgs<AirtimeRechargeSummaryFragmentArgs>()
    private val transactionId by lazy { args.tranxnId }
    private val phoneNumber by lazy {args.phoneNumber}
    private val rechargeAmount by lazy {args.amount}

    private val completeBillPayTransaction = CompleteBillPayTransaction(this);

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_airtime_recharge_summary, container, false)

        binding.amountTv.text = rechargeAmount;
        binding.phoneTv.text = phoneNumber;

        binding.buyAirtime.setOnClickListener {
            if (transactionId.isNotEmpty() || binding.pinEt.text.isNotEmpty()) {

                //send data
                val pin = binding.pinEt.text.toString()
                completeBillPayTransaction.execute(transactionId, pin)
            }
            else {
                val text = "Oops!! Entry Field cannot be empty!!"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
            }
//            val action = HomeLandingFragmentDirections.actionHomeToAmountFragment2()
//            findNavController().navigate(action)
        }

        return binding.root

    }
//


    companion object {
        @JvmStatic
        fun newInstance() = AirtimeRechargeSummaryFragment()
    }

    override fun OnCompleteBillPayTranxn(completeTranxnData: CompleteTransactionModel?) {
        //TODO("Not yet implemented")

        //navigate to other view passing user data
        //val tranxnId = tr?.data?.profileInfo?.firstname.toString()
        val tranxnId = completeTranxnData?.data?.getTransactionId().toString();
        val amount = completeTranxnData?.data?.getAmount().toString();
        val message = completeTranxnData?.getMessage().toString();

        val action = AirtimeRechargeSummaryFragmentDirections.actionAirtimeSummaryToTransactionSuccessFragment(amount, tranxnId)
        //val action = AirtimePaymentFormFragmentDirections.actionLoginToHomeFragment(u)
        findNavController().navigate(action)

//        if (tranxnId.isNotEmpty() && message.contains("Transaction Completed")){
//            val action = AirtimeRechargeSummaryFragmentDirections.actionAirtimeSummaryToTransactionSuccessFragment(tranxnId, amount)
//            //val action = AirtimePaymentFormFragmentDirections.actionLoginToHomeFragment(u)
//            findNavController().navigate(action)
//        }
//        else{
//            // go transaction failed page
//        }


    }
}