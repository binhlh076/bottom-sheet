package asia.teqnological.bottomsheet.item

class BottomSheetItem {
    private var text: String? = null
    private var color: Int? = null

    constructor(text: String?) {
        this.text = text
    }

    class Builder {
        private var text: String? = null
        private var color: Int? = null

        constructor(bottomSheetItem: BottomSheetItem) {
            this.text = bottomSheetItem.text
            this.color = bottomSheetItem.color
        }

        fun setText(text: String?): Builder {
            this.text = text
            return this
        }

        fun setColor(color: Int): Builder {
            this.color = color
            return this
        }

        fun create(): BottomSheetItem {
            return BottomSheetItem(text)
        }
    }
}