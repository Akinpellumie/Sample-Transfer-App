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
import com.interswitchng.interswitchpos.databinding.FragmentCableTvPaymentFormBinding
import com.interswitchng.interswitchpos.databinding.FragmentElectricityPaymentFormBinding
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.domain.models.PaymentType
import com.interswitchng.interswitchpos.utils.showSnack
import com.interswitchng.interswitchpos.views.services.AirtimeRechargeRequests
import com.interswitchng.interswitchpos.views.services.ElectricityPayInitService
import com.interswitchng.interswitchpos.views.services.interfaces.IElectricityPayCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import com.interswitchng.smartpos.IswTxnHandler
import com.interswitchng.smartpos.models.core.TerminalInfo
import org.koin.android.viewmodel.ext.android.viewModel

class ElectricityPaymentFormFragment : Fragment(), IElectricityPayCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private val args by navArgs<ElectricityPaymentFormFragmentArgs>()
    private val electricityCompany by lazy { args.electricityCompany }
    private lateinit var binding: FragmentElectricityPaymentFormBinding;

    private val electricityPayInitService = ElectricityPayInitService(this);

    var electricityAmount = "";
    var phoneNumber = "";
    var customerId = "";
    var customerName = "";
    //private lateinit var binding:

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //update view UI
        val fullElectricityCompanyName = electricityCompany + " " + "Electricity Distribution Company"
        binding.electricityCompany.text = fullElectricityCompanyName

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_electricity_payment_form, container, false)

        binding.initElectricityPayBtn.setOnClickListener {
            if (binding.customerIdEt.text.isNotEmpty() || binding.electricityAmountEntry.text.isNotEmpty() || binding.phoneNumberEt.text.isNotEmpty()) {

                //send data
                phoneNumber = binding.phoneNumberEt.text.toString()
                electricityAmount = binding.electricityAmountEntry.text.toString()
                customerId= binding.customerIdEt.text.toString()
                customerName= binding.customerNameEt.text.toString()

                //airtimeRechargeRequests.execute(phoneNumber,rechargeAmount, rechargeType)
                electricityPayInitService.execute(phoneNumber,electricityAmount,customerId,customerName, electricityCompany)
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


    companion object {
        @JvmStatic
        fun newInstance() = ElectricityPaymentFormFragment()
    }

    override fun OnElectricityPayInitialize(tranxnInitiateData: TranxnInitiateModel?) {
        val tranxnId = tranxnInitiateData?.data?.getTransactionId().toString();


        val action =ElectricityPaymentFormFragmentDirections.actionElectricityPaymentFormToElectricityPaymentSummaryFragment(
                phoneNumber, electricityAmount, customerId, customerName,electricityCompany, tranxnId)
        findNavController().navigate(action)
    }

    override fun OnElectricityPayComplete(commpleteTranxnData: CompleteTransactionModel?) {
        TODO("Not yet implemented")
    }
}