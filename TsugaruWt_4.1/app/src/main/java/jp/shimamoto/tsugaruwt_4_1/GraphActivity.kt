package jp.shimamoto.tsugaruwt_4_1

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_graph.*
import java.text.SimpleDateFormat
import java.util.*

//// 8/25 グラフ表示される事を確認して、これからrealmResultsにトライ！！！

open class GraphActivity : AppCompatActivity() , OnChartValueSelectedListener {

    private lateinit var realm: Realm ////8/25 ok

    //Typefaceの設定、後ほど使用します。スタイルとフォントファミリーの設定
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    //// データの個数
    private val chartDataCount = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        //// グラフの設定
        setupLineChart()
        //// データの設定
        lineChart.data = lineDataWithCount()
        lineChart2.data = lineDataWithCount2()
    }

    private fun setupLineChart() {
        lineChart.apply {
            setOnChartValueSelectedListener(this@GraphActivity)
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            //// 拡大縮小可否
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)


            //// データラベルの表示 ここでは「ツガルの重さ」
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 20f
                textColor = Color.RED
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            // y軸右側の表示
            axisRight.isEnabled = true


            // X軸表示
            xAxis.apply {
                typeface = mTypeface
                //// X軸の数字を表示
                setDrawLabels(false)
                //// 格子線を表示する
                setDrawGridLines(true)
            }

            // y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                //// 格子線を表示する
                setDrawGridLines(true)
            }

        }

        //////////////  ここから　グラフその２の為に　その２  ////////////////
            lineChart2.apply {
                setOnChartValueSelectedListener(this@GraphActivity)
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                //// 拡大縮小可否
                isScaleXEnabled = true
                setPinchZoom(false)
                setDrawGridBackground(false)


                //// データラベルの表示 ここでは「ツガルの重さ」
                legend.apply {
                    form = Legend.LegendForm.LINE
                    typeface = mTypeface
                    textSize = 20f
                    textColor = Color.BLUE    
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                    orientation = Legend.LegendOrientation.HORIZONTAL
                    setDrawInside(false)
                }

                // y軸右側の表示
                axisRight.isEnabled = true


                // X軸表示
                xAxis.apply {
                    typeface = mTypeface
                    //// X軸の数字を表示
                    setDrawLabels(false)
                    //// 格子線を表示する
                    setDrawGridLines(true)
                }

                // y軸左側の表示
                axisLeft.apply {
                    typeface = mTypeface
                    textColor = Color.BLACK
                    //// 格子線を表示する
                    setDrawGridLines(true)
                }

            }
        //////////////  ここまで　グラフその２の為に　その２  ////////////////


    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString())
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }

    //    LineChart用のデータ作成
    private fun lineDataWithCount(): LineData {

        realm = Realm.getDefaultInstance()

        //// 今日の日付を取得してyyyy/MM/ddにする
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("yyyy/MM/dd")

        val pdate = df.format(cal.time)    //// 今日の日付
        pdateDisp.text = pdate.toString()    //// pdateの確認の為表示

        val values = mutableListOf<Entry>()

        cal.add(Calendar.DATE, -30)    //// グラフ反転にtry 8/26

        for (i in 0..30) {
            val x = df.format(cal.time)

            val rResults: RealmResults<TsugaruWt> = realm.where(TsugaruWt::class.java)
                .equalTo("msurDate", x)
                .findAll()

            cal.add(Calendar.DATE, +1)    //// グラフ反転にtry 8/26

            if (!rResults.isNullOrEmpty()) {
                val v = rResults[0]!!.mWt.toFloat()     //// 8/25 rResults[1]の後ろの ”?.” を ”!!” に変更したらグラフに反映できる
                values.add(Entry(i.toFloat(), v))

            } else {
                val v = 3.0f
                values.add(Entry(i.toFloat(), v))
            }
        }

        //// グラフのレイアウトの設定 create a dataset and give it a type
        val yVals = LineDataSet(values, "ツガルの重さ_30日間").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = Color.RED
            //// タップ時のハイライトカラー
            highLightColor = Color.BLUE
            //// グラフの値に点を表示
            setDrawCircles(true)
            setDrawCircleHole(true)
            //// 点の値非表示
            setDrawValues(true)
            //// 線の太さ
            lineWidth = 2f
        }
        val data = LineData(yVals)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(9f)
        return data
    }

    //////////////////////  ここから　グラフその２  /////////////////////////

    //    LineChart用のデータ作成
    private fun lineDataWithCount2(): LineData {

        realm = Realm.getDefaultInstance()

        //// 今日の日付を取得してyyyy/MM/ddにする
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("yyyy/MM/dd")

        val pdate = df.format(cal.time)    //// 今日の日付
        pdateDisp.text = pdate.toString()    //// pdateの確認の為表示

        val values = mutableListOf<Entry>()

        cal.add(Calendar.DATE, -30)    //// グラフ反転にtry 8/26

        for (i in 0..30) {
            val a = df.format(cal.time)

            val rResults: RealmResults<TsugaruWt> = realm.where(TsugaruWt::class.java)
                .equalTo("msurDate", a)
                .findAll()

            cal.add(Calendar.DATE, +1)    //// グラフ反転にtry 8/26

            if (!rResults.isNullOrEmpty()) {
                val b = rResults[0]!!.sWt.toFloat()     //// 8/25 rResults[1]の後ろの ”?.” を ”!!” に変更したらグラフに反映できる
                values.add(Entry(i.toFloat(), b))

            } else {
                val b = 84.0f
                values.add(Entry(i.toFloat(), b))
            }
        }

        //// グラフのレイアウトの設定 create a dataset and give it a type
        val yVals2 = LineDataSet(values, "測った人の重さ_30日間").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = Color.BLUE
            //// タップ時のハイライトカラー
            highLightColor = Color.BLUE
            //// グラフの値に点を表示
            setDrawCircles(true)
            setDrawCircleHole(true)
            //// 点の値非表示
            setDrawValues(true)
            //// 線の太さ
            lineWidth = 2f
        }
        val data = LineData(yVals2)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(9f)
        return data
    }

    //////////////////////  ここまで　グラフその２  /////////////////////////


    override fun onDestroy() {    //// 8/21
        super.onDestroy()    //// 8/21
        realm.close()    //// 8/21
    }
}


/*
    //// X軸表示に日付を使えるようになったらの為に置いておく    8/28  ////
        var labels = arrayOf("", "1日", "2日", "3日", "4日"
            , "5日", "6日", "7日", "8日", "9日", "10日"
            , "11日", "12日", "13日", "14日", "15日", "16日"
            , "17日", "18日", "19日", "20日", "21日", "22日"
            , "23日", "24日", "25日", "26日", "27日", "28日"
            , "29日", "30日", "31日")

 */


    /*

    private lateinit var realm: Realm   //// 8/21

    // スタイルとフォントファミリーの設定
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    // データの個数
    private val chartDataCount = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // グラフの設定
        setupLineChart()
        // データの設定
        lineChart.data = lineData(chartDataCount, 100f)
    }

    // LineChart用のデータ作成
    private fun lineData(count: Int, range: Float):LineData {

        realm = Realm.getDefaultInstance()    //// 8/21

        val values = mutableListOf<Entry>()
/*
        for (i in 0 until count) {
            // 値はランダムで表示させる
            val value = (Math.random() * (range)).toFloat()
            values.add(Entry(i.toFloat(), value))
        }
*/

/*
        //// データ仮入れ  ////
        values.add(Entry(0.toFloat(), 55.25f))
        values.add(Entry(1.toFloat(), 70f))
        values.add(Entry(2.toFloat(), 60f))    ////
        values.add(Entry(3.toFloat(), 79f))
        values.add(Entry(4.toFloat(), 63f))

*/

        //// 体重データでグラフ作りに挑戦　8/23 //// ！！

        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("yyyy/MM/dd")

        val pdate = df.format(cal.time)    //// 今日の日付
        pdateDisp.text = pdate.toString()    //// pdateの確認の為表示

         val i = 8    //// for (i in 0..5) {
             cal.add(Calendar.DATE, -i)
             val keisanDate = df.format(cal.time)




        val dDate = realm.where(TsugaruWt::class.java)
                 .equalTo("msurDate", keisanDate)    //// 今日の日付でrealm内を検索  ==>pdateが文字列のままでは-1が出来ない
                 .findFirst()


             val ddate = dDate?.msurDate.toString()
             val ddateWt = dDate?.mWt.toString().toFloat()
             ddateDisp.text = "${ddate},,,${ddateWt}"    //// dDateの確認の為表示

/*
             if (pdate == ddate) {
                 values.add(Entry(0.toFloat(), ddateWt))
             } else {
                 values.add(Entry(0.toFloat(), 2.8f))
             }

 */
        //// データ仮入れ  ////
        values.add(Entry(0.toFloat(), 55.25f))
        values.add(Entry(1.toFloat(), 70f))
        values.add(Entry(2.toFloat(), 60f))    ////
        values.add(Entry(3.toFloat(), 79f))
        values.add(Entry(4.toFloat(), 63f))

        //// }
/*

        //// 体重データの取り込みに挑戦　8/22 //// ！！

        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("yyyy/MM/dd")

        val pdate = df.format(cal.time).toString()    //// 今日の日付
        pdateDisp.text = pdate    //// pdateの確認の為表示

        val dDate = realm.where(TsugaruWt::class.java)
            .equalTo("msurDate",pdate)    //// 今日の日付でrealm内を検索
            .findFirst()

        val ddate = dDate?.msurDate.toString()
        val ddateWt = dDate?.mWt.toString().toFloat()
        ddateDisp.text = "${ddate},,,${ddateWt}"    //// dDateの確認の為表示


        if (pdate == ddate) {
            values.add(Entry(0.toFloat(),ddateWt))
        } else {
            values.add(Entry(0.toFloat(),2.8f))
        }


 */

        // グラフのレイアウトの設定
        val yVals = LineDataSet(values, "テストデータ").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLUE    //// グラフの線の色
            // タップ時のハイライトカラー
            highLightColor = Color.YELLOW
            setDrawCircles(true)
            setDrawCircleHole(true)
            // 点の値非表示
            setDrawValues(true)
            // 線の太さ
            lineWidth = 2f
        }
        val data = LineData(yVals)
        return data
    }

    private fun setupLineChart() {
        lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            // 拡大縮小可能
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.RED    //// 左下の文字の色
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            //y軸右側の設定
            axisRight.isEnabled = false

            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(false)
                // 格子線を表示する
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.MAGENTA    //// 縦軸の文字の色
                // 格子線を表示する
                setDrawGridLines(true)
            }
        }
    }
}

     */
