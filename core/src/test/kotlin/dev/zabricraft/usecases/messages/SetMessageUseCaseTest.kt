package dev.zabricraft.usecases.messages

import dev.zabricraft.repositories.messages.IMessagesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class SetMessageUseCaseTest {

    @Test
    fun test() {
        val repository = mockk<IMessagesRepository>()
        val useCase = SetMessageUseCase(repository)
        every { repository.set("test", "Test") } returns Unit
        useCase("test", "Test")
        verify { repository.set("test", "Test") }
    }

    @Test
    fun testColorChar() {
        val repository = mockk<IMessagesRepository>()
        val useCase = SetMessageUseCase(repository)
        every { repository.set("test", "§aTest") } returns Unit
        useCase("test", "&aTest")
        verify { repository.set("test", "§aTest") }
    }

}
