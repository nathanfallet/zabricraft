package dev.zabricraft.repositories.messages

interface IMessagesRepository {

    fun get(key: String): String?
    fun set(key: String, value: String)

}
