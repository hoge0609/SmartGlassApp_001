package com.example.smartglassapp_001

import kotlinx.serialization.*

// getAll : 製品マスタ
@Serializable
data class getAllProductData(
    val product_id: Int,
    val product_name: String
)

// 製品データクラス
class ProductData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 製品ID
    var productId: Int = -1
        private set
    // 製品名
    var productName: String = ""
        private set

    // コンストラクタ
    constructor(product_id: Int, product_name: String) {
        var msg: String = ""

        try {
            // 製品ID
            if (product_id < 0) {
                msg = "productId = " + product_id
                throw Exception()
            }
            productId = product_id

            // 製品名
            if (product_name.length < 1) {
                msg = "productName = " + product_name
                throw Exception()
            }
            productName = product_name

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("ProductData::constructor -> " + msg)
            checkFlag = false
        }
    }

}