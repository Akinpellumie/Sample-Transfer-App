package com.interswitchng.interswitchpos.utils


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.interswitchng.interswitchpos.R
import com.interswitchng.interswitchpos.utils.adapters.AstraBankAutoCompleteAdapter
import com.interswitchng.interswitchpos.views.fragments.SendCashLandingFragment
import com.interswitchng.interswitchpos.views.services.Constants
import com.interswitchng.interswitchpos.views.services.model.bank.BankData
import com.interswitchng.smartpos.models.BankModel
import kotlinx.android.synthetic.main.astra_bank_filter_dialog.*


class SendCashBankFilterDialog(private val sendCashCallBackListener: SendCashLandingFragment): DialogFragment() {
    val TAG = "isw_transfer_bank_filter_dialog"
    private var bankList = arrayListOf<BankData>()
    private var toolbar: Toolbar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, theme);
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
                            ): View? {
        isCancelable = false
        super.onCreateView(inflater, container, savedInstanceState)
        bankList.addAll(Constants.BANK_LIST.sortedWith(compareBy { it.name }))
        var rootView: View = inflater.inflate(R.layout.astra_bank_filter_dialog, container, false)
        return rootView
    }

    override fun getTheme(): Int {
        return R.style.ISW_TransferFilterDialog
    }

    fun setUpAutoComplete() {
        val auto: ListView = astra_transfer_bank_search
        val adapter = context?.let {
            AstraBankAutoCompleteAdapter(
                    it,
                    R.layout.isw_bank_autocomplete_row, bankList
            )
        }
        auto.setAdapter(adapter)
        //        TODO: Clear selected bank once the user does a delete activity

        auto.setOnItemClickListener { parent, view, position, id ->
            var selectedBank = parent.getItemAtPosition(position) as BankData
            sendCashCallBackListener.onDataReceived(selectedBank)
            dismiss()
        }

        val searchBar: EditText? = astra_transfer_bank_search_bar
        searchBar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter?.filter?.filter(p0)
            }

            override fun afterTextChanged(s: Editable?) {
//                adapter?.filter?.filter(s.toString())
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAutoComplete()
        astra_transfer_search_dialog_toolbar.setNavigationOnClickListener(View.OnClickListener() {
            dismiss()
        })

    }
}