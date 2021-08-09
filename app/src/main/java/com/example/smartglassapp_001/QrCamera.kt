package com.example.smartglassapp_001

import android.widget.TextView
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

// QRカメラクラス
class QrCamera {
    // バーコードビュー
    private var m_qrView: CompoundBarcodeView
    // バーコードテキスト
    private var m_qrText: String = ""
    // テキストビュー
    private var m_textView_01: TextView? = null

    // コンストラクタ
    constructor(qrView:CompoundBarcodeView, textView: TextView) {
        // バーコードビュー初期化
        m_qrView = qrView
        // バーコードビューテキストをクリア
        m_qrView.statusView.text = ""
        // カレントカメラINDEXを登録
        m_qrView.barcodeView.cameraSettings.requestedCameraId = 0

        // テキストビュー
        m_textView_01 = textView
    }

    // QRコード読み取り開始
    public fun resumeQrCamera() {
        // QRコード読み取り開始
        m_qrView.resume()

        m_qrView.decodeContinuous(object: BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if (result == null) {
                    return
                }
                if (m_qrText != result.text) {
                    // バーコードテキストを更新
                    m_qrText = result.text
                    // テキストビューに表示
                    m_textView_01?.text = m_qrText
                    // TextView_08テキストの登録
                    MessageUtils.toast(m_qrText)
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) { }
        })
    }

    // QRコード読み取り停止
    public fun pauseQrCamera() {
        m_qrView.pause()
    }

}