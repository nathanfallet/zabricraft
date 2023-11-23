package me.nathanfallet.zabricraft.usecases.core

import io.mockk.every
import io.mockk.mockk
import me.nathanfallet.zabricraft.repositories.core.IMessagesRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMessageUseCaseTest {

    @Test
    fun testExisting() {
        val repository = mockk<IMessagesRepository>()
        val useCase = GetMessageUseCase(repository)
        every { repository.get("test") } returns "Test"
        assertEquals("Test", useCase("test"))
    }

    @Test
    fun testNotExisting() {
        val repository = mockk<IMessagesRepository>()
        val useCase = GetMessageUseCase(repository)
        every { repository.get("test") } returns null
        assertEquals("Unknown message: test", useCase("test"))
    }

}
