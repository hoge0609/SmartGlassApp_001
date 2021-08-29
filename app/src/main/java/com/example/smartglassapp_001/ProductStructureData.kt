package com.example.smartglassapp_001

import kotlinx.serialization.*

// getAll : 製品構成マスタ
@Serializable
data class getAllProductStructureData(
    val product_id: Int,
    val parts_id: Int,
    val parts_count: Int
)

// getMulti : 製品構成マスタ
@Serializable
data class getMultiProductStructureData(
    val parts_id: Int,
    val parts_count: Int
)

// 製品構成データクラス
class ProductStructureData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 製品ID
    var productId: Int = -1
        private set
    // 部品ID
    var partsId: Int = -1
        private set
    // 部品数
    var partsCount: Int = 0
        private set

    // コンストラクタ
    constructor(product_id: Int, parts_id: Int, parts_count: Int) {
        var msg: String = ""

        try {
            // 製品ID
            if (product_id < 0) {
                msg = "productId = " + product_id
                throw Exception()
            }
            productId = product_id

            // 部品ID
            if (parts_id < 0) {
                msg = "partsId = " + parts_id
                throw Exception()
            }
            partsId = parts_id

            // 部品数
            if (parts_count < 1) {
                msg = "partsCount = " + parts_count
                throw Exception()
            }
            partsCount = parts_count

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("ProductStructureData::constructor -> " + msg)
            checkFlag = false
        }
    }

}