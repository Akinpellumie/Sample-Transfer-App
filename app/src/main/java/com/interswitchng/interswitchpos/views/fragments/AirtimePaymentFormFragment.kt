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
import com.interswitchng.interswitchpos.databinding.FragmentAirtimePaymentFormBinding
import com.interswitchng.interswitchpos.views.services.AirtimeRechargeRequests
import com.interswitchng.interswitchpos.views.services.interfaces.IAirtimeInitiateCallBack
import com.interswitchng.interswitchpos.views.services.model.transaction.initiate.TranxnInitiateModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AirtimePaymentFormFragment : Fragment(), IAirtimeInitiateCallBack {

    private val viewmodel : AppViewModel by viewModel()
    private val args by navArgs<AirtimePaymentFormFragmentArgs>()
    private val rechargeType by lazy { args.rechargeType }
    private lateinit var binding: FragmentAirtimePaymentFormBinding

    private val airtimeRechargeRequests = AirtimeRechargeRequests(this);

//    private val terminalInfo by lazy {
//        IswTxnHandler().getTerminalInfo()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_airtime_payment_form, container, false)
//
//    }
//
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_airtime_payment_form, container, false)

    //update view UI
    val fullRechargeType = rechargeType + " " + "Recharge"
    binding.rechargeType.text = fullRechargeType

    //initiate airtime payment
        binding.initAirtimePayBtn.setOnClickListener {
            if (binding.airtimePhoneNoEntry.text.isNotEmpty() || binding.airtimeAmountEntry.text.isNotEmpty()) {

            //send data
                val phoneNumber = binding.airtimePhoneNoEntry.text.toString()
                val rechargeAmount = binding.airtimeAmountEntry.text.toString()

                airtimeRechargeRequests.execute(phoneNumber,rechargeAmount, rechargeType)
            }
    else {
        val text = "Oops!! Entry Field cannot be empty!!"
        val duration = Toast.LENGTH_LONG

        Toast.makeText(context, text, duration).show()
    }
//            val action = HomeLandingFragmentDirections.actionHomeToAmountFragment2()
//            findNavController().navigate(action)
        }

//        val level = IswTxnHandler().getBatterLevel(requireContext())
//       showSnack(binding.iswCashOutText, "Battery Level is: $level")
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AirtimePaymentFormFragment()
    }

    override fun OnAirtimeInitiate(tranxnInitiateData: TranxnInitiateModel?) {

        //navigate to other view passing user data
        //val tranxnId = tr?.data?.profileInfo?.firstname.toString()
        val tranxnId = tranxnInitiateData?.data?.getTransactionId();
        //val action = LoginLandingFragmentDirections.actionLoginToHomeFragment(userFirstname)
        //findNavController().navigate(action)
    }
}