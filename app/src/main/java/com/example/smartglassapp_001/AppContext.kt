package com.example.smartglassapp_001

import android.content.Context

// アプリケーションコンテキストクラス
class AppContext {
    // static変数・関数定義
    companion object {
        // インスタンス
        private var instance: AppContext? = null

        // アプリケーションコンテキスト登録
        fun setAppContext(appContext: Context) {
            // コンストラクタ呼び出し
            instance = AppContext(appContext)
        }

        // インスタンス取得
        fun getInstance(): AppContext? {
            return AppContext.instance
        }

        // アプリケーションコンテキスト取得
        fun getAppContext(): Context? {
            return AppContext.getInstance()?.appContext
        }
    }

    // アプリケーションコンテキスト
    private var appContext: Context? = null

    // コンストラクタ
    constructor(context: Context) {
        appContext = context
    }

}