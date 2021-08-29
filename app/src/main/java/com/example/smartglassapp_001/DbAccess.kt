package com.example.smartglassapp_001

// DBアクセスクラス
class DbAccess {
    // staticクラス
    companion object {
        // 製品データクラスリスト
        var productDataList: ProductDataList? = null
        // 部品データクラスリスト
        var partsDataList: PartsDataList? = null
        // ターゲット部品在庫データクラス
        var targetPartsInventoryData: TargetPartsInventoryData? = null
        // ターゲット製品構成データクラス
        var targetProductStructureData: TargetProductStructureData? = null

        // 部品データ文字列取得
        fun getPartsDataString(parts_id: Int) : String {
            var ret: String = ""
            var partsName: String = ""
            var partsInventoryCount: Int = 0

            try {
                ret += ("[部品ID：" + parts_id + "] ")
                // 部品名取得
                partsName = partsDataList?.getPartsName(parts_id)!!
                if (0 < partsName.length) {
                    ret += ("[部品名：" + partsName + "] ")
                    // ターゲット部品在庫データクラス
                    targetPartsInventoryData = TargetPartsInventoryData(parts_id)
                    if (targetPartsInventoryData?.checkFlag == true) {
                        // 部品数
                        partsInventoryCount = targetPartsInventoryData?.partsInventoryCount!!
                        ret += ("[在庫数：" + partsInventoryCount + "]")
                    } else {
                        ret += ("[在庫数：　　]")
                    }
                } else {
                    ret += ("[部品名：　　] ")
                    ret += ("[在庫数：　　]")
                }
            } catch (e: Exception) {
                ret = "[ERROR]"
            }

            return ret
        }
    }

}