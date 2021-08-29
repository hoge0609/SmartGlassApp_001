package com.example.smartglassapp_001

import kotlinx.serialization.*

// getAll : 部品在庫テーブル
@Serializable
data class getAllPartsInventoryData(
    val parts_id: Int,
    val parts_inventory_count: Int
)

// getSingle : 部品在庫テーブル
@Serializable
data class getSinglePartsInventoryData(
    val parts_inventory_count: Int
)

// 部品在庫データクラス
class PartsInventoryData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 部品ID
    var partsId: Int = -1
        private set
    // 部品数
    var partsInventoryCount: Int = -1
        private set

    // コンストラクタ
    constructor(parts_id: Int, parts_inventory_count: Int) {
        var msg: String = ""

        try {
            // 部品ID
            if (parts_id < 0) {
                msg = "partsId = " + parts_id
                throw Exception()
            }
            partsId = parts_id

            // 部品数
            if (parts_inventory_count < 0) {
                msg = "partsInventoryCount = " + parts_inventory_count
                throw Exception()
            }
            partsInventoryCount = parts_inventory_count

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsInventoryData::constructor -> " + msg)
            checkFlag = false
        }
    }

}