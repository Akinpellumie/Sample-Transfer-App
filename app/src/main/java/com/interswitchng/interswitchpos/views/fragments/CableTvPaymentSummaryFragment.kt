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
import com.interswitchng.interswitchpos.databinding.FragmentCablePaymentSummaryBinding
import com.interswitchng.interswitchpos.views.services.interfaces.ICableTvPayCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.services.request.CompleteCableTvPayTransaction
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class CableTvPaymentSummaryFragment : Fragment(), ICableTvPayCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentCablePaymentSummaryBinding
    private val args by navArgs<CableTvPaymentSummaryFragmentArgs>()
    private val transactionId by lazy { args.tranxnId }
    private val amount by lazy {args.amount}
    private val customerName by lazy {args.customerName}
    private val smartCardNumber by lazy {args.smartCardNumber}
    private val cableTvPlan by lazy {args.plan}
    private val cableTvType by lazy {args.cableTvType}
    private val phonenumber by lazy {args.regPhoneNumber}
    //private lateinit var binding:
    private var completeCableTvPayTransaction = CompleteCableTvPayTransaction(this);



//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cable_payment_summary, container, false)


        binding.amountTv.text = amount;
        binding.cardNumberTv.text = smartCardNumber
        binding.phoneTv.text = phonenumber
        binding.planTv.text = cableTvPlan
        binding.typeTv.text = cableTvType


        binding.buySubscription.setOnClickListener {
            if (transactionId.isNotEmpty() || binding.pinEt.text.isNotEmpty()) {

                //send data
                val pin = binding.pinEt.text.toString()
                binding.llProgressBar.visibility = View.VISIBLE
                //completeBillPayTransaction.execute(transactionId, pin)
            }
            else {
                val text = "Oops!! Entry Field cannot be empty!!"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
            }

        }
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = CableTvPaymentSummaryFragment()
    }

    override fun OnCableTvPayInitialize(tranxnInitiateData: TranxnInitiateModel?) {
        TODO("Not yet implemented")
    }

    override fun OnCableTvPayComplete(completeTranxnData: CompleteTransactionModel?) {

        val tranxnId = completeTranxnData?.data?.getTransactionId().toString();
        val amount = completeTranxnData?.data?.getAmount().toString();
        val message = completeTranxnData?.getMessage().toString();
        val status = "SUCCESSFUL";
        val transType = "DSTV " + cableTvPlan;
        val channel = "DSTV SUBSCRIPTION";

        val action = AirtimeRechargeSummaryFragmentDirections.actionAirtimeSummaryToTransactionSuccessFragment(
                amount, tranxnId, transType, channel, status
        )
        //val action = AirtimePaymentFormFragmentDirections.actionLoginToHomeFragment(u)
        findNavController().navigate(action)
    }
}