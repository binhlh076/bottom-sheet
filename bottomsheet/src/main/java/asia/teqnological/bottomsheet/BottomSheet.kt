package asia.teqnological.bottomsheet

import android.content.DialogInterface
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
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
        activity: AppCompatActivity?,
        actionItems: ArrayList<ActionItem>?,
        cancelItem: ActionItem? = null,
        bottomSheetTitle: BottomSheetTitle?,
        bottomSheetDivider: BottomSheetDivider?
    ) : super(
        activity,
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
        activity?.supportFragmentManager?.apply {
            bottomSheetDialog.show(this, "bottom_sheet_dialog")
        }
    }

    override fun show(animation: Animation) {
        activity?.supportFragmentManager?.apply {
            bottomSheetDialog.show(this, "bottom_sheet_dialog")
        }
    }

    override fun dismiss() {
        bottomSheetDialog.dismissAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismissBottomSheetListener?.onDismissed()
    }

    open class Builder {
        private var activity: AppCompatActivity? = null
        private var actionItems: ArrayList<ActionItem>? = null
        private var cancelItem: ActionItem? = null
        private var title: BottomSheetTitle? = null
        private var divider: BottomSheetDivider? = null
        private var titleFont: BottomSheetEnums.Font? = null

        constructor(activity: AppCompatActivity?) {
            this.activity = activity
        }

        constructor(bottomSheet: BottomSheet) {
            this.activity = bottomSheet.activity
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
            return BottomSheet(activity, actionItems, cancelItem, title, divider)
        }
    }
}