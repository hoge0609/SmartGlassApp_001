package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// 部品在庫データクラスリスト
class PartsInventoryDataList {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // データリスト
    var dataList: MutableList<PartsInventoryData>? = null
        private set

    // getAll : URL文字列配列
    private val getAllUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.t_parts_inventory,
        DefineData.getAllUrl
    )

    // コンストラクタ
    constructor() {
        var msg: String = ""
        var recvDataList: List<getAllPartsInventoryData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0
        var partsInventory: PartsInventoryData? = null

        try {
            // 製品構成データリスト取得
            recvDataList = getAllPartsInventoryDataList()
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

            for (recvData in recvDataList) {
                // 部品在庫データクラス作成
                partsInventory = PartsInventoryData(
                    recvData.parts_id,
                    recvData.parts_inventory_count
                )
                if (partsInventory?.checkFlag == false) {
                    msg = ("recvData[" + dataIndex + "] : check error")
                    throw Exception()
                }
                if (dataList == null) {
                    dataList = mutableListOf()
                }
                // データリストに追加
                dataList?.add(dataIndex, partsInventory)
                dataIndex ++
            }

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsInventoryDataList::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 製品構成データリスト取得
    private fun getAllPartsInventoryDataList(): List<getAllPartsInventoryData>? {
        var ret: List<getAllPartsInventoryData>? = null
        var msg: String = ""
        var url: String = ""
        var responseStr: String = ""

        try {
            // URL
            url = getAllUrlArray.joinToString(separator = "")
            if (url.length < 1) {
                msg = "url = " + url
                throw Exception()
            }

            // GETリクエスト
            responseStr = HttpUtils.httpGetRequest(url)
            if (responseStr.length < 1) {
                msg = "responseStr = " + responseStr
                throw Exception()
            }

            // JSON文字列パース
            ret = Json.decodeFromString<List<getAllPartsInventoryData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsInventoryDataList::getAllPartsInventoryDataList -> " + msg)
            ret = null
        }

        return ret
    }

}