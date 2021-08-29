package com.example.smartglassapp_001

import kotlinx.serialization.*

// getAll : 部品マスタ
@Serializable
data class getAllPartsData(
    val parts_id: Int,
    val parts_name: String
)

// 部品データクラス
class PartsData {
    // チェックフラグ
    var checkFlag: Boolean = false
        private set
    // 部品ID
    var partsId: Int = -1
        private set
    // 部品名
    var partsName: String = ""
        private set

    // コンストラクタ
    constructor(parts_id: Int, parts_name: String) {
        var msg: String = ""

        try {
            // 部品ID
            if (parts_id < 0) {
                msg = "partsId = " + parts_id
                throw Exception()
            }
            partsId = parts_id

            // 部品名
            if (parts_name.length < 1) {
                msg = "productName = " + parts_name
                throw Exception()
            }
            partsName = parts_name

            checkFlag = true
        } catch (e: Exception) {
            if (msg.length < 1) {
                msg = e.message.toString()
            }
            MessageUtils.toast("PartsData::constructor -> " + msg)
            checkFlag = false
        }
    }

}