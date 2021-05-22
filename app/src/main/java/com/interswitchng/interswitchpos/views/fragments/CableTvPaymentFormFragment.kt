package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentCableTvPaymentFormBinding
import com.interswitchng.interswitchpos.views.services.CableTvDataLists
import com.interswitchng.interswitchpos.views.services.interfaces.ICableTvPayCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.completeBillpay.CompleteTransactionModel
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.services.request.CableTvPayInitService
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CableTvPaymentFormFragment : Fragment(), ICableTvPayCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private lateinit var binding: FragmentCableTvPaymentFormBinding;
    private val args by navArgs<CableTvPaymentFormFragmentArgs>()
    private val cableTvType by lazy { args.cableType }

    private val cableTvPayInitService = CableTvPayInitService(this);

    var phoneNumber : String = ""
    var cableTvPlanAmount = "";
    var cableTvPlanPayCode = "";
    var smartCardNumber = "";
    var selectedPlan = "";
    var customerName = "";


    private val cableTvDataLists = CableTvDataLists();

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // ArrayList for person names, email Id's and mobile numbers
        val cableTvPlans = ArrayList<String>()
        val cableTvPlansAmount = ArrayList<String>()
        val cableTvPlansPayCode = ArrayList<String>()
        for (item in cableTvDataLists.getCableData(activity?.applicationContext, cableTvType)){
            cableTvPlans.add(item.paymentitemname);
            cableTvPlansAmount.add(item.itemFee);
            cableTvPlansPayCode.add(item.paymentCode);
        }

        val t=inflater.inflate(R.layout.fragment_cable_tv_payment_form, container, false)
        val spinner = t.findViewById<Spinner>(R.id.codeSpinner)
        spinner?.adapter = ArrayAdapter(activity?.applicationContext, R.layout.support_simple_spinner_dropdown_item, cableTvPlans) as SpinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPlan = parent?.getItemAtPosition(position).toString()
                //cableTvPlanAmount = cableTvDataLists.getCableData(activity?.applicationContext, cableTvType)[position].itemFee
                cableTvPlanAmount = cableTvPlansAmount[position]
                //cableTvPlanPayCode = cableTvDataLists.getCableData(activity?.applicationContext, cableTvType)[position].paymentCode
                cableTvPlanPayCode = cableTvPlansPayCode[position]
                binding.amountEv.setText(cableTvPlanAmount)
                Toast.makeText(activity, selectedPlan, Toast.LENGTH_LONG).show()
                println(selectedPlan)
            }

        }
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cable_tv_payment_form, container, false)





        binding.initiateTransaction.setOnClickListener {
            if (binding.smartCardNumberEv.text.isNotEmpty() && binding.customerNameEv.text.isNotEmpty() && binding.amountEv.text.isNotEmpty() && selectedPlan.isNotEmpty()) {

                //send data
                phoneNumber = binding.phoneNumberEv.text.toString()
                cableTvPlanAmount = binding.amountEv.text.toString()
                smartCardNumber = binding.smartCardNumberEv.text.toString()
                customerName = binding.customerNameEv.text.toString()

               // String amount, String phoneNumber, String smartCardNumber, String customerName, String plan, String cableTvType, String payCode
                cableTvPayInitService.execute(cableTvPlanAmount, phoneNumber, smartCardNumber, customerName, selectedPlan, cableTvType, cableTvPlanPayCode)

                //airtimeRechargeRequests.execute(phoneNumber,rechargeAmount, rechargeType)
            } else {
                val text = "Oops!! Entry Field cannot be empty!!"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()


            }
        }
        return binding.root

//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_landing, container, false)
//        if (terminalInfo != null) {
//            viewmodel.getToken(terminalInfo!!)
//        }
////        binding.iswTransferCard.setOnClickListener {
//////            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
////            val action = HomeLandingFragmentDirections.actionHomeToAmountFragment2()
////            findNavController().navigate(action)
////        }
//
//        val level = IswTxnHandler().getBatterLevel(requireContext())
//       showSnack(binding.iswCashOutText, "Battery Level is: $level")
//        return binding.root
   }



    companion object {
        @JvmStatic
        fun newInstance() = CableTvPaymentFormFragment()
    }

    override fun OnCableTvPayInitialize(tranxnInitiateData: TranxnInitiateModel?) {
        val tranxnId = tranxnInitiateData?.data?.getTransactionId().toString();
        val action = CableTvPaymentFormFragmentDirections.actionCableTvFormToCableTvPaymentSummary(cableTvPlanAmount, phoneNumber, smartCardNumber, customerName, selectedPlan, tranxnId, cableTvType)
        findNavController().navigate(action)
    }

    override fun OnCableTvPayComplete(commpleteTranxnData: CompleteTransactionModel?) {
        TODO("Not yet implemented")
    }
}