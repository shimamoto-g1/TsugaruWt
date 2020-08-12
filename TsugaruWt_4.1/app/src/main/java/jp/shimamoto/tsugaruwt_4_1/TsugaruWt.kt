package jp.shimamoto.tsugaruwt_4_1

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TsugaruWt : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var msurDate: String = ""
    var msur_y: String = ""
    var msur_d_m: String = ""
    var tWt: Double = 0.0
    var mWt: Double = 0.0
    var sWt: Double = 0.0
}