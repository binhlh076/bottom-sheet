package asia.teqnological.bottomsheet

import android.content.DialogInterface
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import asia.teqnological.bottomsheet.dialog.BottomSheetDialog
import asia.teqnological.bottomsheet.model.ActionItem
import asia.teqnological.bottomsheet.model.BottomSheetDivider
import asia.teqnological.bottomsheet.model.BottomSheetTitle
import asia.teqnological.bottomsheet.util.BaseBottomSheet
import asia.teqnological.bottomsheet.util.BottomSheetEnums

class BottomSheet : BaseBottomSheet, DialogInterface.OnDismissListener {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var onDismissBottomSheetListener: OnDismissBottomSheetListener? = null

    private constructor() {
        init()
    }

    private constructor(
        fragmentManager: FragmentManager?,
        actionItems: ArrayList<ActionItem>?,
        cancelItem: ActionItem? = null,
        bottomSheetTitle: BottomSheetTitle?,
        bottomSheetDivider: BottomSheetDivider?
    ) : super(
        fragmentManager,
        actionItems, cancelItem, bottomSheetTitle, bottomSheetDivider
    ) {

        init()
    }

    fun setOnDismissBottomSheetListener(onDismissBottomSheetListener: OnDismissBottomSheetListener? = null) {
        this.onDismissBottomSheetListener = onDismissBottomSheetListener
    }

    override fun init() {
        bottomSheetDialog = BottomSheetDialog.getInstance(actionItems, cancelItem, title, divider)
    }

    override fun show() {
        fragmentManager?.apply {
            if (actionItems == null || actionItems.isNullOrEmpty()) {
                throw Throwable("Missing Action Items..")
            } else {
                bottomSheetDialog.show(this, "bottom_sheet_dialog")
            }
        }
    }

    override fun show(animation: Animation) {
        fragmentManager.apply {
            if (actionItems == null || actionItems.isNullOrEmpty()) {
                throw Throwable("Missing Action Items..")
            } else {
                bottomSheetDialog.show(this, "bottom_sheet_dialog")
            }
        }
    }

    override fun dismiss() {
        bottomSheetDialog.dismissAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismissBottomSheetListener?.onDismissed()
    }

    open class Builder {
        private var fragmentManager: FragmentManager? = null
        private var actionItems: ArrayList<ActionItem>? = null
        private var cancelItem: ActionItem? = null
        private var title: BottomSheetTitle? = null
        private var divider: BottomSheetDivider? = null
        private var titleFont: BottomSheetEnums.Font? = null

        constructor(activity: FragmentManager?) {
            this.fragmentManager = activity
        }

        constructor(bottomSheet: BottomSheet) {
            this.fragmentManager = bottomSheet.fragmentManager
            this.actionItems = bottomSheet.actionItems
            this.cancelItem = bottomSheet.cancelItem
            this.title = bottomSheet.title
            this.divider = bottomSheet.divider
            this.titleFont = bottomSheet.titleFont
        }

        fun setActionItems(actionItems: ArrayList<ActionItem>?): Builder {
            this.actionItems = actionItems
            return this
        }

        fun setActionItems(vararg actionItems: ActionItem): Builder {
            this.actionItems = arrayListOf()
            actionItems.forEach {
                this@Builder.actionItems?.add(it)
            }
            return this
        }

        fun setCancelItem(cancelItem: ActionItem?): Builder {
            this.cancelItem = cancelItem
            return this
        }

        fun setDivider(divider: BottomSheetDivider): Builder {
            this.divider = divider
            return this
        }

        fun setTitle(title: BottomSheetTitle): Builder {
            this.title = title
            return this
        }

        fun build(): BottomSheet {
            return BottomSheet(fragmentManager, actionItems, cancelItem, title, divider)
        }
    }
}