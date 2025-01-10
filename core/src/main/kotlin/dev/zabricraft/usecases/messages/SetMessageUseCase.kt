package dev.zabricraft.usecases.messages

import dev.zabricraft.repositories.messages.IMessagesRepository
import org.bukkit.ChatColor

class SetMessageUseCase(
    private val repository: IMessagesRepository,
) : ISetMessageUseCase {

    override fun invoke(input1: String, input2: String) {
        repository.set(input1.lowercase(), ChatColor.translateAlternateColorCodes('&', input2))
    }

}
