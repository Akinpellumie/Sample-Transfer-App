package com.interswitchng.interswitchpos.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentAstraTransactionReceiptBinding
import com.interswitchng.interswitchpos.databinding.FragmentTransactionReceiptBinding
import com.interswitchng.interswitchpos.utils.getAmountWithCurrency
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.utilities.*
import kotlinx.coroutines.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AstraPrintReceiptFragment : Fragment() {
    private lateinit var binding: FragmentAstraTransactionReceiptBinding
    private val job = Job()


    private val astraReceiptFragmentArgs by navArgs<AstraPrintReceiptFragmentArgs>()
    private val cashAmount by lazy { astraReceiptFragmentArgs.amount }
    private val transType by lazy { astraReceiptFragmentArgs.transactionType }
    private val transId by lazy { astraReceiptFragmentArgs.transId }
    private val status by lazy { astraReceiptFragmentArgs.status}
    private val channel by lazy { astraReceiptFragmentArgs.channel}
    private val date by lazy { astraReceiptFragmentArgs.date}
    private val time by lazy { astraReceiptFragmentArgs.time}
    //val data by lazy { astraReceiptFragmentArgs.transactionResponseModel.transactionResult }

    private val terminalInfo by lazy {
        IswTxnHandler().getTerminalInfo()
    }

    private val viewmodel : AppViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_astra_transaction_receipt, container, false)
        handleprint()
        listenToviewModel()
        setupUI()
        return binding.root
    }

    private fun handleprint() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                this@AstraPrintReceiptFragment.requireActivity().runOnUiThread {
                    doPrinting()
                }
            }

        }, 1000)
    }

    private fun doPrinting() {
        if (astraReceiptFragmentArgs.withAgent) {
            val scope = CoroutineScope(Dispatchers.Main + job)
            scope.launch {
                getScreenBitMap(this@AstraPrintReceiptFragment.requireActivity(),
                        binding.rootViewForPrintPage)?.let { viewmodel.printSlipNew(it) }
                delay(3000L)
                binding.customerTitle.text = "*** MERCHANT COPY ***"
                delay(1000L)
                getScreenBitMap(this@AstraPrintReceiptFragment.requireActivity(),
                        binding.rootViewForPrintPage)?.let { viewmodel.printSlipNew(it) }
            }
        } else {
            getScreenBitMap(this@AstraPrintReceiptFragment.requireActivity(),
                    binding.rootViewForPrintPage)?.let { viewmodel.printSlipNew(it) }
        }

    }
    private fun listenToviewModel() {
        val owner = { lifecycle }
        with(viewmodel) {
            printerMessage.observe(owner) {
                showSnack(binding.agentTitle, it.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {

        //set up ui to print
        binding.transactionType.text =  transType
        binding.terminalIdTitle.text = "TERMINAL ID: ${terminalInfo?.terminalId}"
        binding.telTitle.text = "TEL: ${terminalInfo?.agentId}"
        binding.transIdTitle.text = "TRANS ID: $transId"
        binding.channelTitle.text = "CHANNEL: $channel"
        binding.statusTitle.text = "STATUS: $status"
        binding.transactionType.text = transType
        binding.dateTitle.text = "DATE: $date"
        binding.timeTitle.text = "TIME: $time"
        binding.amountTitle.text = getHtmlString("AMOUNT: ${getAmountWithCurrency(cashAmount, terminalInfo!!)}")
//        binding.agentValue.text = terminalInfo?.merchantNameAndLocation
//        binding.terminalIdTitle.text = "TERMINAL ID: ${terminalInfo?.terminalId}"
//        binding.telTitle.text = "TEL: ${terminalInfo?.agentId}"
//        binding.withdrawTitle.text = data?.type?.name
//        binding.channelTitle.text = "CHANNEL: ${data?.paymentType?.name}"
//        binding.dateTitle.text = "DATE: ${getDate(data?.dateTime.toString())}"
//        binding.timeTitle.text = "TIME: ${getTime(data?.dateTime.toString())}"
//        binding.amountTitle.text = getHtmlString("AMOUNT: ${data?.amount.let { getAmountWithCurrency(it.toString(), terminalInfo!!) }}")

//        val cardTypeName = when (data?.cardType) {
//            CardType.MASTER -> "Master Card"
//            CardType.VISA -> "Visa Card"
//            CardType.VERVE -> "Verve Card"
//            CardType.AMERICANEXPRESS -> "American Express Card"
//            CardType.CHINAUNIONPAY -> "China Union Pay Card "
//            CardType.None -> "Unknown Card"
//            else -> "Unknown Card"
//        }
//        binding.cardTitle.text = "CARD TYPE: ${cardTypeName}"
//        binding.panTitle.text = "CARD PAN: ${mask(data?.cardPan.toString())}"
//        binding.expiryDateTitle.text = "EXPIRY DATE: ${formatExpiryDate(data?.cardExpiry.toString(), "/")}"
//        binding.stanTitle.text  = "STAN: ${data?.getTransactionInfo()?.stan?.padStart(6, '0')}"
//        binding.aidTitle.text = "AID: ${data?.AID}"

//        if (data?.getTransactionStatus()?.responseCode.toString() == "00") {
//            binding.retainReceiptTitle.reveal()
//            binding.transactionApprovedTitle.text = "${data?.getTransactionStatus()?.responseMessage}"
//        } else {
//            binding.transactionApprovedTitle.text = "${data?.getTransactionStatus()?.responseMessage}"
//        }
        //binding.refTitle.text = "REF: ${data?.ref}"

//        if (receiptFragmentArgs.rePrint) {
//            binding.customerTitle.text = "*** MERCHANT COPY ***"
//            binding.reprintTitle.reveal()
//            binding.reprintTitle.reveal()
//            binding.lineAfterReprint1.reveal()
//            binding.lineBeforeReprint2.reveal()
//            binding.iswReprintBtn.reveal()
//        }

        binding.iswGoToLanding.setOnClickListener {
            findNavController().popBackStack(R.id.home, false)
        }

        binding.iswReprintBtn.setOnClickListener {
            val scope = CoroutineScope(Dispatchers.Main + job)
            scope.launch {
                binding.iswReprintBtn.hide()
                delay(100L)
                doPrinting()
                delay(100L)
                binding.iswReprintBtn.reveal()
            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() = AstraPrintReceiptFragment()
    }
}