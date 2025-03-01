package com.jiachian.cards.util

import org.junit.Assert.*
import org.junit.Test

class AESEncryptorTest {
    @Test
    fun encryptText_decryptCorrectly() {
        val text = "hello world"
        val encryptor = AESEncryptor()

        val encrypted = encryptor.encrypt(text)
        assertEquals(text, encryptor.decrypt(encrypted))
    }
}