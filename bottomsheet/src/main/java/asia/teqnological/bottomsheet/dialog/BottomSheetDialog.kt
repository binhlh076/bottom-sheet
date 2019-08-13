package asia.teqnological.bottomsheet.dialog

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import asia.teqnological.bottomsheet.R
import asia.teqnological.bottomsheet.model.ActionItem
import asia.teqnological.bottomsheet.model.BottomSheetDivider
import asia.teqnological.bottomsheet.model.BottomSheetTitle
import asia.teqnological.bottomsheet.util.BaseDialogFragment
import kotlinx.android.synthetic.main.item_action_view.view.*

class BottomSheetDialog : BaseDialogFragment() {
    private var actionItems: ArrayList<ActionItem>? = null
    private var cancelItem: ActionItem? = null
    private var title: BottomSheetTitle? = null
    private var divider: BottomSheetDivider? = null

    companion object {
        const val LIST_ACTION = "LIST_ACTION"
        const val CANCEL_ITEM = "CANCEL_ITEM"
        const val DIVIDER_ITEM = "DIVIDER"
        const val TITLE_ITEM = "TITLE"

        fun getInstance(
                actionItems: ArrayList<ActionItem>? = null,
                cancelItem: ActionItem? = null,
                title: BottomSheetTitle? = null,
                divider: BottomSheetDivider? = null
        ): BottomSheetDialog {
            val dialog = BottomSheetDialog()
            val bundle = Bundle()
            bundle.putSerializable(LIST_ACTION, actionItems)
            bundle.putSerializable(CANCEL_ITEM, cancelItem)
            bundle.putSerializable(TITLE_ITEM, title)
            bundle.putSerializable(DIVIDER_ITEM, divider)
            dialog.arguments = bundle
            return dialog
        }

        private enum class ActionPosition {
            TOP, CENTER, BOTTOM, CANCEL_ITEM
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            actionItems = getSerializable(LIST_ACTION) as ArrayList<ActionItem>?
            cancelItem = getSerializable(CANCEL_ITEM) as ActionItem?
            title = getSerializable(TITLE_ITEM) as BottomSheetTitle?
            divider = getSerializable(DIVIDER_ITEM) as BottomSheetDivider?
        }
        setupCancelItem()
        setupListActionItem()
    }

    private fun setupCancelItem() {
        view?.findViewById<AppCompatTextView>(R.id.btn_cancel)?.apply {
            cancelItem?.let {
                typeface = it.typeface?.run {
                    return@run ResourcesCompat.getFont(context, this)
                }
                text = it.text
                textSize = it.textSize ?: 14f
            }
            setOnClickListener {
                cancelItem?.action?.invoke(cancelItem)
                dismissAllowingStateLoss()
            }
        }
    }

    private fun setupListActionItem() {
        val lp =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        view?.findViewById<LinearLayout>(R.id.layout_top)?.apply {
            actionItems?.let { items ->
                when (items.size) {
                    1 -> {
                        addView(createActionItem(items[0], ActionPosition.CANCEL_ITEM), lp)
                    }
                    2 -> {
                        addView(createActionItem(items[0], ActionPosition.TOP), lp)
                        addView(createDivider(divider))
                        addView(createActionItem(items[1], ActionPosition.BOTTOM), lp)
                    }
                    else -> {
                        addView(createActionItem(items[0], ActionPosition.TOP), lp)
                        addView(createDivider(divider))
                        for (i in 1 until items.size - 1) {
                            addView(createActionItem(items[i], ActionPosition.CENTER), lp)
                            addView(createDivider(divider))
                        }
                        addView(createActionItem(items[items.size - 1], ActionPosition.BOTTOM), lp)
                    }
                }
            }
        }
    }


    private fun createDivider(divider: BottomSheetDivider? = null): View {
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, divider?.height
                ?: 1)
        val view = LayoutInflater.from(context).inflate(R.layout.item_divider_view, null)
        view.layoutParams = lp
        divider?.apply {
            view.setBackgroundResource(background)
            view.layoutParams.height = height
            view.requestLayout()
        }
        return view
    }

    private fun createActionItem(actionItem: ActionItem, position: ActionPosition): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_action_view, null)
        view.findViewById<AppCompatTextView>(R.id.tv_action_button)?.apply {
            typeface = actionItem.typeface?.run {
                return@run ResourcesCompat.getFont(context, this)
            }
            text = actionItem.text
            textSize = actionItem.textSize ?: 14f
            setTextColor(ResourcesCompat.getColor(resources, actionItem.color, null))
        }
        val res = when (position) {
            ActionPosition.TOP -> {
                R.drawable.bg_action_item_top
            }
            ActionPosition.CENTER -> {
                R.drawable.bg_action_item
            }
            ActionPosition.BOTTOM -> {
                R.drawable.bg_action_item_bottom
            }
            ActionPosition.CANCEL_ITEM -> {
                R.drawable.bg_cancel_item
            }
        }
        view.item_action?.setBackgroundResource(res)
        actionItem.background.apply {
            val drawable = resources.getDrawable(res) as RippleDrawable?
            drawable?.mutate()
            (drawable?.getDrawable(0)?.mutate() as GradientDrawable?)?.color =
                    ColorStateList.valueOf(resources.getColor(this@apply))
            view.item_action?.setBackgroundDrawable(drawable)
        }
        view.setOnClickListener {
            actionItem.action?.invoke(actionItem)
            dismissAllowingStateLoss()
        }
        view.tag = actionItem.tag
        return view
    }
}