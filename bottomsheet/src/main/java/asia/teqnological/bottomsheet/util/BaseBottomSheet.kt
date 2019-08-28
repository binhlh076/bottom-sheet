package asia.teqnological.bottomsheet.util

import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import asia.teqnological.bottomsheet.model.ActionItem
import asia.teqnological.bottomsheet.model.BottomSheetDivider
import asia.teqnological.bottomsheet.model.BottomSheetTitle
import java.lang.ref.WeakReference

abstract class BaseBottomSheet {
    protected var fragmentManager: FragmentManager? = null
    protected var actionItems: ArrayList<ActionItem>? = null
    protected var cancelItem: ActionItem? = null
    protected var title: BottomSheetTitle? = null
    protected var divider: BottomSheetDivider? = null
    protected var titleFont: BottomSheetEnums.Font? = null

    constructor(
        fragmentManager: FragmentManager? = null,
        actionItems: ArrayList<ActionItem>? = null,
        cancelItem: ActionItem? = null,
        title: BottomSheetTitle? = null,
        divider: BottomSheetDivider? = null,
        titleFont: BottomSheetEnums.Font? = null
    ) {
        this.fragmentManager = fragmentManager
        this.actionItems = actionItems
        this.cancelItem = cancelItem
        this.title = title
        this.divider = divider
        this.titleFont = titleFont
    }

    abstract fun init()

    abstract fun show()

    abstract fun show(animation: Animation)

    abstract fun dismiss()

    interface OnDismissBottomSheetListener {
        fun onDismissed()
    }

}