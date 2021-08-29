package com.example.smartglassapp_001

import android.widget.TextView
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

// QRカメラクラス
class QrCamera {
    // バーコードビュー
    private var qrView: CompoundBarcodeView
    // バーコードテキスト
    private var qrText: String = ""
    // テキストビュー
    private var textView_01: TextView? = null

    // コンストラクタ
    constructor(barcodeView:CompoundBarcodeView, textView: TextView) {
        // バーコードビュー初期化
        qrView = barcodeView
        // バーコードビューテキストをクリア
        qrView.statusView.text = ""
        // カレントカメラINDEXを登録
        qrView.barcodeView.cameraSettings.requestedCameraId = 0

        // テキストビュー
        textView_01 = textView
    }

    // QRコード読み取り開始
    public fun resumeQrCamera() {
        // QRコード読み取り開始
        qrView.resume()

        qrView.decodeContinuous(object: BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                var value: Int? = 0
                if (result == null) {
                    return
                }
                // 読み込んだ文字列を数値に変換
                value = result.text.toIntOrNull()

                if ((value != null) && (qrText != result.text)) {
                    // バーコードテキストを更新
                    qrText = result.text
                    // 部品データ文字列取得、テキストビューに表示
                    textView_01?.text = DbAccess.getPartsDataString(value)
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) { }
        })
    }

    // QRコード読み取り停止
    public fun pauseQrCamera() {
        qrView.pause()
    }

}