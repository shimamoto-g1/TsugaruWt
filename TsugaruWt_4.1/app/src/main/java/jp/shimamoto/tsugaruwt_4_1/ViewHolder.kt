package jp.shimamoto.tsugaruwt_4_1

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_result.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var sWtText: TextView? = null
    var mWtText: TextView? = null
    var msurYear: TextView? = null
    var msurDay_of_munth: TextView? = null

    init {
        sWtText = itemView.sWtText
        mWtText = itemView.mWtText
        // msurYear = itemView.msurYear    //// one_result.xml から msurYear を削除したらエラーになった
        msurDay_of_munth = itemView.msurDay_of_munth
    }

}