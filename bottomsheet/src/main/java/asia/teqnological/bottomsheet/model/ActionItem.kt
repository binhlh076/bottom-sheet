package asia.teqnological.bottomsheet.model

import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import asia.teqnological.bottomsheet.R
import java.io.Serializable

class ActionItem(
    var text: String,
    @ColorRes var color: Int = R.color.black,
    @ColorRes var background: Int = R.color.white,
    var textSize: Int? = null,
    var tag: String? = null,
    var action: ((ActionItem?) -> Unit)? = null,
    @FontRes var typeface: Int? = null
) : Serializable