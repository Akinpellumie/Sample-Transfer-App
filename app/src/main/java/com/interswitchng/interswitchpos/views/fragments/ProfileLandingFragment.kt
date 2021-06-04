package com.interswitchng.interswitchpos.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.databinding.FragmentProfileLandingBinding
import com.interswitchng.interswitchpos.databinding.FragmentSendCashTransferSummaryBinding
import com.interswitchng.interswitchpos.views.services.Constants
import com.interswitchng.interswitchpos.views.services.callback.IChangePinCallBack
import com.interswitchng.interswitchpos.views.services.model.pin.PinModel
import com.interswitchng.interswitchpos.views.services.request.ChangePinRequest
import com.interswitchng.interswitchpos.views.services.request.LoginService


class ProfileLandingFragment : Fragment(), IChangePinCallBack {
    private lateinit var binding: FragmentProfileLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_profile_landing, container, false)
        setUpUI()
        GeneralEvents()
        return binding.root
    }

    //click events
    private fun GeneralEvents(){

        //onclick event of profile tab
        binding.profileTab.setOnClickListener{
            //set default active button
            binding.profileTab.setBackgroundResource(R.drawable.profile_tab_active)
            binding.changePinTab.setBackgroundResource(R.drawable.change_pin_tab_inactive)

            //set default display view
            binding.profileContent.visibility = View.VISIBLE
            binding.changePinContent.visibility = View.GONE

            //set default tab text color
            binding.profileTxt.setTextColor(Color.WHITE)
            binding.pinTxt.setTextColor(Color.BLACK)
        }

        //onclick event of changePin tab
        binding.changePinTab.setOnClickListener{
            //set default active button
            binding.profileTab.setBackgroundResource(R.drawable.profile_tab_inactive)
            binding.changePinTab.setBackgroundResource(R.drawable.change_pin_tab_active)

            //set default display view
            binding.profileContent.visibility = View.GONE
            binding.changePinContent.visibility = View.VISIBLE

            //set default tab text color
            binding.profileTxt.setTextColor(Color.BLACK)
            binding.pinTxt.setTextColor(Color.WHITE)
        }

        //call changePin handler
        binding.setPinBtn.setOnClickListener{
            val oldPin = binding.astraOldpin.text.toString()
            val newPin = binding.astraNewpin.text.toString()
            val confirmPin = binding.astraConfirmPin.text.toString()

            if(oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()){
                val text = "Oops! Entry Fields cannot be empty"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
            }
            else if(newPin!=confirmPin){
                val text = "Oops! New PIN and confirm PIN does not match!"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
            }
            else{
                //show loader
                binding.loader.visibility = View.VISIBLE
                //call changePin Request Java class
                val changePinRequest = ChangePinRequest(this)
                changePinRequest.execute(oldPin,newPin)
            }
        }
    }
    private fun setUpUI() {
        //set default active button
        binding.profileTab.setBackgroundResource(R.drawable.profile_tab_active)
        binding.changePinTab.setBackgroundResource(R.drawable.change_pin_tab_inactive)

        //set default display view
        binding.profileContent.visibility = View.VISIBLE
        binding.changePinContent.visibility = View.GONE

        //set default tab text color
        binding.profileTxt.setTextColor(Color.WHITE)
        binding.pinTxt.setTextColor(Color.BLACK)

        //set static profile details value
        binding.astraUsername.setText(Constants.loggedInAgentUsername.toString())
        binding.phone.setText(Constants.loggedInAgentPhoneNumber.toString())
        binding.email.setText(Constants.loggedInAgentEmail.toString())
        binding.fullname.setText(Constants.loggedInAgentFullname.toString())

        //locking the profile entry view
        binding.astraUsername.isEnabled = false
        binding.phone.isEnabled = false
        binding.fullname.isEnabled = false
        binding.email.isEnabled = false
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileLandingFragment()
    }

    override fun getChangePinResponse(pinModel: PinModel?) {
        when {
            pinModel==null -> {
                //hide loader
                binding.loader.visibility = View.GONE

                val text = "Oops! Server Unavailable at the moment"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, text, duration).show()
                return
            }
            pinModel.getStatus() == 200 -> {
                val text = "Pin changed successfully. Login again!"
                val duration = Toast.LENGTH_LONG
                Toast.makeText(context, text, duration).show()
                val action = ProfileLandingFragmentDirections.actionChangePinToLoginFragment()
                findNavController().navigate(action)
                //hide loader
                binding.loader.visibility = View.GONE
            }
            pinModel.getStatus() != 200 -> {
                //hide loader
                binding.loader.visibility = View.GONE
                val text = pinModel?.message
                val duration = Toast.LENGTH_LONG
                Toast.makeText(context, text, duration).show()
            }
        }
    }
}