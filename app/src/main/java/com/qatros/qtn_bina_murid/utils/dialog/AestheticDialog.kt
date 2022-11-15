package com.qatros.qtn_bina_murid.utils.dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.qatros.qtn_bina_murid.R

/**
 * Aesthetic Dialog class
 * Use Builder to create a new instance.
 *
 * @author Gabriel The Code
 */

@Keep
class AestheticDialog {

    class Builder(
            //Necessary parameters
        private val activity: Activity,
        private val dialogStyle: DialogStyle
    ) {

        private lateinit var alertDialog: AlertDialog
        private val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)

        private var title: String = "Title"
        private var message: String = "Message"
        // Optional features
        private var isCancelable: Boolean = true
        private var gravity: Int = Gravity.NO_GRAVITY
        private var animation: DialogAnimation = DialogAnimation.DEFAULT
        private lateinit var layoutView: View
        private var onClickListener: OnDialogClickListener = object : OnDialogClickListener {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }

        /**
         * Set dialog title text
         *
         * @param title
         * @return this, for chaining.
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * Set dialog message text
         *
         * @param message
         * @return this, for chaining.
         */
        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        /**
         * Set an OnClickListener to the dialog
         *
         * @param onDialogClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        fun setOnClickListener(onDialogClickListener: OnDialogClickListener): Builder {
            this.onClickListener = onDialogClickListener
            return this
        }

        /**
         * Set the animation of the dialog
         *
         * @param animation in milliseconds
         * @return this, for chaining.
         */
        fun setAnimation(animation: DialogAnimation): Builder {
            this.animation = animation
            return this
        }

        /**
         * Dismiss the dialog
         *
         * @return Aesthetic Dialog instance.
         */
        fun dismiss(): AestheticDialog {
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
            return AestheticDialog()
        }


        /**
         * Choose the dialog animation according to the parameter
         *
         */
        @NonNull
        private fun chooseAnimation() {
                when (animation) {
                    DialogAnimation.SHRINK -> {
                        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimationShrink
                    }
                    DialogAnimation.DEFAULT ->{
                        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
                    }
                }
        }


        /**
         * Displays the dialog according to the parameters of the Builder
         *
         * @return Aesthetic Dialog instance.
         */
        fun show(): AestheticDialog {

            when (dialogStyle) {
                DialogStyle.FLASH -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.popup_korfirm_email, null)
                    val btnOk: AppCompatButton = layoutView.findViewById(R.id.btn_check_email)
                    val textTitle: AppCompatTextView = layoutView.findViewById(R.id.tv_conf_email)
                    val textMessage: AppCompatTextView = layoutView.findViewById(R.id.tv_conf_email_again)

                    textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    alertDialog.window?.setLayout(width, height)
                    btnOk.setOnClickListener { onClickListener.onClick(this) }
                }
                DialogStyle.DINE -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.popup_add_email_succes, null)
                    val btnOk: AppCompatButton = layoutView.findViewById(R.id.btn_check_email)
                    val textTitle: AppCompatTextView = layoutView.findViewById(R.id.tv_conf_email)
                    val textMessage: AppCompatTextView = layoutView.findViewById(R.id.tv_conf_email_again)

                    textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    alertDialog.window?.setLayout(width, height)
                    btnOk.setOnClickListener { onClickListener.onClick(this) }
                }

            }

            alertDialog.setCancelable(isCancelable)
            if (gravity != Gravity.NO_GRAVITY) {
                alertDialog.window?.setGravity(gravity)
            }
            return AestheticDialog()
        }
    }
}