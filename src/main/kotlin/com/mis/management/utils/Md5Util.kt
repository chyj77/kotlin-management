package com.mis.management.utils

import org.apache.commons.codec.binary.Hex
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

object Md5Util {
    private const val HEX_NUMS_STR = "0123456789ABCDEF"
    private const val SALT_LENGTH = 16

    /**
     * 加盐MD5加密
     *
     *
     *
     * @Title :
     *
     * getSaltMD5
     * @Description :
     *
     *TODO
     * @Author :
     *
     *HuaZai
     * @Date :
     *
     *2017年12月27日 上午11:21:00
     *
     */
    fun getSaltMD5(password: String, salt: String): String {
        var password = password
        password = md5Hex(password + salt)
        val cs = CharArray(48)
        var i = 0
        while (i < 48) {
            cs[i] = password[i / 3 * 2]
            val c = salt[i / 3]
            cs[i + 1] = c
            cs[i + 2] = password[i / 3 * 2 + 1]
            i += 3
        }
        return String(cs)
    }

    // 生成一个16位的随机数
    val salt:
            // 生成最终的加密盐
            String
        get() {
            // 生成一个16位的随机数
            val random = Random()
            val sBuilder = StringBuilder(SALT_LENGTH)
            sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999))
            val len = sBuilder.length
            if (len < SALT_LENGTH) {
                for (i in 0 until SALT_LENGTH - len) {
                    sBuilder.append("0")
                }
            }
            // 生成最终的加密盐
            return sBuilder.toString()
        }

    fun md5Hex(str: String): String {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(str.toByteArray())
            String(Hex().encode(digest))
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.toString())
            ""
        }
    }

    fun getNoSaltMD5(password: String): String {
        var password = password
        password = md5Hex(password + HEX_NUMS_STR)
        val cs = CharArray(48)
        var i = 0
        while (i < 48) {
            cs[i] = password[i / 3 * 2]
            val c = HEX_NUMS_STR[i / 3]
            cs[i + 1] = c
            cs[i + 2] = password[i / 3 * 2 + 1]
            i += 3
        }
        return String(cs)
    }

    @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class)
    fun md5(param: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.reset()
        md.update(param.toByteArray(charset("utf-8")))
        val d = md.digest()
        val r = StringBuilder(d.size * 2)
        for (b in d) {
            r.append(HEX_NUMS_STR.toCharArray()[(b.toInt() shr 4) and 0xF])
            r.append(HEX_NUMS_STR.toCharArray()[b.toInt() and 0xF])
        }
        return r.toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val password = getNoSaltMD5("123456")
        println(password)
    }
}