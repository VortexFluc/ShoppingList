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
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingItem.Companion.UNDEFINED_ID
import com.google.android.material.textfield.TextInputLayout

class ShoppingItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    private lateinit var viewModel: ShoppingItemViewModel

    private var screenMode: String = MODE_UNKNOWN
    private var shoppingItemId: Int = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

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
            activity?.onBackPressed()
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Params screen mode is absent")
        }

        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOPPING_ITEM_ID)) {
                throw RuntimeException("Param shop_item_id is absent")
            }
            shoppingItemId = args.getInt(SHOPPING_ITEM_ID, UNDEFINED_ID)
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
        private const val TAG = "ShoppingItemFragment"

        private const val SCREEN_MODE = "mode"
        private const val SHOPPING_ITEM_ID = "shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShoppingItemFragment {
            return ShoppingItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shoppingItemId: Int): ShoppingItemFragment {
            return ShoppingItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOPPING_ITEM_ID, shoppingItemId)
                }
            }
        }
    }
}