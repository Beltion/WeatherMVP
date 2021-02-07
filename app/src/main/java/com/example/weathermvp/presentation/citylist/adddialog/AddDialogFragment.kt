package com.example.weathermvp.presentation.citylist.adddialog

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weathermvp.R
import com.example.weathermvp.business.AddDialogView
import com.example.weathermvp.business.CityListView
import com.google.android.material.textfield.TextInputEditText

// Used for add city in CityList. This class call when CityList fab clicked

class AddDialogFragment: AddDialogView, DialogFragment() {

    private val TAG = AddDialogFragment::class.simpleName
    private lateinit var presenter: AddDialogPresenterImpl

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        presenter.onCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = AddDialogPresenterImpl()
        presenter.initV(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.city_list_add_dialog, null))
                    // Add action buttons
                    .setPositiveButton(R.string.find,
                            DialogInterface.OnClickListener { dialog, id ->
                                Log.d(TAG, dialog.toString())
                                val etCity = getDialog()?.findViewById<TextInputEditText>(R.id.city_et_city_list)
                                presenter.onPositiveBtnClick(etCity?.text.toString())
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog()?.cancel()
                            })
            if (savedInstanceState != null){
                val etCity = dialog?.findViewById<TextInputEditText>(R.id.city_et_city_list)
                etCity?.setText(savedInstanceState.get("city").toString())
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun showToast(str: String) {
        (activity as CityListView).showToast(str)
    }

    override fun getStringFromID(id: Int)
        = (activity as CityListView).getStringFromID(id)


    override fun showDialogAgain(city: String) {
        (activity as CityListView).showAddDialog(city)
    }

    override fun startAddCityWeather(city: String) {
        (activity as CityListView).onDialogStartAddCityWeather(city)
    }
}