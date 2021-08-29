package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// 製品構成データクラスリスト
class ProductStructureDataList {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // データリスト
    var dataList: MutableList<ProductStructureData>? = null
        private set

    // getAll : URL文字列配列
    private val getAllUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.m_product_structure,
        DefineData.getAllUrl
    )

    // コンストラクタ
    constructor() {
        var msg: String = ""
        var recvDataList: List<getAllProductStructureData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0
        var productStructure: ProductStructureData? = null

        try {
            // 製品構成データリスト取得
            recvDataList = getAllProductStructureDataList()
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
                // 製品データクラス作成
                productStructure = ProductStructureData(
                    recvData.product_id,
                    recvData.parts_id,
                    recvData.parts_count
                )
                if (productStructure?.checkFlag == false) {
                    msg = ("recvData[" + dataIndex + "] : check error")
                    throw Exception()
                }
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
            MessageUtils.toast("ProductStructureDataList::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 製品構成データリスト取得
    private fun getAllProductStructureDataList(): List<getAllProductStructureData>? {
        var ret: List<getAllProductStructureData>? = null
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
            ret = Json.decodeFromString<List<getAllProductStructureData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("ProductStructureDataList::getAllProductStructureDataList -> " + msg)
            ret = null
        }

        return ret
    }

}