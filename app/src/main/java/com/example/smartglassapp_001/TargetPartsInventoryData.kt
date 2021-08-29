package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// ターゲット部品在庫データクラス
class TargetPartsInventoryData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 部品ID
    var partsId: Int = -1
        private set
    // 部品数
    var partsInventoryCount: Int = -1
        private set

    // getSingle : URL文字列配列
    private val getSingleUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.t_parts_inventory,
        DefineData.getSingleUrl
    )

    // コンストラクタ
    constructor(parts_id: Int) {
        var msg: String = ""
        var recvDataList: List<getSinglePartsInventoryData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0

        try {
            // 部品在庫データ取得
            recvDataList = getSinglePartsInventoryData(parts_id)
            if (recvDataList == null) {
                msg = "recvDataList = null"
                throw Exception()
            }
            dataListCount = recvDataList.count()
            // 入力データのサイズチェック
            if (dataListCount < 1) {
                msg = "dataListCount = " + dataListCount
                throw Exception()
            }

            // 部品ID
            partsId = parts_id
            // 部品数
            partsInventoryCount = recvDataList.get(0).parts_inventory_count

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("TargetPartsInventoryData::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 部品在庫データ取得
    private fun getSinglePartsInventoryData(parts_id: Int): List<getSinglePartsInventoryData>? {
        var ret: List<getSinglePartsInventoryData>? = null
        var msg: String = ""
        var url: String = ""
        var responseStr: String = ""

        try {
            // URL
            url = getSingleUrlArray.joinToString(separator = "")
            if (url.length < 1) {
                msg = "url = " + url
                throw Exception()
            }
            // パラメータを付加
            url += "?"
            url += ("parts_id=" + parts_id)

            // GETリクエスト
            responseStr = HttpUtils.httpGetRequest(url)
            if (responseStr.length < 1) {
                msg = "responseStr = " + responseStr
                throw Exception()
            }

            // JSON文字列パース
            ret = Json.decodeFromString<List<getSinglePartsInventoryData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("TargetPartsInventoryData::getSinglePartsInventoryData -> " + msg)
            ret = null
        }

        return ret
    }

}