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
import kotlinx.android.synthetic.main.activity_graph.*

open class GraphActivity : AppCompatActivity() , OnChartValueSelectedListener {

    //Typefaceの設定、後ほど使用します。
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    private val chartDataCount = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        setupLineChart()
        lineChart.data = lineDataWithCount(chartDataCount, 100f)
        
        }

    private fun setupLineChart(){
        lineChart.apply {
            setOnChartValueSelectedListener(this@GraphActivity)
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.BLACK
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            //y軸右側の表示
            axisRight.isEnabled = false

            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(false)
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                setDrawGridLines(true)
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString())
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }

    //    LineChart用のデータ作成
    private fun lineDataWithCount(count: Int, range: Float):LineData {

        val values = mutableListOf<Entry>()

        for (i in 0 until count) {
            val value = (Math.random() * (range)).toFloat()
            values.add(Entry(i.toFloat(), value))
        }
        // create a dataset and give it a type
        val yVals = LineDataSet(values, "SampleLineData").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLACK
            highLightColor = Color.YELLOW
            setDrawCircles(false)
            setDrawCircleHole(false)
            setDrawValues(false)
            lineWidth = 2f
        }
        val data = LineData(yVals)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(9f)
        return data
    }
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

    override fun onDestroy() {    //// 8/21
        super.onDestroy()    //// 8/21
        realm.close()    //// 8/21
    }

     */
}
