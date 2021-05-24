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
import com.interswitchng.interswitchpos.databinding.FragmentElectricityPaymentSummaryBinding
import com.interswitchng.interswitchpos.utils.getDateFormat
import com.interswitchng.interswitchpos.utils.getTimeFormat
import com.interswitchng.interswitchpos.views.services.request.CompleteElectricityBillPayment
import com.interswitchng.interswitchpos.views.services.interfaces.IElectricityPayCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ElectricityPaymentSummaryFragment : Fragment(), IElectricityPayCallBack {

    private val viewmodel : AppViewModel by viewModel()
    //private lateinit var binding:
    private lateinit var binding: FragmentElectricityPaymentSummaryBinding;
    private val args by navArgs<ElectricityPaymentSummaryFragmentArgs>()
    private val transactionId by lazy { args.tranxnId }
    private val phoneNumber by lazy {args.phoneNumber}
    private val electricityAmount by lazy {args.amount}
    private val customerId by lazy {args.customerId}
    private val customerName by lazy {args.customerName}
    private val billerName by lazy {args.electricityCompany}

    private  val completeElectricityBillPayment = CompleteElectricityBillPayment(this);
    //private val completeBillPayTransaction = CompleteBillPayTransaction(this);

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_electricity_payment_summary, container, false)

        val custName = customerName.substring(9)
       binding.typeTv.text = billerName + " Electric";
        binding.phoneTv.text = phoneNumber;
        binding.amountTv.text = electricityAmount;
        binding.customerIdTv.text = customerId;
        binding.customerName.text = custName;
//        binding.phoneTv.text = phoneNumber;

        binding.buyElectricity.setOnClickListener {
            if (transactionId.isNotEmpty() || binding.pinEt.text.isNotEmpty()) {

                //send data
                val pin = binding.pinEt.text.toString()
                completeElectricityBillPayment.execute(transactionId, pin)
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
        fun newInstance() = ElectricityPaymentSummaryFragment()
    }

    override fun OnElectricityPayInitialize(tranxnInitiateData: TranxnInitiateModel?) {
        TODO("Not yet implemented")
    }

    override fun OnElectricityPayComplete(completeTranxnData: CompleteTransactionModel?) {
//

        val transId = completeTranxnData?.data?.getTransactionId().toString()
        val amount = completeTranxnData?.data?.getAmount().toString()
        val message = completeTranxnData?.getMessage().toString()
        val status = completeTranxnData?.data?.getStatus().toString()
        val transactionType = "BILL PAYMENT"
        val channel = "$billerName Electric"
        val transDate = completeTranxnData?.getData()?.completedAt
        val date = transDate?.let { getDateFormat(it) }
        val time = transDate?.let { getTimeFormat(it) }

        val action = ElectricityPaymentSummaryFragmentDirections.actionElectricitySummaryToTransactionSuccessFragment(
                amount, transId, transactionType, channel, status, date.toString(), time.toString()
        )
        findNavController().navigate(action)
//
////        if (tranxnId.isNotEmpty() && message.contains("Transaction Completed")){
////            val action = AirtimeRechargeSummaryFragmentDirections.actionAirtimeSummaryToTransactionSuccessFragment(tranxnId, amount)
////            //val action = AirtimePaymentFormFragmentDirections.actionLoginToHomeFragment(u)
////            findNavController().navigate(action)
////        }
////        else{
////            // go transaction failed page
////        }
    }

}