package com.example.core.data

import android.content.SharedPreferences
import com.example.core.data.mapper.toUserInfo
import com.example.core.data.mapper.toUserInfoSerializable
import com.example.core.domain.Preferences
import com.example.core.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences,
) : Preferences {


    override suspend fun getUserInfo(): UserInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_USER_INFO, null)
            json?.let {
                Json.decodeFromString<UserInfoSerializable>(it).toUserInfo()
            }
        }
    }

    override suspend fun setUserInfo(userInfo: UserInfo?) {
        withContext(Dispatchers.IO) {
            if (userInfo == null) {
                sharedPreferences.edit().remove(KEY_USER_INFO).apply()
                return@withContext
            }

            val json = Json.encodeToString(userInfo.toUserInfoSerializable())
            sharedPreferences
                .edit()
                .putString(KEY_USER_INFO, json)
                .commit()
        }
    }


    companion object {
        private const val KEY_USER_INFO = "userInfo"
    }
}
