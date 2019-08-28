package asia.teqnological.bottomsheet.util

import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import asia.teqnological.bottomsheet.R


abstract class BaseDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_CustomDialog)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val width = size.x
        val params = window.attributes
        params.width = width * 8 / 9
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = params
        window.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        window.attributes.windowAnimations = R.style.diaLogAnimation
        window.setBackgroundDrawable(ColorDrawable(0x0000000))
    }
}