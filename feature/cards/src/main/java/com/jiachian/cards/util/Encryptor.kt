package com.jiachian.cards.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

interface Encryptor {
    fun encrypt(text: String): String

    fun decrypt(encryptedText: String): String
}

class AESEncryptor : Encryptor {
    companion object {
        private const val ALIAS = "nitra"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORM = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    private val keystore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val cipher = Cipher.getInstance(TRANSFORM)

    private fun getKey(): SecretKey {
        val existingKey = keystore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    override fun encrypt(text: String): String {
        val bytes = text.toByteArray()
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    override fun decrypt(encryptedText: String): String {
        val bytes = Base64.decode(encryptedText.toByteArray(), Base64.DEFAULT)
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data).decodeToString()
    }
}