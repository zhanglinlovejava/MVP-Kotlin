package com.zhanglin.kotlinmvp.utils

import android.text.TextUtils

import com.zhanglin.kotlinmvp.BuildConfig


/**
 * 2015/9/6 14:38
 *
 * @author zhanglin
 * @email www.zhanglin01@100tal.com
 * todo
 */
object Logger {

    private val TAG = "kotlin"

    private val location: String
        get() {
            val className = Logger::class.java.name
            val traces = Thread.currentThread()
                    .stackTrace
            var found = false

            for (trace in traces) {
                try {
                    if (found) {
                        if (!trace.className.startsWith(className)) {
                            val clazz = Class.forName(trace.className)
                            return ("[" + getClassName(clazz) + ":"
                                    + trace.methodName + ":"
                                    + trace.lineNumber + "]: ")
                        }
                    } else if (trace.className.startsWith(className)) {
                        found = true
                    }
                } catch (ignored: ClassNotFoundException) {
                }

            }

            return "[]: "
        }

    fun d() {
        if (BuildConfig.DEBUG) {
            android.util.Log.v(TAG, location)
        }
    }

    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.v(TAG, location + msg)
        }
    }

    fun i(TAG: String, msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(TAG, location + msg)
        }
    }

    fun i(msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(TAG, location + msg)
        }
    }

    fun i() {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(TAG, location)
        }
    }

    fun e(msg: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, location + msg)
        }
    }

    fun e(msg: String, e: Throwable) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, location + msg, e)
        }
    }

    fun e(e: Throwable) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, location, e)
        }
    }

    fun e() {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, location)
        }
    }

    private fun getClassName(clazz: Class<*>?): String {
        return if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.simpleName)) {
                clazz.simpleName
            } else getClassName(clazz.enclosingClass)

        } else ""

    }

}
