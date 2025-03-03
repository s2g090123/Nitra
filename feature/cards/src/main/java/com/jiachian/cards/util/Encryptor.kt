package com.jiachian.cards.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

internal interface Encryptor {
    fun encrypt(text: String): String

    fun decrypt(encryptedText: String): String
}

/**
 * AESEncryptor is a utility class for encrypting and decrypting text using AES encryption
 * with a fixed IV derived from the SHA-256 hash of the input text.
 * This ensures that the same input always produces the same encrypted output while maintaining security.
 *
 * Note:
 * Using a fixed IV can make encryption predictable, which may have security implications.
 * Ensure that this approach fits your security requirements.
 */
internal class AESEncryptor @Inject constructor() : Encryptor {
    companion object {
        private const val ALIAS = "nitra"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORM = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val MESSAGE_ALGORITHM = "SHA-256"
        private const val HASH_SIZE = 16
    }

    private val keystore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val cipher = Cipher.getInstance(TRANSFORM)

    // Retrieves the encryption key from the Android Keystore, or creates a new one if it does not exist
    private fun getKey(): SecretKey {
        val existingKey = keystore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    // Creates a new AES key and stores it in the Android Keystore
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
                    .setRandomizedEncryptionRequired(false)
                    .build()
            )
        }.generateKey()
    }

    // Derives a fixed IV from the SHA-256 hash of the input text
    private fun deriveIV(text: String): ByteArray {
        val digest = MessageDigest.getInstance(MESSAGE_ALGORITHM)
        val hash = digest.digest(text.toByteArray())
        return hash.copyOf(HASH_SIZE) // AES IV needs to be 16 bytes
    }

    // Encrypts the input text using AES/CBC/PKCS7 and returns a Base64-encoded string
    override fun encrypt(text: String): String {
        val bytes = text.toByteArray()
        val iv = deriveIV(text) // Generate IV from the input text
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), IvParameterSpec(iv))
        val encrypted = cipher.doFinal(bytes)
        return Base64.encodeToString(
            iv + encrypted,
            Base64.DEFAULT
        ) // Store IV along with encrypted data
    }

    // Decrypts the given Base64-encoded encrypted text and returns the original string
    override fun decrypt(encryptedText: String): String {
        val bytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val iv = bytes.copyOfRange(0, HASH_SIZE) // Extract IV from the encrypted text
        val encryptedData = bytes.copyOfRange(HASH_SIZE, bytes.size) // Extract encrypted data
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(encryptedData).decodeToString()
    }
}