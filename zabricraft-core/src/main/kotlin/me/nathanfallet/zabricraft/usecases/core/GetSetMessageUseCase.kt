package me.nathanfallet.zabricraft.usecases.core

import org.bukkit.ChatColor

class GetSetMessageUseCase : IGetSetMessageUseCase {

    private val messages: MutableMap<String, String> = mutableMapOf()

    override fun invoke(input: String): String {
        return messages[input.lowercase()] ?: ("Unknown message: " + input.lowercase())
    }

    override fun invoke(input1: String, input2: String) {
        messages[input1.lowercase()] = ChatColor.translateAlternateColorCodes('&', input2)
    }

}
