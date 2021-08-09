package com.example.smartglassapp_001

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

/*
* for VUZIX M400
*/

// メインクラス
class MainActivity : AppCompatActivity(), View.OnClickListener {
    // パーミッションリクエストコード
    private val REQUEST_CODE = 1000

    // QRカメラクラス
    var m_qrCamera: QrCamera? = null

    // アクテビティ作成
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // アプリケーションコンテキストクラス初期化
        AppContext.setAppContext(applicationContext)

        // パーミッションリスト作成
        val permissions: Array<String?> = arrayOf(
            Manifest.permission.CAMERA  // カメラ
        )
        // パーミッションのチェック
        checkPermission(permissions, REQUEST_CODE)

        // OnClickListenerの登録を行うと「C」ボタン（ハードキー）の入力を受け付けなくなる為、コメントアウト
//        imageView_01.setOnClickListener(this)
//        imageView_02.setOnClickListener(this)
//        imageView_03.setOnClickListener(this)

        // QRカメラクラス初期化
        m_qrCamera = QrCamera(qr_view_01, textView_01)
        // QRコード読み取り開始
        m_qrCamera?.resumeQrCamera()
    }

    // パーミッションのチェック
    fun checkPermission(permissions: Array<String?>?, requestCode: Int) {
        // 許可されていないものだけダイアログを表示
        ActivityCompat.requestPermissions(this, permissions!!, requestCode)
    }

    // requestPermissionsのコールバック
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // 拒否されたパーミッション数
        var rejectedCount: Int = 0

        if (requestCode != REQUEST_CODE){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        when (requestCode) {
            REQUEST_CODE -> {
                var i = 0
                while (i < permissions.size) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        MessageUtils.toast("Added Permission::" + permissions[i])
                    } else {
                        // 「今後表示しない」のチェックがONにされていたかチェック
                        if (shouldShowRequestPermissionRationale(permissions[i]) == true) {
                            // 「今後表示しない」のチェックOFF
                            MessageUtils.toast("Rejected Permission::" + permissions[i])
                        } else {
                            // 「今後表示しない」のチェックON
                            MessageUtils.toast("Forever Rejected Permission::" + permissions[i])
                        }
                        // 拒否されたパーミッション数をインクリメント
                        rejectedCount ++
                    }
                    i++
                }
            }
        }

        // 拒否されたパーミッション数をチェック
        if (0 < rejectedCount) {
            // アプリ終了
            closeApp()
        }
    }

    // アプリ終了
    fun closeApp() {
        finish()
        moveTaskToBack(true)
    }

    // アクティビティ破棄
    override fun onDestroy() {
        super.onDestroy()

        // QRコード読み取り停止
        m_qrCamera?.pauseQrCamera()
    }

    // アクティビティ再開
    override fun onResume() {
        super.onResume()

        // QRコード読み取り開始
        m_qrCamera?.resumeQrCamera()
    }

    // アクティビティ停止
    override fun onPause() {
        super.onPause()

        // QRコード読み取り停止
        m_qrCamera?.pauseQrCamera()
    }

    // ウィンドウフォーカスの変更通知イベント
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // フルスクリーン表示
        showFullScreen()
    }

    // フルスクリーン表示
    private fun showFullScreen(){
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        window.decorView.setOnSystemUiVisibilityChangeListener {}
    }

    // キーダウンイベント
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val eventStr: String = "onKeyDown::"

        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {    // 前ボタン
                MessageUtils.toast(eventStr + getButtonStr(keyCode))
                imageView_01.setImageResource(R.drawable.button_a_002)
                onClick(imageView_01)
                true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {     // 中央ボタン
                MessageUtils.toast(eventStr + getButtonStr(keyCode))
                imageView_02.setImageResource(R.drawable.button_b_002)
                onClick(imageView_02)
                true
            }
            KeyEvent.KEYCODE_DPAD_CENTER -> {   // 後ボタン
                MessageUtils.toast(eventStr + getButtonStr(keyCode))
                imageView_03.setImageResource(R.drawable.button_c_002)
                onClick(imageView_03)
                true
            }
            KeyEvent.KEYCODE_MENU -> {          // 前ボタン長押し
                true
            }
            KeyEvent.KEYCODE_HOME -> {          // 中央ボタン長押し
                true
            }
            KeyEvent.KEYCODE_BACK -> {          // 後ボタン長押し
                true
            }
            else -> {                           // その他ボタン
//                MessageUtils.toast(eventStr + getButtonStr(keyCode))
                super.onKeyDown(keyCode, event)
            }
        }
    }

    // キーアップイベント
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val eventStr: String = "onKeyUp::"

        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {    // 前ボタン
                imageView_01.setImageResource(R.drawable.button_a_001)
                true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {     // 中央ボタン
                imageView_02.setImageResource(R.drawable.button_b_001)
                true
            }
            KeyEvent.KEYCODE_DPAD_CENTER -> {   // 後ボタン
                imageView_03.setImageResource(R.drawable.button_c_001)
                true
            }
            else -> {                           // その他ボタン
//                MessageUtils.toast(eventStr + getButtonStr(keyCode))
                super.onKeyUp(keyCode, event)
            }
        }
    }

    // キー長押しイベント
    override fun onKeyLongPress(keyCode: Int, event: KeyEvent): Boolean {
        return super.onKeyLongPress(keyCode, event)
    }

    // クリックイベント
    override fun onClick(view: View) {
        when (view) {
            imageView_01 -> {

            }
            imageView_02 -> {

            }
            imageView_03 -> {

            }
            else -> {

            }
        }
    }

    // ボタン文字列取得
    private fun getButtonStr(keyCode: Int): String {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> { "前ボタン" }
            KeyEvent.KEYCODE_DPAD_LEFT -> { "中央ボタン" }
            KeyEvent.KEYCODE_DPAD_CENTER -> { "後ボタン" }
            KeyEvent.KEYCODE_MENU -> { "前ボタン長押し" }
            KeyEvent.KEYCODE_HOME -> { "中央ボタン長押し" }
            KeyEvent.KEYCODE_BACK -> { "後ボタン長押し" }
            else -> { keyCode.toString() }
        }
    }

}