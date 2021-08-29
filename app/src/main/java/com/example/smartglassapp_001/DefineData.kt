package com.example.smartglassapp_001

// データ定義クラス
class DefineData {
    // static変数・関数定義
    companion object {
        // ベースURL
        val baseUrl: String = "http://hoge0609.php.xdomain.jp/index.php/"

        // 製品マスタ
        val m_product: String = "m_product"
        // 部品マスタ
        val m_parts: String = "m_parts"
        // 製品構成マスタ
        val m_product_structure: String = "m_product_structure"
        // 部品在庫テーブル
        val t_parts_inventory: String = "t_parts_inventory"

        // getAll : URL
        val getAllUrl: String = "/get_all/"
        // getSingle : URL
        val getSingleUrl: String = "/get_single/"
        // getMulti : URL
        val getMultiUrl: String = "/get_multi/"
    }

}