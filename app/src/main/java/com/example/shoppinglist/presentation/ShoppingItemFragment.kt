package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem.Companion.UNDEFINED_ID
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemFragment(
    private var screenMode: String = MODE_UNKNOWN,
    private var shoppingItemId: Int = UNDEFINED_ID
) : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    private lateinit var viewModel: ShoppingItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[ShoppingItemViewModel::class.java]
        initViews(view)
        setupInputObservers()
        setupFinishObserver()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun setupInputObservers() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tilCount.error = getString(R.string.error_non_positive_count)
            }
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tilName.error = getString(R.string.error_name_not_empty)
            }
        }

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
                tilName.error = null
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
                tilCount.error = null
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun launchEditMode() {
        viewModel.shoppingItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            viewModel.changeShoppingItemData(etName.text.toString(), etCount.text.toString())
        }

        viewModel.getShoppingItem(shoppingItemId)

    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShoppingItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun setupFinishObserver() {
        viewModel.canClose.observe(viewLifecycleOwner) {

        }
    }

    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Param screen mode is absent")
        }

        if (screenMode == MODE_EDIT && shoppingItemId == UNDEFINED_ID) {
            throw RuntimeException("Param shopping item id is absent")
        }
    }

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.til_name)
            tilCount = findViewById(R.id.til_count)
            etName = findViewById(R.id.et_name)
            etCount = findViewById(R.id.et_count)
            buttonSave = findViewById(R.id.buttonSave)
        }
    }

    companion object {
        private const val TAG = "ShoppingItemActivity"

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentEdit(context: Context, shoppingItemId: Int): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOPPING_ITEM_ID, shoppingItemId)
            return intent
        }

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
    }
}