package com.example.foodorderingapplication.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun EditText.afterTextChanged(textInputLayout: TextInputLayout) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textInputLayout.error = null
        }

        override fun afterTextChanged(editable: Editable?) {
            if (!editable.isNullOrEmpty())
                textInputLayout.error = null
            else
                textInputLayout.error = "This can't be empty"
        }
    })
}