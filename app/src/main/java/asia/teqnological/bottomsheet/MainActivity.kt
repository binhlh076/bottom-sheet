package asia.teqnological.bottomsheet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import asia.teqnological.bottomsheet.model.ActionItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomSheet = BottomSheet.Builder(this)
            .setCancelItem(createCancelItem())
            .setActionItems(createListAction()).build()
        btn_open?.setOnClickListener {
            bottomSheet.show()
        }
        bottomSheet.show()
    }

    private fun createListAction(): ArrayList<ActionItem> {
        val list = arrayListOf<ActionItem>()
        for (i in 0..3) {
            list.add(ActionItem("Action button $i", tag = "Action button $i", action = {
                Toast.makeText(this, it?.tag, Toast.LENGTH_SHORT).show()
            }, typeface = R.font.font2, color = R.color.colorPrimary))
        }
        return list
    }

    private fun createCancelItem(): ActionItem {
        return ActionItem("Finish", typeface = R.font.font2)
    }
}
