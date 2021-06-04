package com.interswitchng.interswitchpos.views.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentSendCashLandingBinding
import com.interswitchng.interswitchpos.utils.SendCashBankFilterDialog
import com.interswitchng.interswitchpos.utils.customdailog
import com.interswitchng.interswitchpos.views.services.request.BankListService
import com.interswitchng.interswitchpos.views.services.Constants
import com.interswitchng.interswitchpos.views.services.callback.IBankDetailCallBack
import com.interswitchng.interswitchpos.views.services.request.SendCashTransferInitService
import com.interswitchng.interswitchpos.views.services.callback.IBankListServiceCallback
import com.interswitchng.interswitchpos.views.services.interfaces.ISendCashTransferCallBack
import com.interswitchng.interswitchpos.views.services.model.bank.AccountDetailModel
import com.interswitchng.interswitchpos.views.services.model.bank.AstraBankModel
import com.interswitchng.interswitchpos.views.services.model.bank.BankData
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.services.request.VerifyAccountDetailService
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.models.BeneficiaryModel
import com.interswitchng.smartpos.models.NameEnquiryResult
import com.interswitchng.smartpos.shared.utilities.getTextValue
import kotlinx.android.synthetic.main.fragment_account_setup.*
import kotlinx.android.synthetic.main.fragment_send_cash_landing.*
import kotlinx.android.synthetic.main.fragment_transaction_success.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class SendCashLandingFragment : Fragment(), IBankListServiceCallback, ISendCashTransferCallBack, IBankDetailCallBack {
    private lateinit var binding : FragmentSendCashLandingBinding
    private var bankList = arrayListOf<BankData>()
    lateinit var _selectedBank: BankData
    lateinit var submitButton: Button
    lateinit var accountNumberEditor: EditText
    lateinit var _beneficiaryPayload: BeneficiaryModel
    var isValid = false
    lateinit var dialog: Dialog
    var accountNumber = ""
    var userAcctName = ""
    var userBankName = ""
    var userBankCode = ""
    var userBankId = ""
    var cashAmount = ""
    var acctNum = ""
    var narration = ""
    var useNameEnquiry = true
    private val appViewModel: AppViewModel by viewModel()
    private val sendCashTransferInitService = SendCashTransferInitService(this)
    private val bankListService = BankListService(this)

    //private lateinit var  sendCashModel : SendCashTransferModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_send_cash_landing, container, false)
        observeViewModel()
        setUpUI()
        //InitiateCashTransfer()
        return binding.root
    }

    private fun setUpUI() {


        //set cash model value
//        sendCashModel.acctName = acctName.toString()
//        sendCashModel.cashAmount = cashAmount.toString()
//        sendCashModel.acctNumber = acctNum.toString()
//        sendCashModel.bankName = bankName.toString()
//        sendCashModel.narration = narration.toString()

        //get list of banks
        bankListService.execute()

        //this is for acct number and bank checks
        submitButton = binding.initiateBtn
        accountNumberEditor = binding.acctNumEntry
        binding.bankNameEntry.setOnClickListener {
            parentFragmentManager?.let { it1 -> SendCashBankFilterDialog(this).show(it1, "show-bank-filter") }
        }
        binding.acctNumEntry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        this@SendCashLandingFragment.requireActivity().runOnUiThread {
                            //validateBeneficiary()
                            fetchUserAccountName()
                        }
                    }

                }, 300)
                accountNumber = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        //popstack navigation
        binding.astraToolbar.setOnClickListener{
            findNavController().popBackStack(R.id.home, false)
        }

        //proceed to summary page and call initiate send cash
        binding.initiateBtn.setOnClickListener {
            cashAmount = binding.amountEntry.text.toString()
            acctNum = binding.acctNumEntry.text.toString()
//            val acctName = binding.acctNameEntry.text
//            val bankName = binding.bankNameEntry.text
            narration = binding.narrationEntry.text.toString()
            //call initiate transaction
            binding.loader.visibility = View.VISIBLE
            sendCashTransferInitService.execute(cashAmount,acctNum,userAcctName,userBankName,userBankCode,userBankId,narration)

        }
    }

    private fun fetchUserAccountName(){
        if (accountNumber.length == 10 && this::_selectedBank.isInitialized){
            val verifyAccountDetailService = VerifyAccountDetailService(this)
            verifyAccountDetailService.execute(_selectedBank.code!!, accountNumber!!)
            if (!this::dialog.isInitialized) {
                dialog = customdailog(this.requireContext())
            }else {
                dialog.show()
            }
            return
        }
        else {
            isValid = false
            toggleAccountNameVisibility()
            validateInput()
        }
        if (accountNumber.length == 10 && _selectedBank.code.isNotEmpty()) {
            val verifyAccountDetailService = VerifyAccountDetailService(this)
            verifyAccountDetailService.execute(_selectedBank.code!!, accountNumber!!)
        }

    }

    private fun toggleAccountNameVisibility() {
        if (isValid) {
            binding.acctNameEntry.visibility = View.VISIBLE
            binding.acctNameEntry.visibility = View.VISIBLE

            binding.acctNameEntry.setText(userAcctName)
            dialog.hide()
//            if(this::_beneficiaryPayload.isInitialized) binding.acctNameEntry.setText(_beneficiaryPayload.accountName)
//            userAcctName = _beneficiaryPayload.accountName.toString()
        } else {
            binding.acctNameEntry.visibility = View.GONE
            //outlinedNameTextField.visibility = View.GONE
            binding.acctNameEntry.visibility = View.GONE
        }
    }
    private fun validateInput() {
//        submitButton.alpha = if (!isValid) 0.5F else 1F
//        submitButton.isEnabled = isValid
//        submitButton.isClickable = isValid
    }

    private fun observeViewModel() {

        val owner = { lifecycle }

        with(appViewModel) {
            //observe the beneficiary call
            accountValidationResponse.observe(owner) {
                it?.let { beneficiary ->
                    dialog.let { d->
                        d.dismiss()
                    }
                    if (beneficiary != null){
                        when(beneficiary) {
                            is NameEnquiryResult.Success -> {
                                _beneficiaryPayload = beneficiary.value
                                isValid = true
                            }
                            is NameEnquiryResult.Failure -> {
                                Toast.makeText(context, beneficiary.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Name enquiry error please check the fields and try again", Toast.LENGTH_SHORT).show()
                    }
                    //toggleAccountNameVisibility()
                    validateInput()
                }
            }
        }
    }


    override fun onDataReceived(data: BankData) {
        _selectedBank = data
        userBankName = data.name
        userBankCode = data.code
        userBankId = data.id
        val bankText: EditText = bankNameEntry
        bankText.setText(data.name)
        fetchUserAccountName()
        //validateBeneficiary()
    }


    //this call initiate cash transfer
    private fun InitiateCashTransfer(){
        //initiateCashTransfer.execute(amount, cardType.toString())
    }
    companion object {

        @JvmStatic
        fun newInstance() = SendCashLandingFragment()
    }

    override fun OnSendCashInitialize(tranxnInitiateData: TranxnInitiateModel?) {
        when {
            tranxnInitiateData==null -> {
                //hide loader
                binding.loader.visibility = View.GONE

                val text = "Oops! Server Unavailable at the moment"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
                return
            }
            tranxnInitiateData.status ==200 -> {
                //get initiate transaction response
                binding.loader.visibility = View.GONE
                val transId = Constants.SendCashInitTransId
                val action = SendCashLandingFragmentDirections.actionSendCashToSendCashSummaryFragment(
                        cashAmount,acctNum,userAcctName,userBankName,userBankCode,userBankId,narration,transId.toString()
                )
                findNavController().navigate(action)
                return
            }
            tranxnInitiateData.status !=200 -> {
                //hide loader
                binding.loader.visibility = View.GONE
                val text = tranxnInitiateData?.message.toString()
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
                return
            }
        }

    }

    override fun OnSendCashComplete(commpleteTranxnData: CompleteTransactionModel?) {
        //do nothing for now
    }

    override fun getBankList(banks: AstraBankModel?) {

        Constants.BANK_LIST.clear()
        banks?.getData()?.let { Constants.BANK_LIST.addAll(it) }
    }

    override fun getUserAccountName(accountDetailModel: AccountDetailModel?) {
        if(accountDetailModel?.status==200){
            isValid = true
            userAcctName = accountDetailModel.data?.accountName.toString()
            toggleAccountNameVisibility()
        }
        else if(accountDetailModel?.status!=200){
            val text = accountDetailModel?.message.toString()
            val duration = Toast.LENGTH_LONG
            //lock entry and button

            Toast.makeText(context, text, duration).show()
            return
        }

    }

}