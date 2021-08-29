package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// ターゲット製品構成データクラス
class TargetProductStructureData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 製品ID
    var productId: Int = -1
        private set
    // データリスト
    var dataList: MutableList<getMultiProductStructureData>? = null
        private set

    // getMulti : URL文字列配列
    private val getMultiUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.m_product_structure,
        DefineData.getMultiUrl
    )

    // コンストラクタ
    constructor(product_id: Int) {
        var msg: String = ""
        var recvDataList: List<getMultiProductStructureData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0
        var productStructure: getMultiProductStructureData? = null

        try {
            // 製品構成データリスト取得
            recvDataList = getMultiProductStructureDataList(product_id)
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

            // 製品ID
            productId = product_id

            for (recvData in recvDataList) {
                // 製品データクラス作成
                productStructure = getMultiProductStructureData(
                    recvData.parts_id,
                    recvData.parts_count
                )
                if (dataList == null) {
                    dataList = mutableListOf()
                }
                // データリストに追加
                dataList?.add(dataIndex, productStructure)
                dataIndex ++
            }

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("TargetProductStructureData::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 製品構成データリスト取得
    private fun getMultiProductStructureDataList(product_id: Int): List<getMultiProductStructureData>? {
        var ret: List<getMultiProductStructureData>? = null
        var msg: String = ""
        var url: String = ""
        var responseStr: String = ""

        try {
            // URL
            url = getMultiUrlArray.joinToString(separator = "")
            if (url.length < 1) {
                msg = "url = " + url
                throw Exception()
            }
            // パラメータを付加
            url += "?"
            url += ("product_id=" + product_id)

            // GETリクエスト
            responseStr = HttpUtils.httpGetRequest(url)
            if (responseStr.length < 1) {
                msg = "responseStr = " + responseStr
                throw Exception()
            }

            // JSON文字列パース
            ret = Json.decodeFromString<List<getMultiProductStructureData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("TargetProductStructureData::getMultiProductStructureDataList -> " + msg)
            ret = null
        }

        return ret
    }

}