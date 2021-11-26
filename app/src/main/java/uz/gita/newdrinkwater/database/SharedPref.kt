package uz.gita.newdrinkwater.database

import android.content.Context.MODE_PRIVATE
import uz.gita.newdrinkwater.app.App

class SharedPref private constructor() {

    private val pref = App.instance.getSharedPreferences("cache", MODE_PRIVATE)

    companion object {
        private lateinit var pref: SharedPref

        fun getSharedPref(): SharedPref {
            if (!Companion::pref.isInitialized) {
                pref = SharedPref()
            }
            return pref
        }
    }

    var gender: String
        get() = pref.getString("posGender", "Male").toString()
        set(value) = pref.edit().putString("posGender", value).apply()

    var pos: Boolean
        get() = pref.getBoolean("pos", true)
        set(value) = pref.edit().putBoolean("pos", value).apply()

    var weight: String
        get() = pref.getString("weight", "")!!
        set(value) = pref.edit().putString("weight", value).apply()

    var dateWake: String
        get() = pref.getString("dateWake", "00:00").toString()
        set(value) = pref.edit().putString("dateWake", value).apply()

    var dateBed: String
        get() = pref.getString("dateBed", "00:00").toString()
        set(value) = pref.edit().putString("dateBed", value).apply()

    var isStart: Boolean
        get() = pref.getBoolean("isStart", false)
        set(value) = pref.edit().putBoolean("isStart", value).apply()
    var resetData: Boolean
        get() = pref.getBoolean("resetData", false)
        set(value) = pref.edit().putBoolean("resetData", value).apply()
    var reminderSwitch: Boolean
        get() = pref.getBoolean("reminderSwitch", true)
        set(value) = pref.edit().putBoolean("reminderSwitch", value).apply()
    var progressCount: Int
        get() = pref.getInt("countProgress", 0)
        set(value) = pref.edit().putInt("countProgress", value).apply()
    var targetWater: Int
        get() = pref.getInt("targetWater", 3000)
        set(value) = pref.edit().putInt("targetWater", value).apply()
    var cup: Int
        get() = pref.getInt("cup", 100)
        set(value) = pref.edit().putInt("cup", value).apply()

}