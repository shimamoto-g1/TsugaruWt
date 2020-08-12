package jp.shimamoto.tsugaruwt_4_1

/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm

 */

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity    ////     エラーの時にあったもの　！
import android.os.Bundle    ////     エラーの時にあったもの　！
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import io.realm.Realm    ////     エラーの時にあったもの　！
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity(),AlertDialogFragment.NoticeDialogListener {
    private val tag = "TsugaruWt"
    private lateinit var realm: Realm

    // val twid = intent.getLongExtra("id",0L)    //// onCreateから移動

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realm = Realm.getDefaultInstance()

        var id: Long = 0
        var msurDate: String = ""
        var msur_y: String = ""
        var msur_d_m: String = ""
        // var msurDay: String = ""
        var tWt: Double = 0.0
        var mWt: Double = 0.0
        var sWt: Double = 0.0

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // 新規登録か変更登録かを判断
        val twid = intent.getLongExtra("id",0L)    //// class直下に移動　↑30行
        if (twid > 0L) {
            val  tsugaruWt = realm.where<TsugaruWt>()
                .equalTo("id",twid)
                .findFirst()
            editMsurDate.setText(tsugaruWt?.msurDate.toString())
            editTtlWt.setText(tsugaruWt?.tWt.toString())
            editSubWt.setText(tsugaruWt?.sWt.toString())
            calcMainWt.setText(tsugaruWt?.mWt.toString())

            deleteBtn.visibility = View.VISIBLE
        } else {
            deleteBtn.visibility = View.INVISIBLE
        }

        editMsurDate.setOnClickListener {
            val dtp = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, y, m, d ->

                msur_y = String.format("%02d",y)
                msur_d_m = String.format("%02d/%02d",m+1,d)

                //////////////
                val msurYMD = String.format("%02d/%02d/%02d",y,m+1,d)   ////// var -> val に変更した。確認未
                val spannable = SpannableStringBuilder(msurYMD)
                spannable.setSpan(RelativeSizeSpan(0.75f),0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                editMsurDate.setText(spannable)
                //////////////

                ////    val msurYDM = "${msur_y}/${msur_d_m}"
                ////    editMsurDate.setText(msurYDM)

            }, year,month,day
            )

            dtp.show()
        }

        //// このコードの場所を適当に変えると  calcBtn.setOnClickListener 内のnoteMsgがエラーになる　！
        fun noteMsg() {
            Toast.makeText(applicationContext,"トータルとサブの重さを入力して下さい！",Toast.LENGTH_LONG).show()
        }

        calcBtn.setOnClickListener {

            if (!editTtlWt.text.isNullOrEmpty()) {
                tWt = editTtlWt.text.toString().toDouble()

                if (!editSubWt.text.isNullOrEmpty()) {
                    sWt = editSubWt.text.toString().toDouble()
                    mWt = tWt - sWt                                 //// main wt の計算
                    Toast.makeText(applicationContext,"計算成功！",Toast.LENGTH_SHORT).show()     //// 途中確認
                    calcMainWt.text = String.format("%.02f",mWt)

                } else {
                    noteMsg()
                }

            } else {
                noteMsg()
            }
        }

        saveBtn.setOnClickListener {
            if (!editMsurDate.text.isNullOrEmpty()) {
                msurDate = editMsurDate.text.toString()
            }

            if (!editTtlWt.text.isNullOrEmpty()) {
                tWt = editTtlWt.text.toString().toDouble()
            }

            if (!editSubWt.text.isNullOrEmpty()) {
                sWt = editSubWt.text.toString().toDouble()
            }

            if (!calcMainWt.text.isNullOrEmpty()) {
                mWt = calcMainWt.text.toString().toDouble()
            }

            when (twid) {
                0L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<TsugaruWt>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1L
                        val tsugaruWt = realm.createObject<TsugaruWt>(nextId)
                        tsugaruWt.msurDate = msurDate
                        tsugaruWt.msur_y = msur_y
                        tsugaruWt.msur_d_m = msur_d_m
                        tsugaruWt.tWt = tWt
                        tsugaruWt.sWt = sWt
                        tsugaruWt.mWt = mWt
                    }
                }
                else -> {
                    realm.executeTransaction {
                        val tsugaruWt = realm.where<TsugaruWt>()
                            .equalTo("id",twid)
                            .findFirst()
                        tsugaruWt?.msurDate = msurDate
                        tsugaruWt?.msur_y = msur_y
                        tsugaruWt?.msur_d_m = msur_d_m
                        tsugaruWt?.tWt = tWt
                        tsugaruWt?.sWt = sWt
                        tsugaruWt?.mWt = mWt
                    }
                }
            }

            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        deleteBtn.setOnClickListener {
            showNoticeDialog()
        }

    }

    private fun showNoticeDialog() {
        val dialog = AlertDialogFragment()
        dialog.show(supportFragmentManager, "AlertDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {

        val trId = intent.getLongExtra("id",0L)

        realm.executeTransaction {
            val tsugaruWt = realm.where<TsugaruWt>()
                .equalTo("id",trId)
                ?.findFirst()
                ?.deleteFromRealm()
        }

        Toast.makeText(applicationContext,"削除しました",Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}