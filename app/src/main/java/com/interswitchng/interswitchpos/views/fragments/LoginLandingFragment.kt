package com.interswitchng.interswitchpos.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentLoginLandingBinding
import com.interswitchng.interswitchpos.views.activities.MainActivity
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginLandingFragment : Fragment() {

    private val viewmodel : AppViewModel by viewModel()

    private lateinit var binding: FragmentLoginLandingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_landing, container, false)

        binding.forgotTxt.setOnClickListener {
//            val action = HomeLandingFragmentDirections.actionHomeToCardTransactionFragment(amount = "5", paymentType = PaymentType.TRANSFER.name)
            val action = LoginLandingFragmentDirections.actionLoginToForgotPinFragment()
            findNavController().navigate(action)
        }

    binding.loginBtn.setOnClickListener {
        if (binding.password.text.isNotEmpty() || binding.username.text.isNotEmpty()) {

            val action = LoginLandingFragmentDirections.actionLoginToHomeFragment()
                findNavController().navigate(action)




        } else {
            val text = "Login Failed! Enter Credentials to continue!"
            val duration = Toast.LENGTH_LONG

            Toast.makeText(context, text, duration).show()
        }
    }

//        val level = IswTxnHandler().getBatterLevel(requireContext())
//       showSnack(binding.iswCashOutText, "Battery Level is: $level")
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginLandingFragment()
    }
}