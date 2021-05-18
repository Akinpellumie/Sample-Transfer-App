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
import com.interswitchng.interswitchpos.utils.BankFilterDialog
import com.interswitchng.interswitchpos.utils.SendCashBankFilterDialog
import com.interswitchng.interswitchpos.utils.customdailog
import com.interswitchng.interswitchpos.views.services.SendCashTransferInitService
import com.interswitchng.interswitchpos.views.services.TransactionCompleteNotifier
import com.interswitchng.interswitchpos.views.services.interfaces.ISendCashTransferCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.models.BankModel
import com.interswitchng.smartpos.models.BeneficiaryModel
import com.interswitchng.smartpos.models.CallbackListener
import com.interswitchng.smartpos.models.NameEnquiryResult
import com.interswitchng.smartpos.shared.utilities.getTextValue
import kotlinx.android.synthetic.main.fragment_account_setup.*
import kotlinx.android.synthetic.main.fragment_send_cash_landing.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class SendCashLandingFragment : Fragment(), CallbackListener, ISendCashTransferCallBack {
    private lateinit var binding : FragmentSendCashLandingBinding
    private var bankList = arrayListOf<BankModel>()
    lateinit var _selectedBank: BankModel
    lateinit var submitButton: Button
    lateinit var accountNumberEditor: EditText
    lateinit var _beneficiaryPayload: BeneficiaryModel
    var isValid = false
    lateinit var dialog: Dialog
    var accountNumber = ""
    var userAcctName = ""
    var userBankName = ""
    var cashAmount = ""
    var acctNum = ""
    var narration = ""
    var useNameEnquiry = true
    private val appViewModel: AppViewModel by viewModel()
    private val sendCashTransferInitService = SendCashTransferInitService(this)

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
                            validateBeneficiary()
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



        //proceed to summary page
        binding.initiateBtn.setOnClickListener {
            cashAmount = binding.amountEntry.text.toString()
            acctNum = binding.acctNumEntry.text.toString()
            val acctName = binding.acctNameEntry.text
            val bankName = binding.bankNameEntry.text
            narration = binding.narrationEntry.text.toString()
            //call initiate transaction
            binding.llProgressBar.visibility = View.VISIBLE
            sendCashTransferInitService.execute(cashAmount,acctNum,userAcctName, userBankName, narration)

        }
    }

    private fun validateBeneficiary() {
        //val accountNumber = binding.acctNumEntry.text


        if (accountNumber?.length == 10 && this::_selectedBank.isInitialized) {

            if(!useNameEnquiry) {
                _beneficiaryPayload = BeneficiaryModel()
                _beneficiaryPayload.accountName = binding.acctNameEntry.text.toString()
                _beneficiaryPayload.accountNumber = accountNumber.toString()
                isValid = !binding.acctNameEntry.getTextValue().isNullOrEmpty()
                validateInput()
                return
            }

            if (!this::dialog.isInitialized) {
                dialog = customdailog(this.requireContext())
            }else {
                dialog.show()
            }
            appViewModel.validateBeneficiary(_selectedBank.selBankCodes!!, accountNumber!!)
        } else {
            isValid = false
            toggleAccountNameVisibility()
            validateInput()
        }
        if (accountNumber?.length == 10) {
            appViewModel.validateBeneficiary(_selectedBank.selBankCodes.toString(), accountNumber)
        }
    }
    private fun toggleAccountNameVisibility() {
        if (isValid || !useNameEnquiry) {
            binding.acctNameEntry.visibility = View.VISIBLE
            binding.acctNameEntry.visibility = View.VISIBLE
            if(this::_beneficiaryPayload.isInitialized) binding.acctNameEntry.setText(_beneficiaryPayload.accountName)
            userAcctName = _beneficiaryPayload.accountName.toString()
        } else {
            binding.acctNameEntry.visibility = View.GONE
            //outlinedNameTextField.visibility = View.GONE
            binding.acctNameEntry.visibility = View.GONE
        }

//      make Account name input focusable based on the state of use name enquiry

    }

    private fun validateInput() {
        submitButton.alpha = if (!isValid) 0.5F else 1F
        submitButton.isEnabled = isValid
        submitButton.isClickable = isValid
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
                    toggleAccountNameVisibility()
                    validateInput()
                }
            }
        }
    }


    override fun onDataReceived(data: BankModel) {
        _selectedBank = data
        userBankName = data.bankName
        val bankText: EditText = bankNameEntry
        bankText.setText(data.bankName)
        validateBeneficiary()
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
        //call initiate transaction
        binding.llProgressBar.visibility = View.GONE
        val action = SendCashLandingFragmentDirections.actionSendCashToSendCashSummaryFragment(
                cashAmount,acctNum,userAcctName,userBankName,narration
        )
        findNavController().navigate(action)
    }
}