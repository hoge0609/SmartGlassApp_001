package com.example.smartglassapp_001

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/*
@Serializable
data class Product(                 // 製品マスタ
    val product_id: Int,
    val product_name: String
)
*/

/*
@Serializable
data class Parts(                   // 部品マスタ
    val parts_id: Int,
    val parts_name: String
)

@Serializable
data class ProductStructure(        // 製品構成マスタ
    val product_id: Int,
    val parts_id: Int,
    val parts_count: Int
)
*/

@Serializable
data class User(                    // ユーザーマスタ
    val user_id: Int,
    val user_name: String
)

@Serializable
data class Device(                  // デバイスマスタ
    val device_id: Int,
    val device_name: String,
    val user_id: Int,
    val mac_address: String
)

@Serializable
data class InventoryRegistType(     // 在庫登録種別マスタ
    val inventory_regist_type_id: Int,
    val inventory_regist_type_name: String
)

/*
@Serializable
data class PartsInventory(          // 部品在庫テーブル
    val parts_id: Int,
    val parts_inventory_count: Int
)
*/

@Serializable
data class PartsInventoryHistory(   // 部品在庫履歴テーブル
    val parts_inventory_history_id: Int,
    val parts_id: Int,
    val product_id: Int?,   // データベース上でNULLを許可
    val device_id: Int?,    // データベース上でNULLを許可
    val inventory_regist_type_id: Int,
    val variable_count: Int,
    val created_by: Int,
    val create_at: String
)

// データベース接続クラス
class DatabaseConnect {
    val m_scope = CoroutineScope(Dispatchers.Default)

    // テーブルデータINDEX
    var m_tableDataIndex: Int = 0

    // テーブル名リスト
    var m_tableNameList: Array<String?> = arrayOf(
        "m_product",
        "m_parts",
        "m_product_structure",
        "m_user",
        "m_device",
        "m_inventory_regist_type",
        "t_parts_inventory",
        "t_parts_inventory_history"
    )

    // ベースURL
    val m_baseUrl: String = "http://hoge0609.php.xdomain.jp/index.php/"
    // 末尾URL
    val m_endUrl: String = "/get_all/"

    // コンストラクタ
    constructor() {
        Log.d("DatabaseConnect", "constructor")
    }

/*
    // データベース接続
    public suspend fun connect() {
        var msg: String = "データベース接続エラー"
        var outputStr: String = ""
        var connection: HttpURLConnection? = null
        var dataStr: String
        // 接続テーブル名
        var targetTable: String = m_tableNameList[m_tableDataIndex].toString()

        try {
            // onPreExecuteと同等の処理
            withContext(Dispatchers.Main) {
            }

            // doInBackgroundメソッドと同等の処理
            connection = URL(m_baseUrl + targetTable + m_endUrl).openConnection() as HttpURLConnection
            connection?.connectTimeout = 1000
            connection?.readTimeout = 1000
            connection?.useCaches = false
            connection?.requestMethod = "GET"
            // 接続
            connection?.connect()

            var status = connection?.responseCode
            // 処理結果をチェック
            when (status) {
                HttpURLConnection.HTTP_OK -> {
                    // データ取得
                    dataStr = BufferedReader(InputStreamReader(connection?.inputStream)).readLines()[0].toString()
                }
                else -> {
                    msg = connection?.responseCode.toString()
                    throw Exception()
                }
            }

            when (targetTable) {
                "m_product" -> {
                    // 製品マスタ
                    outputStr = getProduct(Json.decodeFromString<List<Product>>(dataStr))
                }
                "m_parts" -> {
                    // 部品マスタ
                    outputStr = getParts(Json.decodeFromString<List<Parts>>(dataStr))
                }
                "m_product_structure" -> {
                    // 製品構成マスタ
                    outputStr = getProductStructure(Json.decodeFromString<List<ProductStructure>>(dataStr))
                }
                "m_user" -> {
                    // ユーザーマスタ
                    outputStr = getUser(Json.decodeFromString<List<User>>(dataStr))
                }
                "m_device" -> {
                    // デバイスマスタ
                    outputStr = getDevice(Json.decodeFromString<List<Device>>(dataStr))
                }
                "m_inventory_regist_type" -> {
                    // 在庫登録種別マスタ
                    outputStr = getInventoryRegistType(Json.decodeFromString<List<InventoryRegistType>>(dataStr))
                }
                "t_parts_inventory" -> {
                    // 部品在庫テーブル
                    outputStr = getPartsInventory(Json.decodeFromString<List<PartsInventory>>(dataStr))
                }
                "t_parts_inventory_history" -> {
                    // 部品在庫履歴テーブル
                    outputStr = getPartsInventoryHistory(Json.decodeFromString<List<PartsInventoryHistory>>(dataStr))
                }
                else -> {
                    throw Exception()
                }
            }
            if (outputStr.length < 1) {
                throw Exception()
            }

            // onPostExecuteメソッドと同等の処理
            withContext(Dispatchers.Main) {
                // 処理結果を表示
                MessageUtils.toast(outputStr)
            }
        } catch (e: Exception) {
            var test = e.message
            // onCancelledメソッドと同等の処理
            MessageUtils.toast("キャンセル")
        } finally {
            if (connection != null) {
                connection?.disconnect()
            }
        }
    }
*/

/*
    // 製品マスタ
    public fun getProduct(data: List<Product>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.product_id + "]") + ", "
                ret += obj.product_name
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

    // 部品マスタ
    public fun getParts(data: List<Parts>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.parts_id + "]") + ", "
                ret += obj.parts_name
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

    // 製品構成マスタ
    public fun getProductStructure(data: List<ProductStructure>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.product_id + "]") + ", "
                ret += ("[" + obj.parts_id + "]") + ", "
                ret += obj.parts_count.toString() + ", "
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }
*/

    // ユーザーマスタ
    public fun getUser(data: List<User>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.user_id + "]") + ", "
                ret += obj.user_name
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

    // デバイスマスタ
    public fun getDevice(data: List<Device>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.device_id + "]") + ", "
                ret += obj.device_name + ", "
                ret += obj.user_id.toString() + ", "
                ret += obj.mac_address + ", "
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

    // 在庫登録種別マスタ
    public fun getInventoryRegistType(data: List<InventoryRegistType>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.inventory_regist_type_id + "]") + ", "
                ret += obj.inventory_regist_type_name + ", "
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

/*
    // 部品在庫テーブル
    public fun getPartsInventory(data: List<PartsInventory>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.parts_id + "]") + ", "
                ret += obj.parts_inventory_count.toString() + ", "
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }
*/

    // 部品在庫履歴テーブル
    public fun getPartsInventoryHistory(data: List<PartsInventoryHistory>): String {
        var ret: String = ""
        try {
            for (obj in data) {
                ret += ("[" + obj.parts_inventory_history_id + "]") + ", "
                ret += obj.parts_id.toString() + ", "
                ret += obj.product_id.toString() + ", "
                ret += obj.device_id.toString() + ", "
                ret += obj.inventory_regist_type_id.toString() + ", "
                ret += obj.variable_count.toString() + ", "
                ret += obj.created_by.toString() + ", "
                ret += obj.create_at
                ret += "\n"
            }
        } catch (e: Exception) {
            ret = ""
        }
        return ret
    }

}