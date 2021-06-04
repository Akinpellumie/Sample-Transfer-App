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
import com.interswitchng.interswitchpos.databinding.FragmentSetPinLandingBinding
import com.interswitchng.interswitchpos.views.services.callback.IChangePinCallBack
import com.interswitchng.interswitchpos.views.services.callback.ISetPinCallBack
import com.interswitchng.interswitchpos.views.services.model.pin.PinModel
import com.interswitchng.interswitchpos.views.services.request.ChangePinRequest
import com.interswitchng.interswitchpos.views.services.request.SetPinRequest


class SetPinLandingFragment : Fragment(), ISetPinCallBack {
    private lateinit var binding: FragmentSetPinLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_set_pin_landing, container, false)
        GeneralEvents()
        return binding.root
    }

    //click events
    private fun GeneralEvents(){

        //call changePin handler
        binding.setPinBtn.setOnClickListener{
            val requestId = binding.astraOtp.text.toString()
            val newPin = binding.astraNewpin.text.toString()
            val confirmPin = binding.astraConfirmPin.text.toString()

            if(requestId.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()){
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

                //call loader
                binding.loader.visibility = View.VISIBLE

                //call changePin Request Java class
                val setPinRequest = SetPinRequest(this)
                setPinRequest.execute(requestId,newPin)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SetPinLandingFragment()
    }


    override fun getSetPinResponse(pinModel: PinModel?) {
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
                val action = SetPinLandingFragmentDirections.actionSetPinToLoginFragment()
                findNavController().navigate(action)
                //hide loader
                binding.loader.visibility = View.GONE
            }
            pinModel?.getStatus() != 200 -> {
                //hide loader
                binding.loader.visibility = View.GONE
                val text = pinModel?.message
                val duration = Toast.LENGTH_LONG
                Toast.makeText(context, text, duration).show()
            }
        }
    }
}