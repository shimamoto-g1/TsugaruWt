package jp.shimamoto.tsugaruwt_4_1

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_graph.*

open class GraphActivity : AppCompatActivity() {

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
        values.add(Entry(0.toFloat(), 55.25f))
        values.add(Entry(1.toFloat(), 70f))
        values.add(Entry(2.toFloat(), 60f))    ////
        values.add(Entry(3.toFloat(), 79f))
        values.add(Entry(4.toFloat(), 63f))

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
}
