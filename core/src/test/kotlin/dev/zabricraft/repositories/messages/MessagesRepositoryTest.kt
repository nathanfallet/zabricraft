package dev.zabricraft.repositories.messages

import kotlin.test.Test
import kotlin.test.assertEquals

class MessagesRepositoryTest {

    @Test
    fun testAdd() {
        val repository = MessagesRepository()
        repository.set("key", "value")
        assertEquals("value", repository.get("key"))
    }

    @Test
    fun testGetNonExists() {
        val repository = MessagesRepository()
        assertEquals(null, repository.get("key"))
    }

}
