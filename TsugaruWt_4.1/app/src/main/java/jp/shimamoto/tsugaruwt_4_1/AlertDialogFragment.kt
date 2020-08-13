package jp.shimamoto.tsugaruwt_4_1

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AlertDialogFragment() : DialogFragment() {
    ////　TaskRecord２で追加したクラス、コード内容の確認要が多数あり　２？

    // 呼び出し元のActivityを保持する
    private lateinit var listener: NoticeDialogListener

    // コールバック用インタフェース。呼び出し元で実装する　２
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            // 呼び出し元のActivityを変数listenerで保持する　２
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // 呼び出し元のActivityでコールバックインタフェースを実装していない場合　２
            throw  ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // ダイアログの生成
            val builder = android.app.AlertDialog.Builder(it)
            builder.setMessage("削除してもよろしいですか？")
                .setPositiveButton("ＯＫ！") { dialog, id ->
                    // 呼び出し元のActivityで定義されているonDialogPositiveClickが実行される　２
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton("削除しない") { dialog, id ->
                    // 呼び出し元のActivityで定義されているonDialogNegativeClickが実行される　２
                    listener.onDialogNegativeClick(this)
                }
                .setIcon(R.mipmap.ic_launcher_foreground)
                .setTitle("確認！")

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}
