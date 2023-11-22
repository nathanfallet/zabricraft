package me.nathanfallet.zabricraft.usecases.core

import me.nathanfallet.zabricraft.repositories.core.IMessagesRepository
import org.bukkit.ChatColor

class SetMessageUseCase(
    private val repository: IMessagesRepository
) : ISetMessageUseCase {

    override fun invoke(input1: String, input2: String) {
        repository.set(input1.lowercase(), ChatColor.translateAlternateColorCodes('&', input2))
    }

}
