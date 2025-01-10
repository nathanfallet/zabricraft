package dev.zabricraft.usecases.messages

import dev.zabricraft.repositories.messages.IMessagesRepository

class GetMessageUseCase(
    private val repository: IMessagesRepository,
) : IGetMessageUseCase {

    override fun invoke(input: String): String {
        return repository.get(input.lowercase()) ?: ("Unknown message: " + input.lowercase())
    }

}
