package com.tnkfactory.rwd.offerer.utils

import android.content.Context
import com.tnkfactory.ad.rwd.AdvertisingIdInfo
import java.io.IOException

/**
 * @author hanago
 * @email hans@tnkfactory.com
 * @since 2022/08/31
 **/
class GAIDTask {

    suspend fun getGAID(context: Context): String {
        return try {
            AdvertisingIdInfo.requestIdInfo(context).getId()
        } catch (e: IOException) {
            "sample_id"
        }
    }
}
