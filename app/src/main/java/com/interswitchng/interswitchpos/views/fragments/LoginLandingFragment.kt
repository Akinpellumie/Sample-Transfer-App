package com.interswitchng.interswitchpos.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentHomeLandingBinding
import com.interswitchng.interswitchpos.databinding.FragmentLoginLandingBinding
import com.interswitchng.interswitchpos.views.services.LoginService
import com.interswitchng.interswitchpos.views.services.interfaces.ILoginCallBack
import com.interswitchng.interswitchpos.views.services.model.login.LoginModel
import com.interswitchng.interswitchpos.views.viewmodels.AppViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginLandingFragment : Fragment(), ILoginCallBack {

    private val viewmodel : AppViewModel by viewModel()

    private lateinit var binding: FragmentLoginLandingBinding
    lateinit var mainBinding: FragmentHomeLandingBinding
    private val loginService = LoginService(this)


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
            val userId = binding.username.text.toString()
            val userPin = binding.password.text.toString()

            loginService.execute(userId,userPin)

//            val action = LoginLandingFragmentDirections.actionLoginToHomeFragment()
//                findNavController().navigate(action)


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

    override fun OnLogin(user: LoginModel?) {
        //navigate to other view passing user data
        val userFirstname = user?.data?.profileInfo?.firstname.toString()
        val action = LoginLandingFragmentDirections.actionLoginToHomeFragment(userFirstname)
        findNavController().navigate(action)
    }
}