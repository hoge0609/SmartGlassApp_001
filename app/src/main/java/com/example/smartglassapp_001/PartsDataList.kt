package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// 部品データクラスリスト
class PartsDataList {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // データリスト
    var dataList: MutableList<PartsData>? = null
        private set

    // getAll : URL文字列配列
    private val getAllUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.m_parts,
        DefineData.getAllUrl
    )

    // コンストラクタ
    constructor() {
        var msg: String = ""
        var recvDataList: List<getAllPartsData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0
        var parts: PartsData? = null

        try {
            // 部品データリスト取得
            recvDataList = getAllPartsDataList()
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
                // 部品データクラス作成
                parts = PartsData(
                    recvData.parts_id,
                    recvData.parts_name
                )
                if (parts?.checkFlag == false) {
                    msg = ("recvData[" + dataIndex + "] : check error")
                    throw Exception()
                }
                if (dataList == null) {
                    dataList = mutableListOf()
                }
                // データリストに追加
                dataList?.add(dataIndex, parts)
                dataIndex ++
            }

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsDataList::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 部品データリスト取得
    private fun getAllPartsDataList(): List<getAllPartsData>? {
        var ret: List<getAllPartsData>? = null
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
            ret = Json.decodeFromString<List<getAllPartsData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsDataList::getAllPartsDataList -> " + msg)
            ret = null
        }

        return ret
    }

}