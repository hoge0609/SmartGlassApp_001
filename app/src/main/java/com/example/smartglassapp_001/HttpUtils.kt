package com.example.smartglassapp_001

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

// HTTP通信ユーティリティクラス
class HttpUtils {
    // static関数定義
    companion object {
        // GETリクエスト
        fun httpGetRequest(url: String): String {
            var ret: String = ""
            var msg: String = ""
            var httpRequest: Request? = null

            try {
                // HTTP通信（非同期処理）
                httpRequest = url.httpGet().responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            // レスポンス文字列
                            ret = result.value
                        }
                        is Result.Failure -> {
                            msg = "Failure"
                            throw Exception()
                        }
                    }
                }
                // 非同期処理終了まで待機
                httpRequest?.join()
            } catch (e: Exception) {
                if (msg.length < 1) {
                    msg = e.message.toString()
                }
                MessageUtils.toast("HttpUtils::httpGetRequest -> " + msg)
                ret = ""
            } finally {
                httpRequest = null
            }

            return ret
        }

        // POSTリクエスト
        // ※未完成
        fun httpPostRequest(url: String, jsonStr: String): Boolean {
            var ret: Boolean = false
            var msg: String = ""
            var httpRequest: Request? = null

            try {
                // HTTP通信（非同期処理）
                httpRequest = url.httpPost(null).response { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            // リクエスト結果
                            ret = true
                        }
                        is Result.Failure -> {
                            msg = "Failure"
                            throw Exception()
                        }
                    }
                }
                // 非同期処理終了まで待機
                httpRequest?.join()
            } catch (e: Exception) {
                if (msg.length < 1) {
                    msg = e.message.toString()
                }
                MessageUtils.toast("HttpUtils::httpPostRequest -> " + msg)
                ret = false
            } finally {
                httpRequest = null
            }

            return ret
        }
    }

}