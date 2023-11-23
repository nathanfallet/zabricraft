package me.nathanfallet.zabricraft.usecases.core

import me.nathanfallet.zabricraft.repositories.core.IMessagesRepository

class GetMessageUseCase(
    private val repository: IMessagesRepository
) : IGetMessageUseCase {

    override fun invoke(input: String): String {
        return repository.get(input.lowercase()) ?: ("Unknown message: " + input.lowercase())
    }

}
