package jp.shimamoto.tsugaruwt_4_1

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class CustomRecyclerViewAdapter(realmResults: RealmResults<TsugaruWt>) : RecyclerView.Adapter<ViewHolder>(){

    private val rResult: RealmResults<TsugaruWt> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return  rResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tsugaruWt = rResult[position]

        val sWtMain = String.format("%.02f",tsugaruWt?.sWt)    //// recyclerviewの表示を小数点以下2桁にする
        val mWtMain = String.format("%.02f",tsugaruWt?.mWt)

        holder.sWtText?.text = sWtMain    ////  holder.sWtText?.text = "${tsugaruWt?.sWt}"----org
        holder.mWtText?.text = mWtMain    ////  holder.mWtText?.text = "${tsugaruWt?.mWt}"----org

        val msurDate2 = SpannableString("${tsugaruWt?.msurDate}")    //// recyclerview画面の文字のサイズを一部変更(2行)
        msurDate2.setSpan(RelativeSizeSpan(0.65f),0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.msurDay_of_munth?.text = msurDate2    //// 後程、msurDay_of_munth を適切な名前にする　未実施


        ////  holder.msurDay_of_munth?.text = "${tsugaruWt?.msurDate}"----org
        ////  holder.msurDay_of_munth?.text = "${tsugaruWt?.msur_d_m}"----org

        holder.msurYear?.text = "${tsugaruWt?.msur_y}"

        holder.itemView.setBackgroundColor(if (position % 2 == 0) Color.MAGENTA else Color.WHITE)

        holder.itemView.setOnLongClickListener {
            val intent = Intent(it.context,EditActivity::class.java)
            intent.putExtra("id",tsugaruWt?.id)
            it.context.startActivity(intent)
            return@setOnLongClickListener true
        }

    }
}