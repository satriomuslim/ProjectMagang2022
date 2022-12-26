package com.qatros.binamurid.customview

import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.qatros.binamurid.R

class SchoolEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
        hint = resources.getString(R.string.school)
    }

    private fun init() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        setBackground("navy")

        doAfterTextChanged {
            when {
                text.isNullOrBlank() -> {
                    setBackground("red")
                }
                else -> {
                    setBackground("blue")
                }
            }
        }
    }

    private fun setBackground(color: String) {
        background = when (color) {
            "navy" -> ContextCompat.getDrawable(context, R.drawable.bg_edt)
            "red" -> ContextCompat.getDrawable(context, R.drawable.bg_edt_error)
            "blue" -> ContextCompat.getDrawable(context, R.drawable.bg_edt_accepted)
            else -> null
        }
    }
}