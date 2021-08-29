package com.example.smartglassapp_001

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// 製品データクラスリスト
class ProductDataList {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // データリスト
    var dataList: MutableList<ProductData>? = null
        private set

    // getAll : URL文字列配列
    private val getAllUrlArray: Array<String> = arrayOf(
        DefineData.baseUrl,
        DefineData.m_product,
        DefineData.getAllUrl
    )

    // コンストラクタ
    constructor() {
        var msg: String = ""
        var recvDataList: List<getAllProductData>? = null
        var dataListCount: Int = 0
        var dataIndex: Int = 0
        var product: ProductData? = null

        try {
            // 製品データリスト取得
            recvDataList = getAllProductDataList()
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
                product = ProductData(
                    recvData.product_id,
                    recvData.product_name
                )
                if (product?.checkFlag == false) {
                    msg = ("recvData[" + dataIndex + "] : check error")
                    throw Exception()
                }
                if (dataList == null) {
                    dataList = mutableListOf()
                }
                // データリストに追加
                dataList?.add(dataIndex, product)
                dataIndex ++
            }

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("ProductDataList::constructor -> " + msg)
            checkFlag = false
        }
    }

    // 製品データリスト取得
    private fun getAllProductDataList(): List<getAllProductData>? {
        var ret: List<getAllProductData>? = null
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
            ret = Json.decodeFromString<List<getAllProductData>>(responseStr)
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("ProductDataList::getAllProductDataList -> " + msg)
            ret = null
        }

        return ret
    }

    // 製品名取得
    fun getProductName(product_id: Int) : String {
        var ret: String = ""

        if (dataList != null) {
            for (data in dataList!!) {
                // 製品IDの一致チェック
                if (data.productId == product_id) {
                    // 製品名を登録
                    ret = data.productName
                    break
                }
            }
        }

        return ret
    }

}